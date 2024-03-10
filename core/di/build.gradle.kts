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
                //to provide the CustomDateUtils Implementation
                implementation(project(":feature:data_submission"))
            

                implementation(libs.kotlinx.coroutines.core)

            }
        }
        val androidMain by getting{
            dependencies {
                implementation(libs.ktor.client.okhttp)

            }
        }
        val desktopMain by getting{
            dependencies {
                implementation(libs.ktor.client.okhttp)
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-swing")
            }
        }
    }


}
android {
    namespace = "di.di"
    compileSdk = 34
    defaultConfig {
        minSdk = 27
    }

}