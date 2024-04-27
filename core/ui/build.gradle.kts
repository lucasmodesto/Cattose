plugins {
    alias(libs.plugins.cattose.android.library)
    alias(libs.plugins.cattose.library.compose)
}

android {
    namespace = "br.com.cattose.app.core.ui"
}

dependencies {
    implementation(platform(libs.compose.bom))
    api(libs.activity.compose)
    api(libs.androidx.compose.foundation)
    api(libs.androidx.compose.foundation.layout)
    api(libs.androidx.lifecycle.runtime.compose)
    api(libs.ui)
    api(libs.ui.graphics)
    api(libs.ui.tooling.preview)
    api(libs.material3)
    api(libs.core.ktx)
    api(libs.lifecycle.runtime.ktx)
    debugApi(libs.ui.tooling)
    debugApi(libs.ui.test.manifest)

    api(libs.coil)
    api(libs.coil.compose)
    api(libs.coil.gif)
}