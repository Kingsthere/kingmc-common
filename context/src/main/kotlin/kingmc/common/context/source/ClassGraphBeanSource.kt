package kingmc.common.context.source

import io.github.classgraph.*
import kingmc.common.context.BeanSource
import kingmc.common.context.Context
import kingmc.common.context.PropertyBeanSource
import kingmc.common.context.annotation.Condition
import kingmc.common.context.annotation.ConditionContext
import kingmc.common.context.beans.*
import kingmc.common.context.exception.ContextInitializeException
import kingmc.util.annotation.getAnnotationContent
import kingmc.util.annotation.getAnnotationContentsStatic
import kingmc.util.annotation.hasAnnotationClassname
import kingmc.util.annotation.model.AnnotationContent
import java.util.*
import kotlin.reflect.jvm.kotlinFunction

const val ANNOTATION_COMPONENT: String = "kingmc.common.context.annotation.Component"
const val ANNOTATION_CONDITIONAL: String = "kingmc.common.context.annotation.Conditional"
const val ANNOTATION_SCOPE: String = "kingmc.common.context.annotation.Scope"
const val ANNOTATION_BEAN: String = "kingmc.common.context.annotation.Bean"
const val ANNOTATION_DISABLE_SCAN: String = "kingmc.common.context.annotation.DisableScan"
const val ANNOTATION_CONFIGURATION: String = "kingmc.common.context.annotation.Configuration"
const val ANNOTATION_LATEINIT: String = "kingmc.common.context.annotation.Lateinit"
const val ANNOTATION_PRIORITY: String = "kingmc.common.context.annotation.Priority"
const val ANNOTATION_PRIMARY: String = "kingmc.common.context.annotation.Primary"
const val ANNOTATION_PRIVACY: String = "kingmc.common.context.annotation.Privacy"

/**
 * A `BeanSource` implemented by `ClassGraph`
 *
 * @author kingsthere
 * @since 0.1.1
 */
