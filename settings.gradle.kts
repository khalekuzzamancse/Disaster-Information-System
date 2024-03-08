pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://maven.pkg.github.com/khalekuzzamancse/PersonalLibraries")
            credentials {
                username = System.getenv("GITHUB_USERNAME")
                password = System.getenv("GITHUB_TOKEN")
            }
        }

    }
}

rootProject.name = "DisasterInformationSystem"
val coreModules = listOf(":core", ":core:network", ":core:work_manager")
val uiLayers = listOf(":ui")
val applications = listOf(":applications", ":applications:android", ":applications:desktop")
val features= listOf(":feature",":feature:navigation",":feature:image_video_picker",":feature:video_compression",":feature:data_submission",":feature:home")
include(applications+uiLayers+coreModules+features)

