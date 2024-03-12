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
                implementation(project(":feature:report_form"))
                implementation(project(":feature:media_picker"))
                implementation(project(":core:network"))
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
                implementation(compose.components.resources)

            }
        }
        val androidMain by getting{
            dependencies {
                //navigation
                implementation(libs.androidx.navigation.compose)
                //for media upload
                implementation(project(":core:work_manager"))
                //to access android such as to use Uri
                implementation(libs.androidx.core)
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
