/*
 * Copyright 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import br.com.cattose.buildlogic.convention.configureFlavors
import br.com.cattose.buildlogic.convention.configureKotlinAndroid
import br.com.cattose.buildlogic.convention.testBundleImplementation
import com.android.build.api.variant.LibraryAndroidComponentsExtension
import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
            }

            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)
                configureFlavors(this)
                defaultConfig {
                    @Suppress("UnstableApiUsage")
                    testOptions {
                        targetSdk = 34
                        animationsDisabled = true
                        unitTests {
                            isIncludeAndroidResources = true
                            isReturnDefaultValues = true
                        }
                        emulatorControl {
                            enable = true
                        }
                    }
                }
            }
            extensions.configure<LibraryAndroidComponentsExtension> {
                beforeVariants {
                    val androidTestDir = project.projectDir.resolve("src/androidTest")
                    it.androidTest.enable = androidTestDir.exists() && androidTestDir.listFiles()
                        ?.isNotEmpty() ?: false
                }
            }
            dependencies {
                testBundleImplementation(target)
            }
        }
    }
}
