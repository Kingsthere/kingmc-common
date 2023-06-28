package kingmc.common.context.initializer

import kingmc.common.context.*
import kingmc.common.context.annotation.*
import kingmc.common.context.beans.*
import kingmc.common.context.condition.*
import kingmc.common.context.process.afterDispose
import kingmc.common.context.process.disposeBean
import kingmc.common.context.process.inheritProcessors
import kingmc.common.context.process.loadProcessors
import kingmc.common.structure.ClassSource
import kingmc.util.annotation.getAnnotation
import kingmc.util.annotation.getAnnotations
import kingmc.util.annotation.hasAnnotation
import kingmc.util.reflect.findFunctionsByAnnotation
import kingmc.util.reflect.findMutablePropertiesByAnnotation
import java.util.function.Predicate
import kotlin.reflect.KAnnotatedElement
import kotlin.reflect.KClass
import kotlin.reflect.full.allSuperclasses
import kotlin.reflect.full.isSubclassOf


/**
 * A simple implementation of [ContextInitializer] allow to initialize [AbstractApplicationContext]s
 *
 * @since 0.0.5
 * @author kingsthere
 */
open class ApplicationContextInitializer(override val context: HierarchicalContext) : HierarchicalContextInitializer {
    protected var filter: Predicate<BeanDefinition> = Predicate { bean ->
        val annotations = bean.annotations
        // @ConditionalOnBean logic
        if (annotations.hasAnnotation<ConditionalOnBean>()) {
            val condition = annotations.getAnnotation<ConditionalOnBean>()!!
            return@Predicate if (condition.beanClass == Any::class) {
                hasInitializingBeanAvailable(condition.beanName)
            } else {
                hasInitializingBeanAvailable(condition.beanClass)
            }
        }
        // @ConditionalOnBeanMissing logic
        if (annotations.hasAnnotation<ConditionalOnBeanMissing>()) {
            val condition = annotations.getAnnotation<ConditionalOnBeanMissing>()!!
            return@Predicate !(if (condition.beanClass == Any::class) {
                hasInitializingBeanAvailable(condition.beanName)
            } else {
                hasInitializingBeanAvailable(condition.beanClass)
            })
        }
        if (annotations.hasAnnotation<ConditionalOnClass>()) {
            val condition = annotations.getAnnotation<ConditionalOnClass>()!!
            condition.value.forEach {
                try {
                    Class.forName(it)
                } catch (e: Exception) {
                    return@Predicate false
                }
            }
        }
        if (annotations.hasAnnotation<ConditionalOnClassMissing>()) {
            val condition = annotations.getAnnotation<ConditionalOnClassMissing>()!!
            condition.value.forEach {
                try {
                    Class.forName(it)
                    return@Predicate false
                } catch (_: Exception) {  }
            }
            return@Predicate true
        }
        return@Predicate true
    }

    protected var elementFilter: Predicate<KAnnotatedElement> = Predicate { element ->
        // @ConditionalOnBean logic
        if (element.hasAnnotation<ConditionalOnBean>()) {
            val condition = element.getAnnotation<ConditionalOnBean>()!!
            return@Predicate if (condition.beanClass == Any::class) {
                hasInitializingBeanAvailable(condition.beanName)
            } else {
                hasInitializingBeanAvailable(condition.beanClass)
            }
        }
        // @ConditionalOnBeanMissing logic
        if (element.hasAnnotation<ConditionalOnBeanMissing>()) {
            val condition = element.getAnnotation<ConditionalOnBeanMissing>()!!
            return@Predicate !(if (condition.beanClass == Any::class) {
                hasInitializingBeanAvailable(condition.beanName)
            } else {
                hasInitializingBeanAvailable(condition.beanClass)
            })
        }
        if (element.hasAnnotation<ConditionalOnClass>()) {
            val condition = element.getAnnotation<ConditionalOnClass>()!!
            condition.value.forEach {
                try {
                    Class.forName(it)
                } catch (e: Exception) {
                    return@Predicate false
                }
            }
        }
        if (element.hasAnnotation<ConditionalOnClassMissing>()) {
            val condition = element.getAnnotation<ConditionalOnClassMissing>()!!
            condition.value.forEach {
                try {
                    Class.forName(it)
                    return@Predicate false
                } catch (_: Exception) {  }
            }
            return@Predicate true
        }
        if (element.hasAnnotation<ConditionalOnProperty>()) {
            val condition = element.getAnnotation<ConditionalOnProperty>()!!
            val propertyValue = context.properties.getProperty(condition.property)
            return@Predicate propertyValue == condition.value
        }
        return@Predicate true
    }

