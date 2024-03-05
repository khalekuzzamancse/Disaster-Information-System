
plugins {
    alias(libs.plugins.androidApplication)
    kotlin("android")
    id("org.jetbrains.compose")
    alias(libs.plugins.kotlinxSerialization)
}

android {
    namespace = "com.khalekuzzaman.just.cse.datacollect"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.khalekuzzaman.just.cse.datacollect"
        minSdk = 27
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.8"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(":core:network"))
    implementation(project(":core:work_manager"))

    //
    implementation(libs.androidx.activity.compose)
    implementation(compose.ui)
    implementation(compose.material3)
    implementation(compose.materialIconsExtended)
    implementation(libs.kotlinx.coroutines.android)
    //permission handle
    implementation (libs.accompanist.permissions)
    //network IO
    //Image load from memory and network
    implementation(libs.coil3.network)
    implementation(libs.coil3)
    implementation(libs.coil3.core)
    //video player
    implementation (libs.androidx.media3.exoplayer)
    implementation(libs.androidx.media3.ui)
    //navigation
    implementation(libs.androidx.navigation.compose)



}