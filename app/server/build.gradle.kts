plugins {
    id("org.springframework.boot")
    kotlin("jvm")
    kotlin("plugin.spring")
    id("jacoco")
}

dependencies {
    implementation(platform(boms.spring.boot.bom))
    implementation(platform(boms.library.bom))
    implementation("dev.mbo:kotlin-logging")
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.integration:spring-integration-mqtt")
    // https://mvnrepository.com/artifact/org.eclipse.paho/org.eclipse.paho.mqttv5.client
    implementation("org.eclipse.paho:org.eclipse.paho.mqttv5.client:1.2.5")
    // https://mvnrepository.com/artifact/com.jayway.jsonpath/json-path
    runtimeOnly("com.jayway.jsonpath:json-path:2.9.0")
}