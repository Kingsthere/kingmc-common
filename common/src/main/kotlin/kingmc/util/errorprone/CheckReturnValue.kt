/*
 * Copyright 2017 The Error Prone Authors.
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

/**
 * Indicates that the return value of the annotated method must be checked. An error is triggered
 * when one of these methods is called but the result is not used.
 *
 *
 * `@CheckReturnValue` may be applied to a class or package to indicate that all methods in
 * that class (including indirectly; that is, methods of inner classes within the annotated class)
 * or package must have their return values checked. For convenience, we provide an annotation,
 * [CanIgnoreReturnValue], to exempt specific methods or classes from this behavior.
 */
@Documented
@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER,
    AnnotationTarget.CONSTRUCTOR,
    AnnotationTarget.ANNOTATION_CLASS,
    AnnotationTarget.CLASS,
    AnnotationTarget.FILE
)
@Retention(
    RetentionPolicy.RUNTIME
)
annotation class CheckReturnValue 