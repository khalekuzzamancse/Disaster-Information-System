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
                implementation(libs.androidx.lifecycle.livedata.ktx )// Check for the latest version
                // Kotlin + coroutines
                implementation(libs.androidx.work.runtime.ktx)
                // optional - GCMNetworkManager support
                implementation(libs.androidx.work.gcm)
                implementation(libs.androidx.work.multiprocess)


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

