package kingmc.common.context

import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap
import kingmc.common.context.annotation.Autowired
import kingmc.common.context.annotation.Lateinit
import kingmc.common.context.annotation.Qualifier
import kingmc.common.context.beans.*
import kingmc.common.context.exception.*
import kingmc.common.context.format.formatContexts
import kingmc.common.context.process.*
import kingmc.util.*
import kingmc.util.annotation.getAnnotation
import kingmc.util.annotation.hasAnnotation
import kingmc.util.format.FormatContext
import kingmc.util.lifecycle.Execution
import kingmc.util.lifecycle.Lifecycle
import kingmc.util.reflect.findMutablePropertiesByAnnotation
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import kotlin.reflect.KClass
import kotlin.reflect.full.isSubclassOf

/**
 * A `ApplicationContext` implementation loads beans from `BeanSource`
 *
 * @author kingsthere
 * @since 0.0.1
 * @see ApplicationContext
 */
open class BeanSourceApplicationContext(
    val beanSource: BeanSource,
    override val properties: Properties,
    override val name: String = "unnamed",
    parents: Set<Context> = emptySet()
) : ApplicationContext {
    /**
     * The parents of this context
     */
    protected val parents: MutableSet<Context> = HashSet(parents)

    /**
     * Processors in this context
     */
    override val processors: MutableMap<Int, MutableList<BeanProcessor>> = Int2ObjectArrayMap(5)

    /**
     * The lifecycle of this context
     */
    private val lifecycle: Lifecycle = Lifecycle()

    /**
     * The instance map to store singleton & protected class instances scoped in this context
     */
    internal val instanceMap: InstanceMap = AutowireCapableSingletonMap(this)

    /**
     * Protected bean definitions
     *
     * @since 0.0.1
     */
    protected val protectedBeanDefinitions: MutableMap<String, BeanDefinition> = ConcurrentHashMap()

    /**
     * Protected bean definitions cached by class
     *
     * @since 0.1.0
     */
    protected val protectedBeanDefinitionsByClass: MutableMap<KClass<*>, BeanDefinition> = ConcurrentHashMap()

    /**
     * Load this context from the bean source, this method should only called once
     */
    fun load() {
        beanSource.finishLoading(this).forEach {
            registerBeanDefinition(it)
        }
        refresh()
    }

    var owningBeanDefinitions: MutableMap<String, BeanDefinition> = computeOwningBeanDefinitions()

    private fun computeOwningBeanDefinitions(): MutableMap<String, BeanDefinition> {
        return this.protectedBeanDefinitions.toMutableMap().apply {
            this@BeanSourceApplicationContext.parents.forEach {
                it.asMap()
                    .forEach { (name, bean) ->
                        if ((bean.scope == BeanScope.SINGLETON || bean.scope == BeanScope.PROTOTYPE) && bean.privacy != BeanPrivacy.PRIVATE) {
                            this[name] = bean
                        }
                    }
            }
        }
    }

    /**
     * All bean definitions, include the beans from parent context
     */
    private var beanDefinitions: MutableMap<String, BeanDefinition> = computeBeanDefinitions()

    /**
     * All bean definitions, include the beans from parent context cached by it class
     */
    private var beanDefinitionsByClass: MutableMap<KClass<*>, BeanDefinition> = computeBeanDefinitionsByClass()

    fun computeBeanDefinitions(): MutableMap<String, BeanDefinition> {
        return HashMap<String, BeanDefinition>().apply {
            putAll(protectedBeanDefinitions)
            parents.forEach {
                it.asMap().forEach { (name, bean) ->
                    if (bean.privacy != BeanPrivacy.PRIVATE) {
                        put(name, bean)
                    }
                }
            }
        }
    }

    fun computeBeanDefinitionsByClass(): MutableMap<KClass<*>, BeanDefinition> {
        return HashMap<KClass<*>, BeanDefinition>().apply {
            putAll(protectedBeanDefinitionsByClass)
            parents.forEach {
                it.asMap().forEach { (_, bean) ->
                    if (bean.privacy != BeanPrivacy.PRIVATE) {
                        put(bean.type, bean)
                    }
                }
            }
        }
    }

    /**
     * The format context of this context
     */
    private var _formatContext: FormatContext = FormatContext().with(formatContexts)

    /**
     * Refreshes [owningBeanDefinitions] & [beanDefinitions], this function should be invoked
     * after [registerBeanDefinition] into this context
     */
    fun refresh() {
        owningBeanDefinitions = computeOwningBeanDefinitions()
        beanDefinitions = computeBeanDefinitions()
        beanDefinitionsByClass = computeBeanDefinitionsByClass()
        _formatContext = FormatContext().with(formatContexts)
    }

    init {
        loadProcessors()
        populateBeans()
    }

    // TODO Resources api for context
    // fun loadResources() {
    //     this.protectedBeanDefinitions.forEach { (_, beanDefinition) ->
    //         val type = beanDefinition.type
    //         if (beanDefinition.isDependencyInjectable() && type.isSubclassOf(Resource::class)) {
    //             val beanInstance = getBeanInstance(beanDefinition)
    //
    //         }
    //     }
    // }

    fun populateBeans() {
        this.protectedBeanDefinitions.forEach { (_, beanDefinition) ->
            if (beanDefinition.isDependencyInjectable()) {
                val beanInstance = getBeanInstance(beanDefinition)
                populate(beanInstance)
            }
        }
    }

    /**
     * Populate dependencies to the given [bean] instance
     */
    fun populate(bean: Any) {
        // Get the properties that need to dependency injection
        bean::class.findMutablePropertiesByAnnotation<Autowired>().forEach {
            try {
                val annotation = it.getAnnotation<Autowired>()!!
                val lateinit = it.getAnnotation<Lateinit>()
                val type = it.returnType
                val typeClass = it.returnType.classifier as KClass<*>
                val inject = {
                    if (typeClass.isSubclassOf(List::class)) {
                        // Populate the beans from container to a list
                        val listElementType = type.arguments[0].type?.classifier as? KClass<*> ?: throw PopulateBeansException("Unable to populate beans for $it")

                        // Populate implementation beans
                        val populatedBeans = beanDefinitionsByClass[listElementType]

                        // Wire beans by reflection
                        it.setter.call(listElementType, populatedBeans)
                    } else {
                        // Load bean name of the bean that dependent to inject if required
                        val beanName: String? = if (it.hasAnnotation<Qualifier>()) {
                            it.getAnnotation<Qualifier>()!!.value
                        } else {
                            null
                        }
                        val populatedBean = if (beanName != null) {
                            // Load the bean that dependent to inject
                            getBean(beanName)
                        } else {
                            // If the bean name is not specified, then
                            // populate the bean by the type which is
                            // assignable
                            try {
                                getBean(it.returnType.classifier as KClass<*>)
                            } catch (_: NoSuchBeanException) {
                                null
                            }
                        }
                        // If the property is required the bean then check
                        // if the bean is null then throw exception
                        if (annotation.required) {
                            if (populatedBean == null) {
                                throw NoSuchBeanException("Could not find bean required by ${bean::class.qualifiedName}.${it.name}", beanType = typeClass, beanName = beanName)
                            }
                        }
                        // Populate bean by reflection
                        it.setter.call(bean, populatedBean)
                    }
                }

                if (lateinit == null || lateinit.lifecycle == 0) {
                    inject()
                } else {
                    val lifecycle = getLifecycle()
                    lifecycle.scheduleExecution(lateinit.lifecycle, Execution(1, "dependency_injection", inject))
                }
            } catch (e: Exception) {
                throw PopulateBeansException("Unable to populate beans required by ${bean::class.qualifiedName}.${it.name}", e)
            }
        }
    }

    /**
     * Register a bean definition into this application context
     *
     * @since 0.1.1
     */
    fun registerBeanDefinition(beanDefinition: BeanDefinition) {
        this.protectedBeanDefinitions[beanDefinition.name] = beanDefinition
        this.protectedBeanDefinitionsByClass[beanDefinition.type] = beanDefinition
    }

    /**
     * Get the lifecycle of this context
     */
    override fun getLifecycle(): Lifecycle =
        lifecycle

    /**
     * Get the format context that this holder holding
     */
    override fun getFormatContext(): FormatContext {
        return _formatContext
    }

    override fun hasBean(clazz: KClass<*>): Boolean {
        val beanDefinition = this.beanDefinitionsByClass[clazz] ?: return false
        return !beanDefinition.isAbstract() || findImplementationBean(beanDefinition) != null
    }

    override fun hasBean(name: String): Boolean {
        val beanDefinition = this.beanDefinitions[name] ?: return false
        return !beanDefinition.isAbstract() || findImplementationBean(beanDefinition) != null
    }

    override fun hasBeanDefinition(clazz: KClass<*>): Boolean =
        this.beanDefinitionsByClass.containsKey(clazz)

    override fun hasBeanDefinition(name: String): Boolean =
        this.beanDefinitions.containsKey(name)

    @Suppress("UNCHECKED_CAST")
    override fun <TBean : Any> getBean(clazz: KClass<out TBean>): TBean? {
        this.beanDefinitionsByClass[clazz]?.let { definition ->
            // Check access
            if (canAccess(definition)) {
                // Return bean instance
                return getBeanInstance(definition) as TBean
            }
        }
        return null
    }

    override fun getBean(name: String): Any? {
        this.beanDefinitions[name]?.let { definition ->
            // Check access
            if (canAccess(definition)) {
                // Return bean instance
                return getBeanInstance(definition)
            }
        }
        return null
    }

    override fun getBeanDefinitions(): Collection<BeanDefinition> = beanDefinitions.values

    override fun getBeanDefinition(name: String): BeanDefinition? {
        return beanDefinitions[name]
    }

    override fun getBeanDefinition(clazz: KClass<*>): BeanDefinition? {
        return beanDefinitions.values.find { it.type.isSubclassOf(clazz) }
    }

    override fun getProtectedBeans(): List<BeanDefinition> {
        return protectedBeanDefinitions.values.toList()
    }

    override fun getOwningBeans(): List<BeanDefinition> {
        return owningBeanDefinitions.values.toList()
    }

    override fun asMap(): MutableMap<String, BeanDefinition> =
        this.beanDefinitions

    override fun getBeanInstance(beanDefinition: BeanDefinition): Any {
        val raw = try {
            getRawBeanInstance(beanDefinition)
        } catch (e: Exception) {
            throw BeanInstantiateException("Unable to get/instantiate bean instance ($beanDefinition)", e)
        }
        return raw
    }

    protected open fun getRawBeanInstance(definition: BeanDefinition): Any {
        // Check permission
        if (!canAccess(definition)) {
            throw IllegalBeanAccessException("Unable to access bean $definition")
        }

        // Check lateinit
        if (definition is LateinitBeanDefinition) {
            if (definition.lifecycle < this.lifecycle.cursor) {
                throw LateinitBeanException("This bean is not available since ${definition.lifecycle} (current ${this.lifecycle.cursor})")
            }
        }

        if (definition is ScannedBeanDefinition) {
            if (definition.isAbstract()) {
                // Try to get implementation bean
                return getRawBeanInstance(findImplementationBean(definition)
                    ?: throw IllegalArgumentException("No implementation of this abstract bean found"))
            }
            // Handle open beans
            if (definition.isOpen()) {
                val overridden = findOverriddenBean(definition)
                if (overridden != definition) {
                    return getRawBeanInstance(findOverriddenBean(definition))
                }
            }
            when (definition.scope) {
                BeanScope.SINGLETON -> {
                    // Instantiate the bean singleton instance isolated in this context
                    return ContextDefiner.runNotifyBeanToObject(this, definition.type.java) { definition.type.instance(instanceMap) }
                }
                BeanScope.SHARED -> {
                    return if (definition.context !== this) {
                        // If the shared bean is from another context
                        val sourceContext = definition.context
                        if (sourceContext is BeanSourceApplicationContext) {
                            // Instantiate
                            ContextDefiner.runNotifyBeanToObject(sourceContext, definition.type.java) { definition.type.instance(sourceContext.instanceMap) }
                        } else {
                            // Incompatible context
                            throw IllegalArgumentException("Incompatible context for $definition")
                        }
                    } else {
                        // Instantiate
                        ContextDefiner.runNotifyBeanToObject(this, definition.type.java) { definition.type.instance(instanceMap) }
                    }
                }
                BeanScope.GLOBAL -> {
                    return definition.type.instance(GlobalBeanInstanceMap)
                }
                else -> {
                    // Instantiate prototype bean
                    return ContextDefiner.runNotifyBeanToObject(this, definition.type.java) { definition.type.instance(PrototypeMap) }
                }
            }
        } else if (definition is AnnotatedBeanDefinition) {
            return callFunctionWithContext(definition.providerKFunction, getBeanInstance(definition.provider))!!
        }
        throw IllegalArgumentException("Unsupported bean definition")
    }

    /**
     * Check if this context has the right to access the given bean
     */
    override fun canAccess(beanDefinition: BeanDefinition): Boolean {
        return if (beanDefinition.privacy == BeanPrivacy.PRIVATE) {
            beanDefinition.context == this
        } else {
            true
        }
    }

    /**
     * Returns an iterator over the elements of this object.
     */
    override fun iterator(): Iterator<BeanDefinition> {
        return getBeanDefinitions().iterator()
    }

    override fun inheritParent(context: Context) {
        this.parents.add(context)
    }

    override fun remove(beanDefinition: BeanDefinition) {
        val beanName = beanDefinition.name
        val beanType = beanDefinition.type
        this.beanDefinitions.remove(beanName)
        this.beanDefinitionsByClass.remove(beanType)
        this.protectedBeanDefinitions.remove(beanName)
        this.protectedBeanDefinitionsByClass.remove(beanType)
        if (beanType.isSubclassOf(BeanProcessor::class) && beanDefinition.isDependencyInjectable()) {
            this.removeProcessor(getBeanInstance(beanDefinition) as BeanProcessor)
        }
    }

    override fun dispose() {
        disposeBeans()
        afterDispose()
        clear()
    }

    override fun clear() {
        this.beanDefinitions.clear()
        this.beanDefinitionsByClass.clear()
        this.protectedBeanDefinitions.clear()
        this.protectedBeanDefinitionsByClass.clear()
        this.owningBeanDefinitions.clear()
        this.processors.clear()
    }

    override fun listParents(): Set<Context> {
        return this.parents
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BeanSourceApplicationContext

        return name == other.name
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }

    override fun toString(): String {
        return "BeanSourceApplicationContext(beanSource=$beanSource,name='$name')"
    }
}