/*
 * Copyright 2014 The Error Prone Authors.
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
package kingmc.util.errorprone.concurrent

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

/**
 * The method to which this annotation is applied releases one or more locks. The caller must hold
 * the locks when the function is entered, and will not hold them when it completes.
 *
 *
 * This annotation does not apply to built-in (synchronization) locks, which cannot be released
 * without being acquired in the same method.
 *
 *
 * The arguments determine which locks the annotated method releases:
 *
 *
 *  * `field-name`: The lock is referenced by the final instance field specified by
 * *field-name*.
 *  * `class-name.this.field-name`: For inner classes, it may be necessary to
 * disambiguate 'this'; the *class-name.this* designation allows you to specify which
 * 'this' reference is intended.
 *  * `class-name.field-name`: The lock is referenced by the static final field
 * specified by *class-name.field-name*.
 *  * `method-name()`: The lock object is returned by calling the named nullary
 * method.
 *
 *
 */
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@Retention(
    RetentionPolicy.CLASS
)
@Deprecated("the correctness of this annotation is not enforced; it will soon be removed.")
annotation class UnlockMethod(vararg val value: String)