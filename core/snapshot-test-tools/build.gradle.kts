plugins {
    alias(libs.plugins.cattose.android.library)
    alias(libs.plugins.cattose.library.compose)
}

android {
    namespace = "br.com.cattose.snapshot_test_tools"
}

dependencies {
    implementation(projects.core.ui)

    implementation(libs.paparazzi)
    implementation(libs.coil)
    implementation(libs.coil.test)
    implementation(libs.coroutines.test)
}