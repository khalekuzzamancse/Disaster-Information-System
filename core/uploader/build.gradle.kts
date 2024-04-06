import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSetTree

plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.kotlinMultiplatform)
}
kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "17"
            }
        }
    }
    //For UI Test
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        instrumentedTestVariant {
            sourceSetTree.set(KotlinSourceSetTree.test)
            dependencies {
                implementation("androidx.compose.ui:ui-test-junit4-android:1.6.4")
                debugImplementation("androidx.compose.ui:ui-test-manifest:1.6.4")
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
                implementation(project(":core:network"))

                //to access context
                implementation(compose.ui)
                implementation(compose.material3)
                implementation(compose.animation)
                implementation(compose.animationGraphics)
                implementation(compose.materialIconsExtended)
                implementation(compose.foundation)
                implementation(compose.runtime)
                implementation(libs.windowSize)
                implementation(libs.androidx.activity.compose)
                //for resources access
                implementation(compose.components.resources)

            }
        }
        val androidMain by getting{
            dependencies {
                implementation(libs.bundles.workManager)
                implementation(libs.androidx.core)


            }
        }
        val commonTest by getting {
            dependencies {
                implementation(libs.bundles.unitTest)
                //UI test for pick media and uploading
                @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                implementation(compose.uiTest)
            }

        }
        val androidUnitTest by getting {
            dependencies {
                implementation(libs.bundles.unitTest)
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
        //For UI Test
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

}
