import br.com.cattose.buildsrc.convention.configureKotlinAndroid
import br.com.cattose.buildsrc.convention.testBundleImplementation
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

                defaultConfig {
                    @Suppress("UnstableApiUsage")
                    testOptions {
                        targetSdk = 34
                        animationsDisabled = true
                        unitTests {
                            isIncludeAndroidResources = true
                            isReturnDefaultValues = true
                        }
                    }
                }
            }
            dependencies {
                testBundleImplementation(target)
            }
        }
    }
}
