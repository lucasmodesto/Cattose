plugins {
    alias(libs.plugins.cattose.library.compose)
    alias(libs.plugins.cattose.feature)
    alias(libs.plugins.cattose.library.jacoco)
}

android {
    namespace = "br.com.cattose.app.feature.list"
}

dependencies {
    implementation(libs.androidx.compose.paging3)
    testImplementation(libs.paging.test)
}