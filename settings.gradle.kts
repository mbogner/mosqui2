rootProject.name = "mosqui2"

mapOf(
    "app-server" to "app/server",
).forEach { (name, path) ->
    include(name)
    project(":$name").projectDir = file(path)
}

pluginManagement {
    repositories {
        mavenLocal()
        gradlePluginPortal()
        mavenCentral()
    }
    plugins {
        // https://plugins.gradle.org/plugin/org.springframework.boot
        id("org.springframework.boot") version "3.4.0"
        // https://kotlinlang.org/docs/releases.html
        kotlin("jvm") version "2.1.0"
        kotlin("plugin.spring") version "2.1.0"

        // https://plugins.gradle.org/plugin/org.sonarqube
        id("org.sonarqube") version "6.0.1.5171"
    }
}

dependencyResolutionManagement {
    versionCatalogs {
        create("boms") {
            library("spring-boot-bom", "dev.mbo", "spring-boot-bom")
                .version("2024.12.1")

            library("library-bom", "dev.mbo", "library-bom")
                .version("2024.12.1")
        }
    }
}