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
                //network IO for image loading
                api(libs.coil3.network)
                api(libs.coil3)
                api(libs.coil3.core)

            }
        }
        val androidMain by getting{
            dependencies {
                //for gallery and intent launching and for context to access permission
                implementation(libs.androidx.activity.compose)
                //
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


}
android {
    namespace = "media_picker"
    compileSdk = 34
    defaultConfig {
        minSdk = 27
    }

}