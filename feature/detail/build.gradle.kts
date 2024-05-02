plugins {
    alias(libs.plugins.cattose.library.compose)
    alias(libs.plugins.cattose.feature)
    alias(libs.plugins.cattose.library.jacoco)
}

android {
    namespace = "br.com.cattose.app.feature.detail"
}