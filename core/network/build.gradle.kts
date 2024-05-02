plugins {
    alias(libs.plugins.cattose.android.library)
    alias(libs.plugins.cattose.hilt)
    alias(libs.plugins.secrets)
    alias(libs.plugins.cattose.library.jacoco)
}

android {
    namespace = "br.com.cattose.core.network"

    buildFeatures {
        buildConfig = true
    }
}

secrets {
    propertiesFileName = "secrets.properties"
}

dependencies {
    implementation(libs.bundles.ktor)
    api(libs.ktor.client.android)
    api(libs.ktor.serialization.kotlinx)
}