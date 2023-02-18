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

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

/**
 * Indicates that the PackageLocation warning should be suppressed for this package.
 *
 *
 * The standard [SuppressWarnings] annotation cannot be applied to packages, so we must use
 * a custom suppression annotation for this check.
 */
@Target(AnnotationTarget.FILE)
@Retention(RetentionPolicy.CLASS)
annotation class SuppressPackageLocation 