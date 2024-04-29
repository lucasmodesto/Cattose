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
