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
    }
}

rootProject.name = "Data Collect"
val coreModules= listOf(":core",":core:network",":core:work_manager")
val uiLayers= listOf(":ui")
val applications= listOf(":applications",":applications:desktop")
include(applications)
include(uiLayers)
include(":app")
include(coreModules)