    protected val sources: MutableList<Class<*>> = mutableListOf()
    protected val initializingBeans: MutableMap<String, BeanDefinition> =
        linkedMapOf()
    protected val scopedBeans: MutableMap<String, BeanDefinition> =
        linkedMapOf()

    /**
     * Add a filter to filter beans
     */
    override fun addBeanFilter(filter: Predicate<BeanDefinition>) {
        this.filter = this.filter.and(filter)
    }

    /**
     * Add a element filter
     */
    override fun addElementFilter(filter: Predicate<KAnnotatedElement>) {
        this.elementFilter = this.elementFilter.and(filter)
    }

    /**
     * Add a parent context to initializing context
     */
    override fun addParent(context: Context) {
        this.context.inheritParent(context)
        this.context.inheritProcessors(context)
    }

    /**
     * Add a bean source([ClassSource]) to this context
     */
    override fun addSource(classSource: ClassSource) {
        val classes = classSource.getClasses()
        sources.addAll(classes)
        classes.forEach { clazz ->
            try {
                defineBean(clazz, false)?.let { bean ->
                    this.initializingBeans[bean.name] = bean
                    this.scopedBeans[bean.name] = bean
                    defineBeansFromConfiguration(bean).forEach { configuredBean ->
                        this.initializingBeans[configuredBean.name] = configuredBean
                        this.scopedBeans[configuredBean.name] = configuredBean
                    }
                }
            }
            catch (_: KotlinReflectionNotSupportedError) { }
            catch (_: kotlin.reflect.jvm.internal.KotlinReflectionInternalError) { }
        }
    }

    /**
     * Define a bean from [Class]
     */
    fun defineBean(clazz: Class<out Any>, declared: Boolean = false): BeanDefinition? {
        val beanClass = clazz.kotlin

        // Check if the class is an annotation
        if (clazz.isAnnotation) {
            return null
        }

        if (!declared) {
            try {
                // If the bean is annotated with @Ignore then ignore
                if (beanClass.hasAnnotation<DisableScan>()) {
                    return null
                }

                // Check if the class is an available component
                if (!beanClass.hasAnnotation<Component>()) {
                    return null
                }
            } catch (e: UnsupportedOperationException) {
                // If the class is generated by kotlin compiler and
                // not able to use kotlin reflection, then return null
                return null
            } catch (e: ExceptionInInitializerError) {
                e.printStackTrace()
                return null
            }
        }

        // Get the scope of the component (by default is Shared)
        val scope = beanClass.getAnnotation<Scope>()?.scope ?: BeanScope.SHARED

        // Get the bean name
        val beanName: String = BeansUtil.getBeanName(beanClass)

        val lateinit: Lateinit? = beanClass.getAnnotation()

        val beanPrivacy: BeanPrivacy = beanClass.getAnnotation<Privacy>()?.privacy ?: BeanPrivacy.PUBLIC

        // Build and return the BeanDefinition
        val beanDefinition = if (lateinit == null || lateinit.lifecycle == 0) {
            ScannedGenericBeanDefinition(
                beanClass = beanClass,
                context = context,
                annotations = beanClass.annotations,
                scope = scope,
                isAbstract = beanClass.isAbstract,
                name = BeansUtil.getBeanName(beanClass),
                deprecated = beanClass.annotations.any { it.annotationClass == Deprecated::class },
                primary = beanClass.annotations.any { it.annotationClass == Primary::class },
                privacy = beanPrivacy
            )
        } else {
            LateinitScannedGenericBeanDefinition(
                beanClass = beanClass,
                context = context,
                annotations = beanClass.annotations,
                scope = scope,
                isAbstract = beanClass.isAbstract,
                name = BeansUtil.getBeanName(beanClass),
                deprecated = beanClass.annotations.any { it.annotationClass == Deprecated::class },
                primary = beanClass.annotations.any { it.annotationClass == Primary::class },
                privacy = beanPrivacy,
                lifecycle = lateinit.lifecycle
            )
        }

        this.whenBeanDefine(beanDefinition)

        return beanDefinition
    }

