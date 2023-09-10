package kingmc.util

import io.github.classgraph.ClassGraph
import kingmc.util.annotation.getAnnotation
import kingmc.util.annotation.getAnnotationContent
import kingmc.util.annotation.hasAnnotation
import kingmc.util.annotation.hasAnnotationClassname
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class AnnotationTest {
    @Test
    fun testHasAnnotation() {
        Assertions.assertTrue(TestClass1::class.hasAnnotation<TestAnnotation1>())
        Assertions.assertFalse(TestClass2::class.hasAnnotation<TestAnnotation2>())
        Assertions.assertFalse(TestClass3::class.hasAnnotation<TestAnnotation1>())
    }

    @Test
    fun testHasAnnotationClassGraph() {
        val classes = ClassGraph()
            .acceptClasses("kingmc.util.AnnotationTest\$TestClass1")
            .acceptClasses("kingmc.util.AnnotationTest\$TestClass2")
            .acceptClasses("kingmc.util.AnnotationTest\$TestClass3")
            .enableAnnotationInfo()
            .scan()
        val testClass1 = classes.getClassInfo("kingmc.util.AnnotationTest\$TestClass1")
        val testClass2 = classes.getClassInfo("kingmc.util.AnnotationTest\$TestClass2")
        val testClass3 = classes.getClassInfo("kingmc.util.AnnotationTest\$TestClass3")
        Assertions.assertTrue(testClass1.hasAnnotationClassname("kingmc.util.AnnotationTest\$TestAnnotation1"))
        Assertions.assertFalse(testClass2.hasAnnotationClassname("kingmc.util.AnnotationTest\$TestAnnotation2"))
        Assertions.assertFalse(testClass3.hasAnnotationClassname("kingmc.util.AnnotationTest\$TestAnnotation1"))
    }

    @Test
    fun testAnnotationAttribute() {
        val annotation = TestClass1::class.getAnnotation<TestAnnotation1>()!!
        Assertions.assertTrue(annotation.arg1 == "value1")
        val annotation2 = TestClass2::class.getAnnotation<TestAnnotation1>()!!
        Assertions.assertTrue(annotation2.arg1 == "value3")
    }

    @Test
    fun testHasAnnotationAttributeClassGraph() {
        val classes = ClassGraph()
            .acceptClasses("kingmc.util.AnnotationTest\$TestClass1")
            .acceptClasses("kingmc.util.AnnotationTest\$TestClass2")
            .enableAnnotationInfo()
            .scan()
        val testClass1 = classes.getClassInfo("kingmc.util.AnnotationTest\$TestClass1")
        val testClass2 = classes.getClassInfo("kingmc.util.AnnotationTest\$TestClass2")
        val annotation = testClass1.getAnnotationContent("kingmc.util.AnnotationTest\$TestAnnotation1")!!
        Assertions.assertTrue(annotation.getAttribute("arg1") == "value1")
        val annotation2 = testClass2.getAnnotationContent("kingmc.util.AnnotationTest\$TestAnnotation1")!!
        Assertions.assertTrue(annotation2.getAttribute("arg1") == "value3")
    }

    @Retention
    @Target(AnnotationTarget.CLASS, AnnotationTarget.ANNOTATION_CLASS)
    annotation class TestAnnotation1(
        val arg1: String
    )

    @Retention
    @Target(AnnotationTarget.CLASS)
    @TestAnnotation1("value1")
    annotation class TestAnnotation2(
        val arg2: String
    )

    @Retention
    @Target(AnnotationTarget.CLASS)
    annotation class TestAnnotation3

    @TestAnnotation2("value2")
    class TestClass1

    @TestAnnotation1("value3")
    class TestClass2

    @TestAnnotation3
    class TestClass3
}