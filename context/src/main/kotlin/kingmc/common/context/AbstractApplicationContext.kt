package kingmc.common.context

import kingmc.common.context.beans.*
import kingmc.common.context.format.ContextFormatContext
import kingmc.util.*
import kingmc.util.format.FormatContext
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.function.Predicate
import kotlin.reflect.KAnnotatedElement
import kotlin.reflect.KClass
import kotlin.reflect.full.isSubclassOf

/**
 * An abstract implement for [ApplicationContext] to load
 * beans from [ClassSource][kingmc.common.structure.ClassSource]
 *
 * @since 0.0.1
 * @author kingsthere
 * @see ApplicationContext
 */
abstract class AbstractApplicationContext(override val properties: Properties, override val name: String = "unnamed") : ApplicationContext {
    /**
     * Protected bean definitions
     *
     * @since 0.0.1
     */
    protected val protectedBeanDefinitions: MutableMap<String, BeanDefinition> = ConcurrentHashMap()

    var owningBeanDefinitions: MutableMap<String, BeanDefinition> = computeOwningBeanDefinitions()

    private fun computeOwningBeanDefinitions(): MutableMap<String, BeanDefinition> {
        return this.protectedBeanDefinitions.toMutableMap().apply {
            this@AbstractApplicationContext.parents.forEach {
                it.asMap()
                        .filter { (_, bean) -> bean.scope == BeanScope.SINGLETON || bean.scope == BeanScope.PROTOTYPE }
                        .forEach { (name, bean) -> this[name] = bean }
            }
        }
    }

    /**
     * The filter to filter elements
     */
    protected var elementFilter: Predicate<KAnnotatedElement> = Predicate { true }

    /**
     * All bean definitions, include the beans from parent context
     */
    private var beanDefinitions: MutableMap<String, BeanDefinition> = computeBeanDefinitions()

    fun computeBeanDefinitions(): MutableMap<String, BeanDefinition> {
        return HashMap<String, BeanDefinition>().apply {
            putAll(protectedBeanDefinitions)
            parents.forEach {
                putAll(it.asMap())
            }
        }
    }

    /**
     * Refreshes [owningBeanDefinitions] & [beanDefinitions]
     */
    fun refresh() {
        owningBeanDefinitions = computeOwningBeanDefinitions()
        beanDefinitions = computeBeanDefinitions()
    }

    /**
     * The parents of this context
     */
    protected val parents: MutableSet<Context> = HashSet()

    /**
     * The lifecycle of this context
     */
    private val lifecycle: Lifecycle<Runnable> =
        lifecycleBuilder<Runnable>()
        .build()!!

    /**
     * The instance map to store singleton & protected class instances scoped in this context
     */
    internal val instanceMap: InstanceMap = SingletonMap()

    /**
     * The format context of this context
     */
     private val _formatContext: FormatContext = ContextFormatContext(this)

    /**
     * Register a bean definition into this application context
     *
     * @since 0.0.2
     */
    internal fun registerBeanDefinpition(beanDefinition: BeanDefinition) {
        this.protectedBeanDefinitions[beanDefinition.name] = beanDefinition
    }
    /**
     * Dispose a registered bean definition into this application context
     *
     * @since 0.0.2
     */
    internal fun disposeBeanDefinition(beanName: String) {
        this.protectedBeanDefinitions.remove(beanName)
    }

    /**
     * Get the lifecycle of this context
     */
    override fun getLifecycle(): Lifecycle<Runnable> =
        lifecycle

    override fun remove(name: String) {
        this.protectedBeanDefinitions.remove(name)
    }

    /**
     * Test the condition of an element is available
     */
    override fun testElementCondition(element: KAnnotatedElement): Boolean {
        return elementFilter.test(element)
    }

    /**
     * Get the format context that this holder holding
     */
    override fun getFormatContext(): FormatContext {
        return _formatContext
    }

