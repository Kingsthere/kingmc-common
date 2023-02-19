package kingmc.util.annotation.impl.cglib

import kingmc.util.annotation.AliasFor
import kingmc.util.annotation.Extended
import kingmc.util.annotation.impl.GenericAnnotationNode
import kingmc.util.annotation.model.AnnotationAttribute
import kingmc.util.annotation.model.AnnotationNode
import kingmc.util.annotation.model.AnnotationNodeFactory
import kingmc.util.annotation.model.RawAnnotationAttribute
import kotlin.reflect.KClass
import kotlin.reflect.full.*

class CGLIBAnnotationNode(
    override val annotationClass: KClass<out Annotation>,
    annotationNodeFactory: AnnotationNodeFactory = CGLIBAnnotationNodeFactory
) : GenericAnnotationNode() {
    /**
     * [AnnotationNode] that this node is inherited from
     */
    override val inherited: List<AnnotationNode>

    /**
     * The attributes defined in this annotation
     */
    override val attributes: List<AnnotationAttribute>

    /**
     * The attributes defined in this annotation and [inherited]
     * annotations
     */
    override val declaredAttributes: List<AnnotationAttribute>

    init {
        fun loadRawAttributes(): List<List<RawAnnotationAttribute>> {
            return buildList {
                val batchRawAnnotationAttributes = mutableMapOf<String, MutableList<RawAnnotationAttribute>>()
                annotationClass.declaredMemberProperties
                    .forEach {
                        if (it.getter.hasAnnotation<kingmc.util.annotation.AliasFor>()) {
                            val built = CGLIBRawAnnotationAttribute(it.name)
                            val rawAnnotationAttributePointer = batchRawAnnotationAttributes.computeIfAbsent(it.name) { mutableListOf() }
                            rawAnnotationAttributePointer.add(built)
                            val aliasedRawAnnotationAttributePointer = batchRawAnnotationAttributes.computeIfAbsent(it.getter.findAnnotation<kingmc.util.annotation.AliasFor>()!!.attribute) { mutableListOf() }
                            aliasedRawAnnotationAttributePointer.add(built)
                        } else {
                            val rawAnnotationAttributePointer = batchRawAnnotationAttributes.computeIfAbsent(it.name) { mutableListOf() }
                            rawAnnotationAttributePointer.add(CGLIBRawAnnotationAttribute(it.name))
                        }
                    }
                batchRawAnnotationAttributes.forEach { (_, rawAnnotationAttributes) ->
                    add(rawAnnotationAttributes)
                }
            }
        }

        fun loadAttributes(): List<AnnotationAttribute> {
            val declaredRawAttributes = loadRawAttributes()
            return declaredRawAttributes
                .map { CGLIBAnnotationAttribute(it) }
        }

        if (annotationClass.hasAnnotation<Extended>()) {
            val extendedAnnotation: Extended = annotationClass.findAnnotation()!!
            this.inherited = extendedAnnotation.inheritedParents
                .toList()
                .map { annotationNodeFactory(it) }
            this.declaredAttributes = loadAttributes()
            this.attributes = this.declaredAttributes.plus(this.inherited.flatMap { it.attributes })
        } else {
            this.inherited = emptyList()
            this.declaredAttributes = loadAttributes()
            this.attributes = this.declaredAttributes
        }
    }

    override fun toString(): String {
        return "CGLIBAnnotationNode(annotationClass=$annotationClass, inherited=$inherited, attributes=$attributes, declaredAttributes=$declaredAttributes)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CGLIBAnnotationNode

        if (annotationClass != other.annotationClass) return false

        return true
    }

    override fun hashCode(): Int {
        return annotationClass.hashCode()
    }
}