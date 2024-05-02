/*
 * Copyright 2024 The Android Open Source Project
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

package br.com.cattose.buildlogic.convention

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.ApplicationProductFlavor
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.ProductFlavor


private const val DIMENSION_ENVIRONMENT = "env"

@Suppress("EnumEntryName")
enum class CattoseFlavor(
    val applicationIdSuffix: String? = null,
    val isDefault: Boolean
) {
    demo(applicationIdSuffix = ".demo", isDefault = false),
    prod(isDefault = true)
}

fun configureFlavors(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
    flavorConfigurationBlock: ProductFlavor.(flavor: CattoseFlavor) -> Unit = {}
) {
    commonExtension.apply {
        flavorDimensions += DIMENSION_ENVIRONMENT
        productFlavors {
            CattoseFlavor.values().forEach {
                create(it.name) {
                    dimension = DIMENSION_ENVIRONMENT
                    flavorConfigurationBlock(this, it)
                    if (this@apply is ApplicationExtension && this is ApplicationProductFlavor) {
                        isDefault = it.isDefault
                        if (it.applicationIdSuffix != null) {
                            applicationIdSuffix = it.applicationIdSuffix
                        }
                    }
                }
            }
        }
    }
}