    /**
     * Define beans from configuration bean
     *
     * @since 0.0.5
     * @author kingsthere
     */
    fun defineBeansFromConfiguration(configurationBean: BeanDefinition, declared: Boolean = false): List<BeanDefinition> {
        val definedBeans: MutableList<BeanDefinition> =
            mutableListOf()
        if (!declared) {
            if (!configurationBean.beanClass.hasAnnotation<Configuration>()) {
                return emptyList()
            }
        }

        configurationBean.beanClass.findFunctionsByAnnotation<Bean>().forEach {
            val beanAnnotation: Bean = it.getAnnotation()!!

            val beanName: String = beanAnnotation.name.ifEmpty {
                it.name
            }

            val lateinit: Lateinit? = it.getAnnotation()

            val beanPrivacy: BeanPrivacy = it.getAnnotation<Privacy>()?.privacy ?: BeanPrivacy.PUBLIC

            val beanClass = it.returnType.classifier as KClass<*>

            val beanDefinition = if (lateinit == null || lateinit.lifecycle == 0) {
                AnnotatedGenericBeanDefinition(
                    beanClass = beanClass,
                    context = context,
                    configurationBean = configurationBean,
                    beanProvider = it,
                    annotations = beanClass.annotations,
                    scope = BeanScope.PROTOTYPE,
                    isAbstract = false,
                    name = beanName,
                    deprecated = beanClass.annotations.any { annotation -> annotation.annotationClass == Deprecated::class },
                    primary = beanClass.annotations.any { annotation -> annotation.annotationClass == Primary::class },
                    privacy = beanPrivacy
                )
            } else {
                LateinitAnnotatedGenericBeanDefinition(
                    beanClass = beanClass,
                    context = context,
                    configurationBean = configurationBean,
                    beanProvider = it,
                    annotations = beanClass.annotations,
                    scope = BeanScope.PROTOTYPE,
                    isAbstract = false,
                    name = beanName,
                    deprecated = beanClass.annotations.any { annotation -> annotation.annotationClass == Deprecated::class },
                    primary = beanClass.annotations.any { annotation -> annotation.annotationClass == Primary::class },
                    privacy = beanPrivacy,
                    lifecycle = lateinit.lifecycle
                )
            }

            this.whenBeanDefine(beanDefinition)

            // Build the BeanDefinition
            definedBeans.add(beanDefinition)
        }

        println("Class: $configurationBean, has: ${configurationBean.beanClass.hasAnnotation<Import>()}")
        // @Import beans
        if (configurationBean.beanClass.hasAnnotation<Import>()) {
            println("Class: ${configurationBean.beanClass} annotation: ${configurationBean.beanClass.getAnnotations<Import>()}")
            configurationBean.beanClass.getAnnotations<Import>().forEach { importAnnotation ->
                importAnnotation.value.forEach { importValue ->

                    defineBean(importValue.java, true)?.let { bean ->
                        println("Imported $bean")
                        this.initializingBeans[bean.name] = bean
                        this.scopedBeans[bean.name] = bean
                        defineBeansFromConfiguration(bean, true).forEach { configuredBean ->
                            println("Imported configured $configuredBean")
                            this.initializingBeans[configuredBean.name] = configuredBean
                            this.scopedBeans[configuredBean.name] = configuredBean
                        }
                    }
                }
            }
        }
        return definedBeans
    }

