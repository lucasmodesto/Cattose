plugins {
    alias(libs.plugins.cattose.android.application)
    alias(libs.plugins.cattose.android.application.jacoco)
    alias(libs.plugins.cattose.application.compose)
    alias(libs.plugins.cattose.hilt)
}

android {
    namespace = "br.com.cattose"

    defaultConfig {
        applicationId = "br.com.cattose"
        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
        }
    }
    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(projects.core.data)
    implementation(projects.core.ui)

    implementation(libs.androidx.hilt.navigation.compose)

    implementation(projects.feature.list)
    implementation(projects.feature.detail)

    implementation(libs.tracing)

    testImplementation(libs.bundles.test.commons)
    androidTestImplementation(libs.bundles.android.test.commons)
}