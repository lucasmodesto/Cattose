plugins {
    alias(libs.plugins.cattose.library.compose)
    alias(libs.plugins.cattose.feature)
}

android {
    namespace = "br.com.cattose.app.feature.detail"
}

dependencies {
    testImplementation(libs.bundles.test.commons)
    androidTestImplementation(libs.bundles.android.test.commons)
}