package kingmc.common.file.compress

import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream

/**
 * Extract this file as a .zip file
 *
 * @param dest the directory to extract this file into
 * @author kingsthere
 * @since 0.0.2
 */
fun File.extractZip(dest: File) {
    val buffer = ByteArray(1024)
    FileInputStream(this).use { fis ->
        ZipInputStream(fis).use { zis ->
            var ze: ZipEntry? = zis.nextEntry
            while (ze != null) {
                val newFile = File(dest, ze.name)
                if (!newFile.toPath().normalize().startsWith(dest.toPath().normalize())) {
                    throw RuntimeException("Bad zip entry")
                }
                val parent = newFile.parentFile
                check(!(!parent.exists() && !parent.mkdirs())) { "Couldn't create dir: $parent" }
                if (ze.isDirectory) {
                    newFile.mkdir()
                    ze = zis.nextEntry
                    continue
                }
                FileOutputStream(newFile).use { fos ->
                    var len: Int
                    while (zis.read(buffer).also { len = it } > 0) {
                        fos.write(buffer, 0, len)
                    }
                }
                ze = zis.nextEntry
            }
            zis.closeEntry()
        }
    }
}