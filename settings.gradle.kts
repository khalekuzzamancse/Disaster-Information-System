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

val coreModules = listOf(":core", ":core:network", ":core:uploader")
val uiLayers = listOf(":common")
val applications = listOf(":applications", ":applications:android", ":applications:desktop")
val features= listOf(":feature",":feature:navigation",":feature:media_picker",":feature:video_compression",":feature:report_form",":feature:miscellaneous")
include(applications+uiLayers+coreModules+features)

