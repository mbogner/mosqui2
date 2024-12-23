import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") apply false
    kotlin("jvm") apply false
    kotlin("plugin.spring") apply false
    id("org.sonarqube")
    id("jacoco")
}

allprojects {
    val mavenGroup: String by System.getProperties()
    group = mavenGroup

    repositories {
        mavenLocal()
        mavenCentral()
        google()
    }

    tasks {
        val javaVersion: String by System.getProperties()

        withType<JavaCompile> {
            sourceCompatibility = javaVersion
            targetCompatibility = javaVersion
        }

        withType<KotlinCompile> {
            compilerOptions {
                freeCompilerArgs = listOf("-Xjsr305=strict")
                jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.fromTarget(javaVersion)
            }
        }

        withType<Copy> {
            duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        }

        withType<Test> {
            useJUnitPlatform()
        }

        withType<JacocoReport> {
            reports {
                xml.required.set(true)
                html.required.set(false)
            }
        }
    }
}

tasks.withType<Wrapper> {
    // https://gradle.org/releases/
    gradleVersion = "8.12"
    distributionType = Wrapper.DistributionType.ALL
}

sonarqube {
    properties {
        property("sonar.sourceEncoding", "UTF-8")
        property("sonar.projectKey", "dev_mbo_mosqui2")
        property("sonar.projectName", "Mosqui2")
        property("sonar.sources", "src/main/kotlin,src/main/java,src/main/resources")
        property("sonar.exclusions", "**/src/gen/**/*")
    }
}

jacoco {
    // https://github.com/jacoco/jacoco/releases
    toolVersion = "0.8.12"
}
