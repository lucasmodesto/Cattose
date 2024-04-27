import br.com.cattose.buildlogic.convention.androidTestBundleImplementation
import br.com.cattose.buildlogic.convention.libs
import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("cattose.android.library")
                apply("cattose.android.hilt")
            }
            extensions.configure<LibraryExtension> {
                defaultConfig {
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                    consumerProguardFiles("consumer-rules.pro")
                }
                packaging {
                    resources.excludes.addAll(
                        listOf(
                            "META-INF/LICENSE.md",
                            "META-INF/LICENSE-notice.md",
                            "/META-INF/{AL2.0,LGPL2.1}"
                        )
                    )
                }
            }

            dependencies {
                add("implementation", project(":core:ui"))
                add("implementation", project(":core:data"))
                add(
                    "implementation",
                    libs.findLibrary("androidx.hilt.navigation.compose").get()
                )
                androidTestBundleImplementation(target)
            }
        }
    }
}
