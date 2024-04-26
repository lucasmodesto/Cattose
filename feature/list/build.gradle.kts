plugins {
    alias(libs.plugins.cattose.library.compose)
    alias(libs.plugins.cattose.feature)
}

android {
    namespace = "br.com.cattose.app.feature.list"
}

dependencies {
    implementation(libs.androidx.compose.paging3)
    implementation(libs.androidx.hilt.navigation.compose)
    testImplementation(libs.bundles.test.commons)
    testImplementation(libs.paging.test)
    androidTestImplementation(libs.bundles.android.test.commons)
}