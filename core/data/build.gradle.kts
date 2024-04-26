plugins {
    alias(libs.plugins.cattose.android.library)
    alias(libs.plugins.cattose.hilt)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "br.com.cattose.data"
}

dependencies {
    implementation(projects.core.network)
    implementation(libs.androidx.compose.paging3)
    testImplementation(libs.paging.test)
    testImplementation(libs.bundles.test.commons)
}