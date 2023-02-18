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

import java.lang.annotation.Documented

/**
 * Indicates that callers of this API should be inlined. That is, this API is trivially expressible
 * in terms of another API, for example a method that just calls another method.
 */
@Documented
@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER,
    AnnotationTarget.CONSTRUCTOR
)
annotation class InlineMe(
    /**
     * What the caller should be replaced with. Local parameter names can be used in the replacement
     * string. If you are invoking an instance method or constructor, you must include the implicit
     * `this` in the replacement body. If you are invoking a static method, you must include the
     * implicit `ClassName` in the replacement body.
     */
    val replacement: String,
    /** The new imports to (optionally) add to the caller.  */
    val imports: Array<String> = [],
    /** The new static imports to (optionally) add to the caller.  */
    val staticImports: Array<String> = []
)