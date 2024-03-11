plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("org.jetbrains.compose")
    alias(libs.plugins.kotlinxSerialization)
}
kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "17"
            }
        }
    }
    jvm("desktop") {
        jvmToolchain(17)
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(compose.ui)
                api(compose.material3)
                api(compose.animation)
                api(compose.animationGraphics)
                api(compose.materialIconsExtended)
                api(compose.foundation)
                api(compose.runtime)
                api(libs.windowSize)
                //
                implementation(libs.kotlinx.coroutines.core)
                //network IO for image loading
                api(libs.ktor.client.core)
                api(libs.ktor.client.okhttp)
                api(libs.ktor.client.content.negotiation)
                api(libs.coil3.network)
                api(libs.coil3)
                api(libs.coil3.core)
                api(libs.ktor.serialization.kotlinx.json)
            }
        }
        val androidMain by getting {
            dependencies {

                //Google map
                implementation(libs.maps.compose)
                implementation(libs.play.services.location)
                //to access Context such as for permissions
                implementation(libs.androidx.activity.compose)

            }
        }
        val desktopMain by getting {
            dependencies {
                //dependency to support android coil on desktop
                api("org.jetbrains.kotlinx:kotlinx-coroutines-swing")
            }
        }
    }
    compilerOptions {
        // Common compiler options applied to all Kotlin source sets
        freeCompilerArgs.add("-Xexpect-actual-classes")
    }

}
android {
    namespace = "ui"
    compileSdk = 34
    defaultConfig {
        minSdk = 27
    }

}
