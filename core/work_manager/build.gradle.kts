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
                //to convert workmanager live data to flow
                implementation(libs.androidx.lifecycle.livedata.ktx )
                // Kotlin + coroutines
                implementation(libs.androidx.work.runtime.ktx)
                // optional - GCMNetworkManager support
                implementation(libs.androidx.work.gcm)
                implementation(libs.androidx.work.multiprocess)
                //for notification manager
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
