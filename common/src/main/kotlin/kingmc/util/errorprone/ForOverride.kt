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

/**
 * Indicates that the annotated method is provided only to be overridden: it should not be
 * *invoked* from outside its declaring source file (as if it is `private`), and
 * overriding methods should not be directly invoked at all. Such a method represents a contract
 * between a class and its *subclasses* only, and is not to be considered part of the
 * *caller*-facing API of either class.
 *
 *
 * The annotated method must have protected or package-private visibility, and must not be `static`, `final` or declared in a `final` class. Overriding methods must have either
 * protected or package-private visibility, although their effective visibility is actually "none".
 */
@Documented
@IncompatibleModifiers(modifier = [Modifier.PUBLIC, Modifier.PRIVATE, Modifier.STATIC, Modifier.FINAL])
@Retention(
    RetentionPolicy.CLASS
) // Parent source might not be available while compiling subclass
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
annotation class ForOverride 