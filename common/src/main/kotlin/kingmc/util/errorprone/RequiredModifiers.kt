/*
 * Copyright 2015 The Error Prone Authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package kingmc.util.errorprone

import java.lang.annotation.Documented
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import javax.lang.model.element.Modifier

/**
 * Annotation declaring that the target annotation requires all the specified modifiers. For
 * example, an annotation declared as:
 *
 * <pre>
 * @RequiredModifiers(modifier = Modifier.PUBLIC)
 * @interface MyAnnotation {}
</pre> *
 *
 *
 * will be considered illegal when used on non-public elements such as:
 *
 * <pre>
 * @MyAnnotation void foo() {}
</pre> *
 *
 * @author benyu@google.com (Jige Yu)
 */
@Documented
@Retention(RetentionPolicy.CLASS) // Element's source might not be available during analysis
@Target(AnnotationTarget.ANNOTATION_CLASS)
annotation class RequiredModifiers(
    @Deprecated("use {@link #modifier} instead") vararg val value: Modifier = [],
    /**
     * The required modifiers. The annotated element is illegal if any one or more of these modifiers
     * are absent.
     *
     *
     * Empty array has the same effect as not applying this annotation at all; duplicates are
     * allowed but have no effect.
     */
    val modifier: Array<Modifier> = []
)