    /**
     * Check a bean with specified type is existed
     * in this ioc container
     *
     * @param clazz the class of the bean
     * @since 0.0.1
     * @return is the bean exist
     */
    override fun <T : Any> hasBean(clazz: KClass<out T>): Boolean =
        this.beanDefinitions.any { (it.value as? ClassBeanDefinition)?.beanClass?.isSubclassOf(clazz) == true }

    /**
     * Check a bean with specified name is existed
     * in this ioc container
     *
     * @param name the class of the bean
     * @since 0.0.1
     * @return is the bean exist
     */
    override fun hasBean(name: String): Boolean =
        this.beanDefinitions.containsKey(name)

    /**
     * Get a bean with specified type in this container
     * by the type of that bean
     *
     * @param clazz the class
     * @param T the type of bean
     * @since 0.0.1
     * @return the bean got, `null` if the bean
     *         specified is not valid in this container
     */
    @Suppress("UNCHECKED_CAST")
    override fun <T : Any> getBean(clazz: KClass<out T>): T {
        var deprecatedBeanDefinition: BeanDefinition? = null
        var secondaryBeanDefinition: BeanDefinition? = null
        this.beanDefinitions.forEach { (_, definition) ->
            if (definition is ClassBeanDefinition) {
                if (definition.beanClass.isSubclassOf(clazz)) {
                    if (definition.primary) {
                        return getBeanInstance(definition) as T
                    } else if (!definition.deprecated) {
                        secondaryBeanDefinition = definition
                    } else {
                        deprecatedBeanDefinition = definition
                    }
                }
            }
        }
        return if (secondaryBeanDefinition != null) {
            getBeanInstance(secondaryBeanDefinition!!) as T
        } else if (deprecatedBeanDefinition != null) {
            getBeanInstance(deprecatedBeanDefinition!!) as T
        } else {
            throw NoBeanDefFoundException("No bean definition class assignable of $clazz found")
        }
    }

    override fun <T : Any> getBeans(clazz: KClass<out T>): List<T> {
        return buildList {
            beanDefinitions.forEach { (_, definition) ->
                if (definition is ClassBeanDefinition) {
                    if (definition.beanClass.isSubclassOf(clazz)) {
                        @Suppress("UNCHECKED_CAST")
                        ((getBeanInstance(definition) as? T)?.let { add(it) })
                    }
                }
            }
        }
    }

    /**
     * Get a bean with specified name in this container
     * by the name of that bean
     *
     * @param name the bean name
     * @param T the type of bean
     * @see LoadedBean.name
     * @since 0.0.1
     * @return the bean got, `null` if the bean
     *         specified is not valid in this container
     */
    override fun getBean(name: String): Any {
        val definition = this.beanDefinitions[name]
            ?: throw NoBeanDefFoundException("No bean definition named $name", beanName = name)
        return getBeanInstance(definition)
    }

    /**
     * Get a bean in this container
     * by the name of that bean with required type
     *
     * @param name the bean name
     * @param T the type of bean
     * @see LoadedBean.name
     * @since 0.0.1
     * @return the bean got, `null` if the bean
     *         specified is not valid in this container
     */
    override fun <T : Any> getBean(name: String, requiredType: KClass<out T>): T {
        val definition = this.beanDefinitions[name]
            ?: throw NoBeanDefFoundException("No bean definition named $name")
        @Suppress("UNCHECKED_CAST")
        return getBeanInstance(definition) as T
    }

    /**
     * Get all beans that in this container
     * and return as a [Collection]
     *
     * @since 0.0.1
     * @see Collection
     * @return the beans got
     */
    override fun getBeans(): Collection<Any> {
        return beanDefinitions.values.mapNotNull {
            try {
                getBeanInstance(it)
            } catch (e: Exception) {
                null
            }
        }
    }

    override fun getProtectedBeans(): List<BeanDefinition> {
        return protectedBeanDefinitions.values.toList()
    }

    /**
     * Gets the owning beans in this context, owning beans include
     * protected beans and beans inherited from parent that scoped `SINGLETON` or `PROTOTYPE`
     *
     * @since 0.0.5
     * @author kingsthere
     * @return the owning beans
     */
    override fun getOwningBeans(): List<BeanDefinition> {
        return owningBeanDefinitions.values.toList()
    }

