package kingmc.util.annotation.impl

/**
 * Default ignored annotations to prevent unnecessary annotations scanned by [AnnotationContentFactoryImpl]
 */
val IGNORED_ANNOTATIONS = arrayOf(
    "kotlin.annotation.Retention",
    "kotlin.annotation.Target",
    "kotlin.annotation.MustBeDocumented",
    "kotlin.annotation.Repeatable",
    "kotlin.annotation.Metadata",
    "kotlin.jvm.internal.SourceDebugExtension",
    "java.lang.annotation.Documented",
    "java.lang.annotation.Inherited",
    "java.lang.annotation.Target",
    "java.lang.annotation.Repeatable",
    "java.lang.annotation.Retention",
)

/**
 * Default ignored annotations attributes to prevent unnecessary annotations attributes such
 * as [toString] or [hashCode], [equals]
 */
val IGNORED_ATTRIBUTES = arrayOf(
    "toString",
    "hashCode",
    "equals"
)