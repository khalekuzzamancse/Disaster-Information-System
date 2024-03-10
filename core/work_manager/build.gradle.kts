plugins {
    kotlin("multiplatform")
    alias(libs.plugins.androidLibrary)

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
                implementation(libs.kotlinx.coroutines.core)

            }
        }
        val androidMain by getting{
            dependencies {
                implementation(project(":core:network"))
                implementation(libs.bundles.workManager)
                implementation(libs.androidx.core)



            }
        }
        val desktopMain by getting{
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-swing")
            }
        }
    }


}
android {
    namespace = "core.work_manager"
    compileSdk = 34
    defaultConfig {
        minSdk = 27
    }

}
