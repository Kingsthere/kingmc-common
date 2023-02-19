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
 * Indicates that any concrete method that overrides the annotated method, directly or indirectly,
 * must invoke `super.theAnnotatedMethod(...)` at some point. This does not necessarily
 * require an *unconditional* call; any matching call appearing directly within the method body
 * (not inside an intervening class or lambda expression) is acceptable.
 *
 *
 * If the overriding method is itself overridable, applying this annotation to that method is
 * technically redundant, but may be helpful to readers.
 *
 *
 * **Preferred:** usually, a better solution is to make the method `final`, and have its
 * implementation delegate to a second concrete method which *is* overridable (or to a function
 * object which subclasses can specify). "Mandatory" statements remain in the final method while
 * "optional" code moves out. This is the only way to make sure the statements will be executed
 * unconditionally.
 */
@Documented
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@Retention(
    RetentionPolicy.CLASS
)
annotation class OverridingMethodsMustInvokeSuper 