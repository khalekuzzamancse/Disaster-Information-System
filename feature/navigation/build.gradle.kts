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
                implementation(project(":ui"))
                implementation(project(":core:di"))
                implementation(project(":feature:home"))
                implementation(project(":feature:data_submission"))
                implementation(project(":feature:image_video_picker"))
                //
                implementation(compose.ui)
                implementation(compose.material3)
                implementation(compose.animation)
                implementation(compose.animationGraphics)
                implementation(compose.materialIconsExtended)
                implementation(compose.foundation)
                implementation(compose.runtime)
                implementation(libs.windowSize)
                //
                implementation(libs.kotlinx.coroutines.core)

            }
        }
        val androidMain by getting{
            dependencies {
                //navigation
                implementation(libs.androidx.navigation.compose)
            }
        }
        val desktopMain by getting{
            dependencies {
                //dependency to support android coil on desktop
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-swing")
            }
        }
    }
    compilerOptions {
        // Common compiler options applied to all Kotlin source sets
        freeCompilerArgs.add("-Xexpect-actual-classes")
    }

}
android {
    namespace = "navigation"
    compileSdk = 34
    defaultConfig {
        minSdk = 27
    }

}
