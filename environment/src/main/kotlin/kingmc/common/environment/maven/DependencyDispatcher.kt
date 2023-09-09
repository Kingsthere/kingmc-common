package kingmc.common.environment.maven

import kingmc.common.environment.AbstractXmlParser
import kingmc.common.environment.ClassAppender
import kingmc.common.environment.DependencyDeclaration
import kingmc.common.environment.maven.model.*
import kingmc.common.file.URLFileDownloader
import kingmc.common.file.concurrent.DownloadExecutors
import kingmc.common.file.concurrent.ExecutorHttpURLDownloader
import kingmc.common.logging.Logger
import me.lucko.jarrelocator.JarRelocator
import org.apache.commons.io.FileUtils
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.xml.sax.SAXException
import java.io.*
import java.net.URL
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.text.ParseException
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Future
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.parsers.ParserConfigurationException
import kotlin.collections.HashSet


/**
 * A class responsible for dispatching maven dependencies
 *
 * @param root the root directory to store downloaded maven dependency files
 * @author kingsthere
 * @since 0.0.6
 */
class DependencyDispatcher(
    val root: File,
    val logger: Logger,
    val classAppender: ClassAppender,
    val executorService: ExecutorService = DownloadExecutors(),
    val fileDownloader: URLFileDownloader = ExecutorHttpURLDownloader(16, executorService)
): AbstractXmlParser() {

    /**
     * Dependencies installed by this dependency dispatcher
     */
    val installedDependencies: MutableSet<DependencyDeclaration> = Collections.synchronizedSet(HashSet())

    /**
     * Install the given [dependencyDeclaration] asynchronously
     */
    fun installDependencyAsync(dependencyDeclaration: DependencyDeclaration): Future<*> {
        return executorService.submit {
            installDependency(dependencyDeclaration)
        }
    }

    /**
     * Install the given [dependency] asynchronously
     */
    fun installDependencyAsync(dependency: Dependency, repository: Repository, relocations: Collection<JarRelocation>, vararg scopes: DependencyScope): Future<*> {
        return installDependencyAsync(DependencyDeclaration(dependency, repository, relocations, scopes))
    }

    /**
     * Install the [dependency] from specifies [repository]
     */
    fun installDependency(dependency: Dependency, repository: Repository, relocations: Collection<JarRelocation>, vararg scopes: DependencyScope) {
        installDependency(DependencyDeclaration(dependency, repository, relocations, scopes))
    }

    /**
     * Install the given [dependencyDeclaration] from specifies [repository]
     */
    fun installDependency(dependencyDeclaration: DependencyDeclaration) {
        val relocations = dependencyDeclaration.relocations
        val dependency = dependencyDeclaration.dependency
        val repository = dependencyDeclaration.repository

        logger.logInfo("Installing dependency $dependency")
        fun injectClasspath(file: File, downloadedDependency: DownloadedDependency) {
            if (relocations.isEmpty()) {
                logger.logInfo("Adding $file to classpath")
                classAppender.addPath(file.toPath())
            } else {
                logger.logInfo("Relocating $dependency ($relocations)")
                val relocated = File(file.path + "-" + relocations.hashCode() + ".jar")
                if (!relocated.exists() || relocated.length() == 0L) {
                    try {
                        val relocationMap = buildMap {
                            relocations.forEach {
                                put(it.pattern, it.relocatedPattern)
                            }
                        }
                        val relocatedFile = File.createTempFile(file.name, ".jar")
                        FileUtils.copyFile(file, relocatedFile)
                        JarRelocator(
                            relocatedFile,
                            relocated,
                            relocationMap
                        ).run()
                    } catch (e: IOException) {
                        e.printStackTrace()
                        throw IllegalStateException(String.format("Unable to relocate %s%n", dependency), e)
                    }
                }
                logger.logInfo("Adding $relocated to classpath")
                classAppender.addPath(relocated.toPath())
            }
            downloadedDependency.transitive.forEach {
                injectClasspath(it.jar, it)
            }
        }
        if (dependencyDeclaration !in installedDependencies) {
            val downloadedDependency = downloadDependency(dependency, setOf(repository), *dependencyDeclaration.scopes)
            injectClasspath(downloadedDependency.jar, downloadedDependency)
            installedDependencies.add(dependencyDeclaration)
        }
    }

    /**
     * Download a [dependency] from specifies [repositories]
     */
    fun downloadDependency(dependency: Dependency, repositories: Collection<Repository>, vararg scopes: DependencyScope): DownloadedDependency {
        val directory = createDependencyDirectory(root, dependency)
        // Create parent directory if not exists
        if (!directory.exists()) {
            directory.mkdirs()
        }
        val jar = File(directory, "${dependency.artifactId}-${dependency.version}.jar")
        val jarSha1 = File(directory, "${dependency.artifactId}-${dependency.version}.jar.sha1")
        val pom = File(directory, "${dependency.artifactId}-${dependency.version}.pom")
        val pomSha1 = File(directory, "${dependency.artifactId}-${dependency.version}.pom.sha1")
        val transitive = mutableListOf<DownloadedDependency>()

        if (pom.exists() && pomSha1.exists() && jar.exists() && jarSha1.exists() && readFile(pomSha1)
                .startsWith(readFileHash(pom)) && readFile(jarSha1)
                .startsWith(readFileHash(jar))
        ) {
            val transitiveDependencies = getDependenciesFromPom(pom, *scopes)
            transitiveDependencies.dependencies.forEach {
                transitive.add(downloadDependency(it, transitiveDependencies.repositories, *scopes))
            }
            return DownloadedDependency(
                jar = jar,
                jarSha1 = jarSha1,
                pom = pom,
                pomSha1 = pomSha1,
                transitive = transitive
            )
        }
        var exception: IOException? = null
        for (repository in repositories) {
            try {
                try {
                    downloadFile(repository, dependency, jar)
                    downloadFile(repository, dependency, jarSha1)
                    downloadFile(repository, dependency, pom)
                    downloadFile(repository, dependency, pomSha1)
                } catch (exception: IOException) {
                    try {
                        val factory = DocumentBuilderFactory.newInstance().apply {
                            isIgnoringElementContentWhitespace = true
                        }
                        val builder = factory.newDocumentBuilder()
                        val xml = builder.parse(pom)
                        try {
                            if (find("packaging", xml.documentElement, "pom") == "jar") {
                                throw exception
                            }
                        } catch (ex: ParseException) {
                            ex.addSuppressed(exception)
                            throw IOException("Unable to find packaging logger.logInformation in pom.xml", ex)
                        }
                    } catch (ex: ParserConfigurationException) {
                        ex.addSuppressed(exception)
                        throw IOException("Unable to load pom.xml parser", ex)
                    } catch (ex: SAXException) {
                        ex.addSuppressed(exception)
                        throw IOException("Unable to parse pom.xml", ex)
                    } catch (ex: IOException) {
                        if (ex != exception) {
                            ex.addSuppressed(exception)
                        }
                        throw ex
                    }
                }
                // Load additional dependency from pom file
                if (pom.exists()) {
                    val transitiveDependencies = getDependenciesFromPom(pom, *scopes)
                    transitiveDependencies.dependencies.forEach {
                        transitive.add(downloadDependency(it, transitiveDependencies.repositories, *scopes))
                    }
                }
                exception = null
                break
            } catch (ex: IOException) {
                if (exception == null) {
                    exception = IOException(String.format("Unable to find download for %s (%s)", dependency, repository.url))
                }
                exception.addSuppressed(ex)
            }
        }
        if (exception != null) {
            throw exception
        }
        return DownloadedDependency(
            jar = jar,
            jarSha1 = jarSha1,
            pom = pom,
            pomSha1 = pomSha1,
            transitive = transitive
        )
    }

    fun getDependenciesFromPom(pom: File, vararg scopes: DependencyScope): Pom {
        val factory = DocumentBuilderFactory.newInstance().apply {
            isIgnoringElementContentWhitespace = true
        }
        val builder = factory.newDocumentBuilder()
        val xml: Document = builder.parse(pom)

        val repositories = mutableListOf<Repository>()
        val dependencies = mutableListOf<Dependency>()
        var nodes = xml.childNodes

        var item2 = 0
        while (item2 < nodes.length) {
            val node = nodes.item(item2)
            if (node.nodeName == "repositories") {
                nodes = (node as Element).getElementsByTagName("repository")
                item2 = 0
                while (item2 < nodes.length) {
                    val e = nodes.item(item2) as Element
                    repositories.add(Repository(e))
                    ++item2
                }
                break
            }
            ++item2
        }

        nodes = xml.getElementsByTagName("dependency")
        try {
            for (item in 0 until nodes.length) {
                val dependency = Dependency(nodes.item(item) as Element)
                if (scopes.contains(dependency.scope)) {
                    dependencies.add(dependency)
                }
            }
        } catch (ex: ParseException) {
            throw IOException("Unable to parse dependencies", ex)
        }
        return Pom(dependencies, repositories)
    }

    fun downloadFile(repository: Repository, dependency: Dependency, out: File) {
        val url = URL(
            String.format(
                "%s/%s/%s/%s/%s",
                repository.url.removeSuffix("/"),
                dependency.groupId.replace('.', '/'),
                dependency.artifactId,
                dependency.version,
                out.name
            )
        )
        logger.logInfo("[${Thread.currentThread().name}] Downloading $dependency from $url to $out")
        FileUtils.copyURLToFile(url, out)
    }

    fun downloadFileAsync(repository: Repository, dependency: Dependency, out: File) {
        val url = URL(
            String.format(
                "%s/%s/%s/%s/%s",
                repository.url.removeSuffix("/"),
                dependency.groupId.replace('.', '/'),
                dependency.artifactId,
                dependency.version,
                out.name
            )
        )
        logger.logInfo("[${Thread.currentThread().name}] Downloading $dependency from $url to $out")
        fileDownloader.download(url, out).run()
    }

    /**
     * Create the directory of [dependency] to store dependency files downloaded
     *
     * @return the directory created
     */
    fun createDependencyDirectory(root: File, dependency: Dependency): File {
        var dir: File = root
        for (part in dependency.groupId.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()) {
            dir = File(dir, part)
        }
        dir = File(dir, dependency.artifactId)
        dir = File(dir, dependency.version)
        return dir
    }


    private fun readFile(file: File): String {
        try {
            FileInputStream(file).use { fileInputStream ->
                return readFully(
                    fileInputStream,
                    StandardCharsets.UTF_8
                )
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return "null (" + UUID.randomUUID() + ")"
    }

    private fun readFileHash(file: File): String {
        try {
            val digest = MessageDigest.getInstance("sha-1")
            Files.newInputStream(file.toPath()).use { inputStream ->
                val buffer = ByteArray(1024)
                var total: Int
                while (inputStream.read(buffer).also { total = it } != -1) {
                    digest.update(buffer, 0, total)
                }
            }
            return getHash(digest)
        } catch (ex: IOException) {
            ex.printStackTrace()
        } catch (ex: NoSuchAlgorithmException) {
            ex.printStackTrace()
        }
        return "null (" + UUID.randomUUID() + ")"
    }

    @Throws(IOException::class)
    fun readFully(inputStream: InputStream, charset: Charset): String {
        return String(readFully(inputStream), charset)
    }

    @Throws(IOException::class)
    fun readFully(inputStream: InputStream): ByteArray {
        val stream = ByteArrayOutputStream()
        val buf = ByteArray(1024)
        var len: Int
        while (inputStream.read(buf).also { len = it } > 0) {
            stream.write(buf, 0, len)
        }
        return stream.toByteArray()
    }

    private fun getHash(digest: MessageDigest): String {
        val result = StringBuilder()
        for (b in digest.digest()) {
            result.append(String.format("%02x", b))
        }
        return result.toString()
    }
}