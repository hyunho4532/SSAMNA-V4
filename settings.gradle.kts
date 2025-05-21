pluginManagement {
    repositories {
        maven {
            url = uri("https://oss.sonatype.org/content/repositories/snapshots/")
        }
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
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
            url = uri("https://www.jitpack.io")
        }
    }
}

rootProject.name = "SSAMNA"
include(":app")
include(":common")
include(":presentation")
include(":data")
include(":domain")