    /**
     * Return the beans defined in this context as
     * a [Map]
     */
    override fun asMap(): MutableMap<String, BeanDefinition> =
        this.beanDefinitions

    override fun getBeanInstance(beanDefinition: BeanDefinition): Any {
        val raw = try {
            getRawBeanInstance(beanDefinition)
        } catch (e: Exception) {
            throw NoSuchBeanException("Unable to get/instantiate bean instance ($beanDefinition)", e)
        }
        return raw
    }

    protected open fun getRawBeanInstance(definition: BeanDefinition): Any {
        if (definition is LateinitBeanDefinition) {
            if (definition.lifecycle < this.lifecycle.cursor()) {
                throw BeanInitializeException("This bean is not available since ${definition.lifecycle} (current ${this.lifecycle.cursor()})")
            }
        }
        if (definition is ScannedGenericBeanDefinition) {
            if (definition.isAbstract()) {
                return getRawBeanInstance(definition.implementations().firstOrNull()
                    ?: throw IllegalArgumentException("No implementation of this abstract bean found"))
            }
            when (definition.scope) {
                BeanScope.SINGLETON -> {
                    // Instantiate the bean singleton instance isolated in this context
                    return ContextDefiner.runNotifyBeanToObject(this, definition.beanClass.java) { definition.beanClass.instance(instanceMap) }
                }
                BeanScope.SHARED -> {
                    return if (definition.context !== this) {
                        // If the shared bean is from other context
                        val sourceContext = definition.context
                        if (sourceContext is AbstractApplicationContext) {
                            ContextDefiner.runNotifyBeanToObject(sourceContext, definition.beanClass.java) { definition.beanClass.instance(sourceContext.instanceMap) }
                        } else {
                            sourceContext.getBeanInstance(definition)
                        }
                    } else {
                        ContextDefiner.runNotifyBeanToObject(this, definition.beanClass.java) { definition.beanClass.instance(instanceMap) }
                    }
                }
                BeanScope.GLOBAL -> {
                    return definition.beanClass.instance(GlobalBeanInstanceMap)
                }
                else -> {
                    // Instantiate prototype bean
                    return ContextDefiner.runNotifyBeanToObject(this, definition.beanClass.java) { definition.beanClass.instance(PrototypeMap) }
                }
            }
        } else if (definition is AnnotatedGenericBeanDefinition) {
            return callFunctionWithContext(definition.beanProvider, getBeanInstance(definition.configurationBean))!!
        }
        throw IllegalArgumentException("Unsupported bean definition")
    }

    /**
     * Add a element condition to this context
     */
    fun addElementCondition(elementFilter: Predicate<KAnnotatedElement>) {
        this.elementFilter = elementFilter.and(elementFilter)
    }

    override fun <T : Any> remove(type: KClass<out T>) {
        // Load bean name from the class
        this.protectedBeanDefinitions.forEach { (name, definition) ->
            (definition as? ClassBeanDefinition)?.let {
                if (definition.beanClass.isSubclassOf(type)) {
                    this.protectedBeanDefinitions.remove(name)
                }
            }
        }
    }

    /**
     * Returns an iterator over the elements of this object.
     */
    override fun iterator(): Iterator<BeanDefinition> {
        return (this.beanDefinitions.values).iterator()
    }

    override fun inheritParent(context: Context) {
        this.parents.add(context)
    }

    override fun listParents(): Set<Context> {
        return this.parents
    }

    override fun close() {
        this.protectedBeanDefinitions.clear()
    }

    override fun toString(): String {
        return "GenericApplicationContext(beans=${beanDefinitions.size}, parents=$parents, name=$name)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AbstractApplicationContext

        if (name != other.name) return false
        if (protectedBeanDefinitions != other.protectedBeanDefinitions) return false
        if (parents != other.parents) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + protectedBeanDefinitions.hashCode()
        result = 31 * result + parents.hashCode()
        return result
    }


}