import br.com.cattose.buildlogic.convention.configureKotlinAndroid
import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {

        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
            }

            extensions.configure<ApplicationExtension> {
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = 34

                @Suppress("UnstableApiUsage")
                testOptions {
                    animationsDisabled = true
                    emulatorControl {
                        enable = true
                    }
                    unitTests {
                        isIncludeAndroidResources = true
                        isReturnDefaultValues = true
                    }
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
        }
    }
}
