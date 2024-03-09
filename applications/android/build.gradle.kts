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
    implementation("khalekuzzamancse.cse:cmp-components:1.0.0")
    implementation(project(":ui"))
    implementation(project(":feature:navigation"))
    implementation(project(":feature:image_video_picker"))
    implementation(project(":core:network"))
    implementation(project(":core:work_manager"))
    implementation(project(":feature:home"))
    implementation(project(":feature:data_submission"))

    implementation(compose.ui)
    implementation(compose.material3)
    implementation(compose.animation)
    implementation(compose.animationGraphics)
    implementation(compose.materialIconsExtended)
    implementation(compose.foundation)
    implementation(compose.runtime)
    implementation(libs.windowSize)

    //

    implementation(libs.androidx.activity.compose)
    implementation(libs.kotlinx.coroutines.android)
 
    //navigation
    implementation(libs.androidx.navigation.compose)
    implementation(libs.play.services.location)


}