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
