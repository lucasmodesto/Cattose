plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

apply {
    from( "${project.rootDir.path}/config/compose.gradle")
}

android {
    namespace = "br.com.cattose.app.core.ui"
    compileSdk = 34

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(platform(libs.compose.bom))
    api(libs.activity.compose)
    api(libs.androidx.compose.foundation)
    api(libs.androidx.compose.foundation.layout)
    api(libs.androidx.lifecycle.runtime.compose)
    api(libs.ui)
    api(libs.ui.graphics)
    api(libs.ui.tooling.preview)
    api(libs.material3)
    api(libs.core.ktx)
    api(libs.lifecycle.runtime.ktx)
    debugApi(libs.ui.tooling)
    debugApi(libs.ui.test.manifest)

    api(libs.coil)
    api(libs.coil.compose)
    api(libs.coil.gif)

    testImplementation(libs.bundles.test.commons)
    androidTestImplementation(libs.bundles.android.test.commons)
}