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
include(":app")
include(coreModules)
include(":mylibrary2")