    /**
     * Populate beans to [instance]
     *
     * @since 0.0.5
     * @author kingsthere
     */
    fun populateBeans(context: ApplicationContext, bean: BeanDefinition, instance: Any) {
        // Get the properties that need to dependency injection
        bean.beanClass.findMutablePropertiesByAnnotation<Autowired>().forEach {
            try {
                val annotation = it.getAnnotation<Autowired>()!!
                val lateinit = it.getAnnotation<Lateinit>()
                val type = it.returnType
                val typeClass = it.returnType.classifier as KClass<*>
                val inject = {
                    if (typeClass.isSubclassOf(List::class)) {
                        // Populate the beans from container to a list
                        val listElementType = type.arguments[0].type?.classifier ?: throw PopulateBeansException("Unable to populate beans for $it")
                        // Get beans that is follow the element type of the list
                        val populatedBeans = context.findBeansByType(listElementType as KClass<*>).map { bean -> context.getBeanInstance(bean) }
                        // Wire beans by reflect
                        it.setter.call(instance, populatedBeans)
                    } else {
                        // Load bean name of the bean that dependent to inject if required
                        val beanName: String? = if (it.hasAnnotation<Qualifier>()) {
                            it.getAnnotation<Qualifier>()!!.value
                        } else {
                            null
                        }
                        val populatedBean = if (beanName != null) {
                            // Load the bean that dependent to inject
                            context.getBean(beanName)
                        } else {
                            // If the bean name is not specified then
                            // populate the bean by the type which is
                            // assignable
                            try {
                                context.getBean(it.returnType.classifier as KClass<*>)
                            } catch (_: NoBeanDefFoundException) {
                                null
                            }
                        }
                        // If the property is required the bean then check
                        // if the bean is null then throw exception
                        if (annotation.required) {
                            if (populatedBean == null) {
                                throw NoBeanDefFoundException("Could not find bean required by ${bean.beanClass.qualifiedName}.${it.name}", beanType = typeClass)
                            }
                        }
                        // Wire beans by reflect
                        it.setter.call(instance, populatedBean)
                    }
                }

                if (lateinit == null || lateinit.lifecycle == 0) {
                    inject()
                } else {
                    val lifecycle = context.getLifecycle()
                    lifecycle.insertPlan(lateinit.lifecycle, inject)
                }
            } catch (e: Exception) {
                throw PopulateBeansException("Unable to populate beans required by ${bean.beanClass.qualifiedName}.${it.name}", e)
            }
        }

    }

    private fun defineAbstractBeanImplementations() {
        initializingBeans.values.forEach {
            if (!it.isAbstract()) {
                val beanClass = it.beanClass
                beanClass.allSuperclasses.forEach { superclass ->
                    val superBean = findBeanByClass(superclass)
                    if (superBean != null) {
                        (superBean as ClassBeanDefinition).defineImplementation(it)
                    }
                }
            }
        }
    }

    /**
     * Check if this initializer has a bean is initializing and avaiable
     */
    fun hasInitializingBeanAvailable(beanName: String): Boolean {
        val bean = initializingBeans[beanName]
            ?: return false
        return isBeanAvailable(bean)
    }

    /**
     * Check if this initializer has a bean is initializing and avaiable
     */
    fun hasInitializingBeanAvailable(beanClass: KClass<*>): Boolean {
        val bean = initializingBeans.values.find { beanClass.isSubclassOf(it.beanClass) }
            ?: return false
        return isBeanAvailable(bean)
    }

    fun findBeanByClass(beanClass: KClass<*>): BeanDefinition? {
        return scopedBeans.values.find { beanClass.isSubclassOf(it.beanClass) }
    }

    /**
     * Check if a bean is available
     */
    fun isBeanAvailable(beanDefinition: BeanDefinition): Boolean {
        return filter.test(beanDefinition)
    }

    /**
     * Called when a bean defined
     */
    protected fun whenBeanDefine(beanDefinition: BeanDefinition) {  }

    /**
     * Invoke this context initializer and finish initializing of target context
     */
    override fun invoke() {
        defineAbstractBeanImplementations()

        (context as AbstractApplicationContext).addElementCondition(elementFilter)

        // Register initializing beans into the context
        initializingBeans.values.forEach {
            if (isBeanAvailable(it)) {
                (context as AbstractApplicationContext).registerBeanDefinition(it)
            }
        }

        (context as AbstractApplicationContext).refresh()

        // Load processors
        try {
            context.loadProcessors()
        } catch (e: Exception) {
            ContextInitializeException("Error while loading processors of context", e).printStackTrace()
        }

        // Populate beans
        for (beanDefinition in context) {
            if (beanDefinition.isSingleton()) {
                val beanInstance = context.getBeanInstance(beanDefinition)
                try {
                    populateBeans(context as AbstractApplicationContext, beanDefinition, beanInstance)
                } catch (populateBeansException: PopulateBeansException) {
                    populateBeansException.printStackTrace()
                } catch (e: Exception) {
                    throw PopulateBeansException("Unable to populate bean", e)
                }
            }
        }

    }

    /**
     * Called to dispose (close) the context
     */
    override fun dispose() {
        // Set up a set with instances of
        // classes in the project specified
        context
            .getProtectedBeans()
            .filter { it.isSingleton() }
            .forEach {
                val beanInstance = context.getBeanInstance(it)
                // Dispose single bean logic
                context.disposeBean(beanInstance)
            }
        context.afterDispose()
        // Close context
        context.close()
    }
}