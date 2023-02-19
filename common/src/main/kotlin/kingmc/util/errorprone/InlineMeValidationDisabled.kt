/*
 * Copyright 2021 The Error Prone Authors.
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

/**
 * An annotation that disables validation of the [InlineMe] annotation's correctness (i.e.:
 * that it accurately represents an inlining of the annotated method).
 */
@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER,
    AnnotationTarget.CONSTRUCTOR
)
annotation class InlineMeValidationDisabled(
    /**
     * An explanation as to why the validation is disabled (e.g.: moving from a constructor to a
     * static factory method that delegates to this constructor, which is behavior-perserving, but
     * isn't strictly an inlining).
     */
    val value: String
)