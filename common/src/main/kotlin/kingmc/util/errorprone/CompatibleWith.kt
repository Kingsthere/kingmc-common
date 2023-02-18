/*
 * Copyright 2016 The Error Prone Authors.
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
 * Declares that a parameter to a method must be "compatible with" one of the type parameters in the
 * method's enclosing class, or on the method itself. "Compatible with" means that there can exist a
 * "reference casting conversion" from one type to the other (JLS 5.5.1).
 *
 *
 * For example, [Collection.contains] would be annotated as follows:
 *
 * <pre>`interface Collection<E> {
 * boolean contains(@CompatibleWith("E") Object o);
 * }
`</pre> *
 *
 *
 * To indicate that invocations of [Collection.contains] must be passed an argument whose
 * type is compatible with the generic type argument of the Collection instance:
 *
 * <pre>`Collection<String> stringCollection = ...;
 * boolean shouldBeFalse = stringCollection.contains(42); // BUG! int isn't compatible with String
`</pre> *
 *
 *
 * Note: currently, this annotation can't be used if the method overrides another method that has
 * `@CompatibleWith` already present.
 */
@MustBeDocumented
@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.VALUE_PARAMETER)
annotation class CompatibleWith(val value: String)