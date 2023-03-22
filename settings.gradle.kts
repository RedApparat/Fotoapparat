@file:Suppress("UnstableApiUsage")

import org.gradle.api.initialization.resolve.RepositoriesMode

pluginManagement {
    repositories {
        google(); mavenCentral(); gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        // uncomment if you published local changes to test
        // DO NOT MERGE THIS UNCOMMENTED
        // mavenLocal()
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    versionCatalogs {
        create("deps") {
            from(files("gradle/deps.versions.toml"))
            // Update versions and dependencies not used in tooling here.
        }
    }
}

include(":fotoapparat")
include(":fotoapparat-adapters:rxjava3")
include(":sample")