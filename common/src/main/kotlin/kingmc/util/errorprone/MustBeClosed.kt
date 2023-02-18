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

import java.lang.annotation.Documented

/**
 * Annotation for constructors of AutoCloseables or methods that return an AutoCloseable and require
 * that the resource is closed.
 *
 *
 * This is enforced by checking that invocations occur within the resource variable initializer
 * of a try-with-resources statement, which guarantees that the resource is always closed. The
 * analysis may be improved in the future to recognize other patterns where the resource will always
 * be closed.
 *
 *
 * Note that Android SDK versions prior to 19 do not support try-with-resources, so the
 * annotation should be avoided on APIs that may be used on Android, unless desugaring is used.
 */
@Documented
@Target(
    AnnotationTarget.CONSTRUCTOR,
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
annotation class MustBeClosed 