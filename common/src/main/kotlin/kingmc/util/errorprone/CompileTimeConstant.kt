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

/**
 * Annotation for method parameter and class field declarations, which denotes that corresponding
 * actual values must be compile-time constant expressions.
 *
 *
 * When the formal parameter of a method or constructor is annotated with the [ ] type annotation, the corresponding actual parameter must be an expression
 * that satisfies one of the following conditions:
 *
 *
 *  1. The expression is one for which the Java compiler can determine a constant value at compile
 * time, or
 *  1. the expression consists of the literal `null`, or
 *  1. the expression consists of a single identifier, where the identifier is a formal method
 * parameter or class field that is declared `final` and has the [       ] annotation.
 *
 *
 *
 * This constraint on call sites of methods or constructors that have one or more formal
 * parameters with this annotation is enforced by [error-prone](https://errorprone.info).
 *
 *
 * For example, the following code snippet is legal:
 *
 * <pre>`public class C {
 * private static final S = "Hello";
 * void m(@CompileTimeConstant final String s) { }
 * void n(@CompileTimeConstant final String t) {
 * m(S + " World!");
 * m(null);
 * m(t);
 * }
 * }
`</pre> *
 *
 *
 * In contrast, the following is illegal:
 *
 * <pre>`public class C {
 * void m(@CompileTimeConstant final String s) { }
 * void n(String t) {
 * m(t);
 * }
 * }
`</pre> *
 *
 *
 * When a class field is annotated with the [CompileTimeConstant] type annotation, the
 * field must also be declared to be `final`, and the corresponding initialised value must be
 * an expression that satisfies one of the following conditions:
 *
 *
 *  1. The expression is one for which the Java compiler can determine a constant value at compile
 * time, or
 *  1. the expression consists of the literal `null`, or
 *  1. the expression consists of a single identifier, where the identifier is a formal method
 * parameter or class field that is declared `final` and has the [       ] annotation.
 *
 *
 *
 * This constraint on fields with this annotation is enforced by [error-prone](https://errorprone.info).
 *
 *
 * For example, the following code snippet is legal:
 *
 * <pre>`public class C {
 * \@CompileTimeConstant final String S;
 * public C(@CompileTimeConstant String s) {
 * this.S = s;
 * }
 * void m(@CompileTimeConstant final String s) { }
 * void n() {
 * m(S);
 * }
 * }
`</pre> *
 *
 *
 * In contrast, the following are illegal:
 *
 * <pre>`public class C {
 * \@CompileTimeConstant String S;
 * public C(@CompileTimeConstant String s) {
 * this.S = s;
 * }
 * void m(@CompileTimeConstant final String s) { }
 * void n() {
 * m(S);
 * }
 * }
`</pre> *
 *
 * <pre>`public class C {
 * \@CompileTimeConstant final String S;
 * public C(String s) {
 * this.S = s;
 * }
 * }
`</pre> *
 *
 *
 * Compile-time constant values are implicitly under the control of the trust domain of the
 * application whose source code they are part of. Hence, this annotation is useful to constrain the
 * use of APIs that may only be safely called with values that are under application control.
 *
 *
 * The current implementation of the @CompileTimeConstant checker cannot reason about more
 * complex scenarios, for example, returning compile-time-constant values from a method, or storing
 * compile-time-constant values in a collection. APIs will typically accommodate such use cases via
 * domain-specific types that capture domain-specific aspects of trustworthiness that arise from
 * values being under application control.
 */
@MustBeDocumented
@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.FIELD)
annotation class CompileTimeConstant 