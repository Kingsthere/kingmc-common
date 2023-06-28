package kingmc.util

import kingmc.util.annotation.getAnnotation
import kingmc.util.annotation.hasAnnotation
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
    fun testAnnotationAttribute() {
        val annotation = TestClass1::class.getAnnotation<TestAnnotation1>()!!
        Assertions.assertTrue(annotation.arg1 == "value1")
        val annotation2 = TestClass2::class.getAnnotation<TestAnnotation1>()!!
        Assertions.assertTrue(annotation2.arg1 == "value3")
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
    class TestClass1 {

    }

    @TestAnnotation1("value3")
    class TestClass2 {

    }

    @TestAnnotation3
    class TestClass3 {

    }
}