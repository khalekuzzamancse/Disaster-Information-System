plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsCompose)
}
kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "17"
            }
        }
    }
    jvm("desktop"){
        jvmToolchain(17)
    }
    sourceSets{
        val commonMain by getting{
            dependencies {
                implementation(project(":common"))
                //
                implementation(compose.ui)
                implementation(compose.material3)
                implementation(compose.animation)
                implementation(compose.animationGraphics)
                implementation(compose.materialIconsExtended)
                implementation(compose.foundation)
                implementation(compose.runtime)
                implementation(libs.windowSize)
                //for resources access
                implementation(compose.components.resources)
                //
                implementation(libs.kotlinx.coroutines.core)

            }
        }
        val androidMain by getting{
            dependencies {
                implementation(libs.androidx.core)
                //for view model
                val lifecycleVersion = "2.7.0"
                // ViewModel
                implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")
                // ViewModel utilities for Compose
                implementation("androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycleVersion")
                implementation(libs.androidx.navigation.compose)
            }
        }
        val desktopMain by getting{
            dependencies {
                //dependency to support android coroutine on desktop
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-swing")
            }
        }
    }


}
android {
    namespace = "feature.home"
    compileSdk = 34
    defaultConfig {
        minSdk = 27
    }

}