open class ClassGraphBeanSource(
    classGraph: ClassGraph = ClassGraph()
        .enableAnnotationInfo()
        .enableClassInfo(),
    val classLoader: ClassLoader,
    override val properties: Properties,
    parent: List<BeanSource> = emptyList(),
    val preparedBeanDefinition: List<BeanDefinition> = emptyList(),
) : PropertyBeanSource {
    val scanResult: ScanResult = classGraph.scan()
    private val _parent: MutableList<BeanSource> = parent.toMutableList()

    /**
     * Start to load beans from this bean source
     */
    override fun load() {
        scanResult.allClasses.forEach { classInfo ->
            // Skip annotation classes
            if (classInfo.isAnnotation) {
                return@forEach
            }

            // Load bean from classInfo
            val result = loadAndRememberScannedBeans(classInfo, true)?.first ?: return@forEach

            // If the loading classInfo is declared as a bean
            getInheritedBeans(classInfo).forEach {
                // Load inherited beans
                val implemented = loadAndRememberScannedBeans(it)?.first ?: getLoadingBeanDefinition(getBeanName(it))
                if (implemented is ClassGraphLoadingScannedBeanDefinition) {
                    // Add implementation to inherited bean
                    implemented.addImplementation(result)
                }
            }
        }
    }

    /**
     * Add a parent bean source to this bean source
     */
    override fun addParent(parent: BeanSource) {
        this._parent.add(parent)
    }

    /**
     * A map stores loading bean definitions only created by this bean source
     */
    val protectedLoadingBeanDefinitions: MutableMap<String, LoadingBeanDefinition> = HashMap()


    /**
     * Test the condition of annotations is available
     */
    fun testCondition(
        loadingBeanDefinition: LoadingBeanDefinition,
        annotations: AnnotationInfoList,
        testedLoadingBeanDefinition: MutableMap<LoadingBeanDefinition, Boolean>
    ): Boolean {
        if (loadingBeanDefinition is ClassGraphLoadingAnnotatedBeanDefinition) {
            val sourceBean = loadingBeanDefinition.source
            if (!testCondition(
                    sourceBean,
                    (sourceBean as ClassGraphLoadingBeanDefinition).annotations,
                    testedLoadingBeanDefinition
                )
            ) {
                testedLoadingBeanDefinition[loadingBeanDefinition] = false
                return false
            }
        }
        if (testedLoadingBeanDefinition.containsKey(loadingBeanDefinition)) {
            return testedLoadingBeanDefinition[loadingBeanDefinition]!!
        } else {
            val getResult: (LoadingBeanDefinition) -> Boolean = {
                testCondition(
                    loadingBeanDefinition = it,
                    annotations = (it as ClassGraphLoadingBeanDefinition).annotations,
                    testedLoadingBeanDefinition = testedLoadingBeanDefinition
                )
            }
            val value: Boolean = run {
                // Test the condition of that bean if that bean wasn't tested yet
                annotations.getAnnotationContentsStatic(ANNOTATION_CONDITIONAL).forEach { conditional ->
                    val condition =
                        (conditional.getAttribute("condition") as AnnotationClassRef).loadClass().kotlin.objectInstance as Condition
                    if (!condition.test(
                            loadingBeanDefinition,
                            ClassGraphConditionContextImpl(this, getResult, conditional)
                        )
                    ) {
                        return@run false
                    }
                }
                return@run true
            }
            testedLoadingBeanDefinition[loadingBeanDefinition] = value
            return value
        }
    }

    /**
     * Check if the given class info is written as a scanned bean
     */
    fun isScannedBean(classInfo: ClassInfo) = classInfo.hasAnnotationClassname(ANNOTATION_COMPONENT)
            && !classInfo.hasAnnotationClassname(ANNOTATION_DISABLE_SCAN)

    /**
     * Check if the given class info is written as a configuration bean
     */
    fun isConfigurationBean(classInfo: ClassInfo) = classInfo.hasAnnotationClassname(ANNOTATION_CONFIGURATION)

    fun isAnnotatedBean(methodInfo: MethodInfo) = methodInfo.hasAnnotationClassname(ANNOTATION_BEAN)

    open fun loadAndRememberScannedBeans(
        classInfo: ClassInfo,
        check: Boolean = true
    ): Pair<LoadingBeanDefinition, Set<LoadingBeanDefinition>>? {
        // Check if the given class is a valid bean
        if (!isScannedBean(classInfo)) {
            return null
        }

        // Get bean name
        val beanName = getBeanName(classInfo)

        // Check if the bean is already loaded
        if (check && hasLoadingBeanDefinition(beanName)) {
            return null
        }

        val result = loadScannedBeans(classInfo)
        defineLoadingBeanDefinition(result.first)
        result.second.forEach { bean ->
            defineLoadingBeanDefinition(bean)
        }
        return result
    }

    protected open fun defineLoadingBeanDefinition(loadingBeanDefinition: LoadingBeanDefinition) {
        val name = loadingBeanDefinition.name
        if (hasLoadingBeanDefinition(name)) {
            throw IllegalArgumentException("A bean with name $name is already defined")
        }
        protectedLoadingBeanDefinitions[name] = loadingBeanDefinition
    }

    open fun loadScannedBeans(classInfo: ClassInfo): Pair<LoadingBeanDefinition, Set<LoadingBeanDefinition>> {
        val extra: MutableSet<LoadingBeanDefinition> = LinkedHashSet()

        // Process scanned bean (Annotated with @Component)
        val source = ClassGraphLoadingScannedBeanDefinition(classInfo, getBeanName(classInfo))

        // Process annotated bean (Provided by @Configuration beans)
        if (isConfigurationBean(classInfo)) {
            classInfo.methodInfo.forEach { methodInfo ->
                if (isAnnotatedBean(methodInfo)) {
                    loadAnnotatedBean(source, methodInfo)?.let { bean -> extra.add(bean) }
                }
            }
        }

        return source to extra
    }

    open fun loadAnnotatedBean(source: LoadingBeanDefinition, methodInfo: MethodInfo): LoadingBeanDefinition? {
        return ClassGraphLoadingAnnotatedBeanDefinition(source, methodInfo)
    }

    protected fun getInheritedBeans(classInfo: ClassInfo): List<ClassInfo> {
        val result = LinkedList<ClassInfo>()
        getInheritedBeansToList(classInfo, result)
        return result
    }

    private fun getInheritedBeansToList(classInfo: ClassInfo, list: MutableList<ClassInfo>) {
        classInfo.superclasses.forEach {
            if (isScannedBean(it)) {
                // The classInfo is declared as a bean, add it to the list provided
                list.add(it)
            }
        }
        classInfo.interfaces.forEach {
            if (isScannedBean(it)) {
                // The classInfo is declared as a bean, add it to the list provided
                list.add(it)
            }
        }
    }

    /**
     * Gets the bean name for the given classInfo, the given classInfo must be
     * declared as a scanned bean
     */
    fun getBeanName(classInfo: ClassInfo): String {
        return if (classInfo.hasAnnotationClassname(ANNOTATION_COMPONENT)) {
            // Name specified by @Component
            val name = classInfo.getAnnotationContent(ANNOTATION_COMPONENT)!!.getAttribute("name") as String
            name.ifEmpty {
                // Default bean name from class (simpleName of class with first letter lowercase)
                getDefaultBeanName(classInfo)
            }
        } else {
            throw IllegalArgumentException("Class ${classInfo.name} is not a valid scanned bean")
        }
    }

    /**
     * Gets the bean name for the given classInfo, the given classInfo must be
     * declared as a scanned bean
     */
    fun getBeanName(annotationClassRef: AnnotationClassRef): String {
        return getBeanName(annotationClassRef.classInfo)
    }

    private fun getDefaultBeanName(classInfo: ClassInfo): String {
        return classInfo.simpleName.replaceFirstChar { it.lowercase() }
    }

    /**
     * Get already prepared bean definitions from this bean source
     */
    override fun getPreparedBeanDefinitions(): List<BeanDefinition> = preparedBeanDefinition

    /**
     * Finish loading and get loaded bean definitions from this bean source
     */
    override fun finishLoading(context: Context): List<BeanDefinition> {
        val testedLoadingBeanDefinitions: MutableMap<LoadingBeanDefinition, Boolean> =
            HashMap(protectedLoadingBeanDefinitions.size)
        return protectedLoadingBeanDefinitions
            .filter { (_, loadingBeanDefinition) -> // Test bean condition
                return@filter testCondition(
                    loadingBeanDefinition = loadingBeanDefinition,
                    annotations = (loadingBeanDefinition as ClassGraphLoadingBeanDefinition).annotations,
                    testedLoadingBeanDefinition = testedLoadingBeanDefinitions
                )
            }.also { println(it) }
            .map { it.value.finishLoading(context) } // Map to`BeanDefinition`
    }

    /**
     * Gets a loading bean definition with the given name from this bean source
     *
     * @param name the name of the bean to get
     * @return loading bean definition created by this bean source, or `null` if
     *         bean with the given name of not found in this bean source
     */
    override fun getLoadingBeanDefinition(name: String): LoadingBeanDefinition? {
        // Get bean definitions from this bean source
        protectedLoadingBeanDefinitions[name]?.let {
            return it
        }

        // Get bean definitions from parent bean sources
        _parent.forEach { parent ->
            parent.getLoadingBeanDefinition(name)?.let {
                return it
            }
        }

        // No bean definition found, return null
        return null
    }

    /**
     * Check if a loading bean definition with the given name it exists in this bean source
     *
     * @param name the name of the bean to check
     * @return `true` if the bean definition with the given name exists in this bean source
     */
    override fun hasLoadingBeanDefinition(name: String): Boolean {
        // Check bean definitions from this bean source
        if (protectedLoadingBeanDefinitions.containsKey(name)) {
            return true
        }

        // Check bean definition from parent bean sources
        return _parent.any { it.hasLoadingBeanDefinition(name) }
    }

}

