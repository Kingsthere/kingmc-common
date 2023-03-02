package kingmc.util

/**
 * A marker annotations for DSLs
 */
@DslMarker
@Target(AnnotationTarget.CLASS, AnnotationTarget.TYPEALIAS, AnnotationTarget.TYPE, AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.SOURCE)
@MustBeDocumented
annotation class KingMCDsl

/**
 * API marked with this annotation is internal, and it is not intended to be used outside kingmc.
 */
@RequiresOptIn(
    level = RequiresOptIn.Level.ERROR,
    message = "This API is internal in KingMC and should not be used. It could be removed or changed without notice."
)
@Target(
    AnnotationTarget.CLASS,
    AnnotationTarget.TYPEALIAS,
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY,
    AnnotationTarget.FIELD,
    AnnotationTarget.CONSTRUCTOR,
    AnnotationTarget.PROPERTY_SETTER,
    AnnotationTarget.PROPERTY_SETTER
)
@Retention
@MustBeDocumented
annotation class InternalAPI

/**
 * Represent a utility class, the classes annotated with [@Utility][Utility] should
 * not be instantiated by anyone. In kotlin the classes that is annotated with this
 * should is a [object](https://kotlinlang.org/docs/object-declarations.html).
 * A utility class in kotlin should like this:
 * ```
 * @Utility
 * class UtilityClass {
 *     // ...
 * }
 * ```
 *
 * In java classes the classes annotated with this should have a [**private constructor**][java.lang.reflect.Constructor]
 * so the classes won't instantiate the class, for example:
 * ```java
 * @Utility
 * public class UtilityClass {
 *     /** This class should not be instantiate */
 *     private UtilityClass() {
 *
 *     }
 *
 *     // ...
 * }
 * ```
 *
 * @since 0.0.1
 * @author kingsthere
 */
@Singleton
@Retention
@Target(
    AnnotationTarget.CLASS, AnnotationTarget.FILE
)
@MustBeDocumented
annotation class Utility

/**
 * Represent a class that should not instantiate over **1 time**, the
 * singleton class should store in a **static class** or
 * [object](https://kotlinlang.org/docs/object-declarations.html).
 *
 *
 * The singletons should have a getter, in most time the
 * getter should be a **function(method in java)**, for example:
 * ```
 * @Singleton
 * class SingletonObject {
 *     // ...
 * }
 * ```
 * The singleton getter should like:
 * ```
 * object SingletonGetter {
 *     // The var store singleton instance
 *     val singleton: SingletonObject = Singleton()
 *     // Getter function to get singleton
 *     fun getSingleton() : SingletonObject {
 *         return singleton;
 *     }
 *
 *     // ...
 * }
 * ```
 *
 *
 * @since 0.0.1
 * @author kingsthere
 */
@Retention
@Target(
    AnnotationTarget.CLASS
)
@MustBeDocumented
annotation class Singleton

/**
 * Represent an interface or abstract class that the subclasses extends
 * from **must is a singleton or object**, is equivalent to the subclasses
 * is all annotated with [Singleton]
 *
 * @author kingsthere
 * @since 0.0.1
 */
@Retention
@Target(
    AnnotationTarget.CLASS
)
@MustBeDocumented
annotation class SubclassSingleton