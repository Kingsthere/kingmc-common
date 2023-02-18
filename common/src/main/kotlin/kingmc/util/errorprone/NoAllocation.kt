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
package kingmc.util.errorprone

import java.lang.annotation.Documented

/**
 * Annotation for method declarations, which denotes that this method will not cause allocations
 * that are visible from source code. Compilers or runtimes can still introduce opportunities for
 * allocation to occur that might result in garbage collection.
 *
 *
 * Be careful using this annotation. It should be used sparingly, typically only for methods
 * called within inner loops or user interface event handlers. Misuse will likely lead to decreased
 * performance and significantly more complex code.
 */
@Documented
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
annotation class NoAllocation 