class ClassGraphConditionContextImpl(
    val beanSource: ClassGraphBeanSource,
    val beanConditionResultFunction: (LoadingBeanDefinition) -> Boolean,
    override val annotationInfo: AnnotationContent
) : ClassGraphConditionContext {
    /**
     * Check if the given bean definition is available in the current context
     */
    override fun isBeanAvailable(loadingBeanDefinition: LoadingBeanDefinition) =
        beanConditionResultFunction(loadingBeanDefinition)

    /**
     * Gets the bean source for the loading bean definition
     */
    override fun getBeanSource(): BeanSource = beanSource

}

/**
 * [ConditionContext] for `ClassGraph` bean source
 */
interface ClassGraphConditionContext : ConditionContext {
    /**
     * The annotation causes the condition to check
     */
    val annotationInfo: AnnotationContent
}

/**
 * [LoadingBeanDefinition] for `ClassGraph` bean source
 */
interface ClassGraphLoadingBeanDefinition : LoadingBeanDefinition {
    /**
     * Annotations in this loading bean definition
     */
    val annotations: AnnotationInfoList
}

class ClassGraphLoadingScannedBeanDefinition(val classInfo: ClassInfo, beanName: String) :
    ClassGraphLoadingBeanDefinition {
    /**
     * Returns the name of this bean
     */
    override val name: String = beanName

    /**
     * Annotations in this loading bean definition
     */
    override val annotations: AnnotationInfoList = classInfo.annotationInfo

    /**
     * Returns the scope of this bean
     */
    override val scope: BeanScope = if (classInfo.hasAnnotationClassname(ANNOTATION_SCOPE)) {
        classInfo.getAnnotationContent(ANNOTATION_SCOPE)!!.getAttribute("scope") as BeanScope
    } else {
        BeanScope.SHARED
    }

    /**
     * Returns the bean privacy of this bean
     */
    override val privacy: BeanPrivacy = if (classInfo.hasAnnotationClassname(ANNOTATION_PRIVACY)) {
        classInfo.getAnnotationContent(ANNOTATION_PRIVACY)!!.getAttribute("privacy") as BeanPrivacy
    } else {
        BeanPrivacy.PUBLIC
    }

    /**
     * Returns `true` if this bean is deprecated
     */
    override val deprecated: Boolean = classInfo.hasAnnotation(Deprecated::class.java)

    /**
     * Returns `true` if this bean is primary
     */
    override val primary: Boolean = classInfo.hasAnnotation(ANNOTATION_PRIMARY)

    /**
     * Returns the priority of this bean definition
     */
    override val priority: Byte = if (classInfo.hasAnnotationClassname(ANNOTATION_PRIORITY)) {
        classInfo.getAnnotationContent(ANNOTATION_PRIORITY)!!.getAttribute("priority") as Byte
    } else {
        0
    }

    private val _implementations: MutableSet<LoadingBeanDefinition>? = if (isAbstract() || isOpen()) {
        LinkedHashSet()
    } else {
        null
    }

    fun addImplementation(implementationBean: LoadingBeanDefinition) {
        if (isAbstract() || isOpen()) {
            _implementations!!.add(implementationBean)
        } else {
            throw UnsupportedOperationException("$this is not an abstract bean")
        }
    }

    /**
     * Returns `true` if this is an open bean
     */
    override fun isOpen(): Boolean {
        return !classInfo.isFinal
    }

    /**
     * Returns `true` if this is an abstract bean
     */
    override fun isAbstract(): Boolean {
        return classInfo.isAbstract
    }

    /**
     * Return the implementation beans of this bean definition if this bean is an abstract bean
     */
    override fun implementations(): Collection<LoadingBeanDefinition> {
        if (isAbstract()) {
            return _implementations!!
        } else {
            throw UnsupportedOperationException("This is not an abstract bean")
        }
    }

    /**
     * Finish loading this bean definition and create a [BeanDefinition] from this loading
     * bean definition
     */
    override fun finishLoading(context: Context): BeanDefinition {
        val type = classInfo.loadClass().kotlin
        return if (!isAbstract()) {
            val implementations = _implementations?.map {
                if (it == this@ClassGraphLoadingScannedBeanDefinition) {
                    throw ContextInitializeException("Recursive bean definition loading ($it)", null)
                }
                it.finishLoading(context)
            }
            if (!classInfo.hasAnnotationClassname(ANNOTATION_LATEINIT)) {
                ScannedBeanDefinition(
                    type = type,
                    context = context,
                    annotations = classInfo.annotationInfo.map { it.loadClassAndInstantiate() },
                    name = name,
                    scope = scope,
                    deprecated = deprecated,
                    primary = primary,
                    privacy = privacy,
                    priority = priority,
                    isOpened = isOpen(),
                    implementations = implementations
                )
            } else {
                LateinitScannedBeanDefinition(
                    type = type,
                    context = context,
                    annotations = classInfo.annotationInfo.map { it.loadClassAndInstantiate() },
                    name = name,
                    scope = scope,
                    deprecated = deprecated,
                    primary = primary,
                    privacy = privacy,
                    lifecycle = classInfo.getAnnotationContent(ANNOTATION_LATEINIT)!!.getAttribute("lifecycle") as Int,
                    priority = priority,
                    isOpen = isOpen(),
                    implementations = implementations
                )
            }
        } else {
            val implementations = _implementations!!.map {
                if (it == this@ClassGraphLoadingScannedBeanDefinition) {
                    throw ContextInitializeException("Recursive bean definition loading ($it)", null)
                }
                it.finishLoading(context)
            }.sortedByDescending { it.priority }
            if (!classInfo.hasAnnotationClassname(ANNOTATION_LATEINIT)) {
                AbstractScannedBeanDefinition(
                    type = type,
                    context = context,
                    annotations = classInfo.annotationInfo.map { it.loadClassAndInstantiate() },
                    name = name,
                    scope = scope,
                    deprecated = deprecated,
                    primary = primary,
                    privacy = privacy,
                    priority = priority,
                    implementations = implementations
                )
            } else {
                AbstractLateinitScannedBeanDefinition(
                    type = type,
                    context = context,
                    annotations = classInfo.annotationInfo.map { it.loadClassAndInstantiate() },
                    name = name,
                    scope = scope,
                    deprecated = deprecated,
                    primary = primary,
                    privacy = privacy,
                    lifecycle = classInfo.getAnnotationContent(ANNOTATION_LATEINIT)!!.getAttribute("lifecycle") as Int,
                    priority = priority,
                    implementations = implementations
                )
            }
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ClassGraphLoadingScannedBeanDefinition

        return name == other.name
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }

    override fun toString(): String {
        return "ClassGraphLoadingScannedBeanDefinition(name='$name', scope=$scope, privacy=$privacy, deprecated=$deprecated, priority=$priority)"
    }
}

class ClassGraphLoadingAnnotatedBeanDefinition(val source: LoadingBeanDefinition, val methodInfo: MethodInfo) :
    ClassGraphLoadingBeanDefinition {
    /**
     * Returns the name of this bean
     */
    override val name: String = if (methodInfo.hasAnnotationClassname(ANNOTATION_COMPONENT)) {
        val name = methodInfo.getAnnotationContent(ANNOTATION_COMPONENT)!!.getAttribute("name") as String
        name.ifEmpty {
            getDefaultBeanName()
        }
    } else {
        getDefaultBeanName()
    }

    private fun getDefaultBeanName(): String {
        return methodInfo.name
    }

    /**
     * Annotations in this loading bean definition
     */
    override val annotations: AnnotationInfoList = methodInfo.annotationInfo

    /**
     * Returns the scope of this bean
     */
    override val scope: BeanScope = BeanScope.PROTOTYPE

    /**
     * Returns the bean privacy of this bean
     */
    override val privacy: BeanPrivacy = if (methodInfo.hasAnnotationClassname(ANNOTATION_PRIVACY)) {
        methodInfo.getAnnotationContent(ANNOTATION_PRIVACY)!!.getAttribute("privacy") as BeanPrivacy
    } else {
        BeanPrivacy.PUBLIC
    }

    /**
     * Returns `true` if this bean is deprecated
     */
    override val deprecated: Boolean = methodInfo.hasAnnotation(Deprecated::class.java)

    /**
     * Returns `true` if this bean is primary
     */
    override val primary: Boolean = methodInfo.hasAnnotation(ANNOTATION_PRIMARY)

    /**
     * Returns the priority of this bean definition
     */
    override val priority: Byte = if (methodInfo.hasAnnotationClassname(ANNOTATION_PRIORITY)) {
        methodInfo.getAnnotationContent(ANNOTATION_PRIORITY)!!.getAttribute("priority") as Byte
    } else {
        0
    }

    /**
     * Returns `true` if this is an open bean
     */
    override fun isOpen(): Boolean = false

    /**
     * Returns `true` if this is an abstract bean
     */
    override fun isAbstract(): Boolean = false

    /**
     * Return the implementation beans of this bean definition if this bean is an abstract bean
     */
    override fun implementations(): Collection<LoadingBeanDefinition> {
        throw UnsupportedOperationException("This is not an abstract bean")
    }

    /**
     * Finish loading this bean definition and create a [BeanDefinition] from this loading
     * bean definition
     */
    override fun finishLoading(context: Context): BeanDefinition {
        val method = methodInfo.loadClassAndGetMethod()
        val type = method.returnType.kotlin
        val providerKFunction =
            checkNotNull(method.kotlinFunction) { "Method should be able to represents as a kotlin function" }
        return if (!methodInfo.hasAnnotationClassname(ANNOTATION_LATEINIT)) {
            AnnotatedBeanDefinition(
                type = type,
                provider = source.finishLoading(context),
                providerKFunction = providerKFunction,
                context = context,
                annotations = methodInfo.annotationInfo.map { it.loadClassAndInstantiate() },
                name = name,
                scope = scope,
                deprecated = deprecated,
                primary = primary,
                privacy = privacy,
                priority = priority
            )
        } else {
            LateinitAnnotatedBeanDefinition(
                type = type,
                provider = source.finishLoading(context),
                providerKFunction = providerKFunction,
                context = context,
                annotations = methodInfo.annotationInfo.map { it.loadClassAndInstantiate() },
                name = name,
                scope = scope,
                deprecated = deprecated,
                primary = primary,
                privacy = privacy,
                lifecycle = methodInfo.getAnnotationContent(ANNOTATION_LATEINIT)!!.getAttribute("lifecycle") as Int,
                priority = priority
            )
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ClassGraphLoadingAnnotatedBeanDefinition

        return name == other.name
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }

    override fun toString(): String {
        return "ClassGraphLoadingScannedBeanDefinition(name='$name', scope=$scope, privacy=$privacy, deprecated=$deprecated, priority=$priority)"
    }
}