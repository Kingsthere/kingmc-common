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
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

/**
 * Annotation for a method that takes a printf-style format string as an argument followed by
 * arguments for that format string.
 *
 *
 * This annotation is used in conjunction with [FormatString] to denote a method that takes
 * a printf-style format string and its format arguments. In any method annotated as `FormatMethod` without a [FormatString] parameter, the first `String` parameter is
 * assumed to be the format string. For example, the following two methods are equivalent:
 *
 * <pre>
 * &#064;FormatMethod void log(Locale l, &#064;FormatString String logMessage, Object... args) {}
 * &#064;FormatMethod void log(Locale l, String logMessage, Object... args) {}
</pre> *
 *
 *
 * Using [FormatMethod] on a method header will ensure the following for the parameters
 * passed to the method:
 *
 *
 *  1. A format string is either:
 *
 *  * A compile time constant value (see [CompileTimeConstant] for more info).
 *
 * The following example is valid:
 * <pre>
 * public class Foo {
 * static final String staticFinalLogMessage = "foo";
 * &#064;FormatMethod void log(&#064;FormatString String format, Object... args) {}
 * void validLogs() {
 * log("String literal");
 * log(staticFinalLogMessage);
 * }
 * }</pre>
 *
 * However the following would be invalid:
 * <pre>
 * public class Foo{
 * &#064;FormatMethod void log(&#064;FormatString String format, Object... args) {}
 * void invalidLog(String notCompileTimeConstant) {
 * log(notCompileTimeConstant);
 * }
 * }</pre>
 *  * An effectively final variable that was assigned to a compile time constant value.
 * This is to permit the following common case:
 * <pre>
 * String format = "Some long format string: %s";
 * log(format, arg);
</pre> *
 *  * Another [FormatString] annotated parameter. Ex:
 * <pre>
 * public class Foo {
 * static final String staticFinalLogMessage = "foo";
 * &#064;FormatMethod void log(&#064;FormatString String format, Object... args) {}
 * &#064;FormatMethod void validLog(&#064;FormatString String format, Object... args) {
 * log(format, args);
 * }
 * }</pre>
 *
 *  1. The format string will be valid for the input format arguments. In the case that the actual
 * format string parameter has a compile time constant value, this will compare the actual
 * format string value to the types of the passed in format arguments to ensure validity. In
 * the case that the actual format string parameter is a parameter that was annotated [       ] itself, this will ensure that the types of the arguments passed to the callee
 * match the types of the arguments in the caller.
 *
 */
@Documented
@Retention(RetentionPolicy.CLASS)
@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER,
    AnnotationTarget.CONSTRUCTOR
)
annotation class FormatMethod 