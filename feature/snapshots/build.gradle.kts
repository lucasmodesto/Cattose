plugins {
    alias(libs.plugins.cattose.android.library)
    alias(libs.plugins.cattose.library.compose)
    alias(libs.plugins.paparazzi)
}

android {
    namespace = "br.com.cattose.snapshots"
}

dependencies {
    implementation(projects.feature.list)
    implementation(projects.feature.detail)
    implementation(libs.androidx.compose.foundation)

    testImplementation(projects.core.snapshotTestTools)
}