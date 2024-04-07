plugins {
    alias(libs.plugins.androidApplication)
    kotlin("android")
    id("org.jetbrains.compose")
    alias(libs.plugins.kotlinxSerialization)
}

android {
    namespace = "com.dis"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.khalekuzzaman.just.cse.dis"
        minSdk = 27
        targetSdk = 34
        versionCode = 2
        versionName = "1.1"

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
    implementation(project(":feature:navigation"))
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
//
    implementation("org.slf4j:slf4j-simple:1.7.30")
    //for slash screen
    implementation("androidx.core:core-splashscreen:1.0.1")

}