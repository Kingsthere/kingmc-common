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

/**
 * The method to which this annotation is applied cannot be called.
 *
 *
 * The annotation is applied to methods that are required to satisfy the contract of an
 * interface, but that are not supported. One example is the implementation of [ ][java.util.Collection.add] in an immutable collection implementation.
 *
 *
 * Marking methods annotated with `@DoNotCall` as `@Deprecated` is recommended, since
 * it provides IDE users with more immediate feedback.
 */
@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
annotation class DoNotCall(
    /** An optional explanation of why the method should not be called.  */
    val value: String = ""
)