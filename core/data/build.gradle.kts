plugins {
    alias(libs.plugins.cattose.android.library)
    alias(libs.plugins.cattose.hilt)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.cattose.library.jacoco)
}

android {
    namespace = "br.com.cattose.data"
}

dependencies {
    implementation(projects.core.network)
    implementation(libs.androidx.compose.paging3)
    testImplementation(libs.paging.test)
    testImplementation(libs.bundles.test.commons)
    testImplementation(libs.ktor.client.mock)
    testImplementation(libs.bundles.ktor)
}