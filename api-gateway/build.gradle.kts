plugins {
    kotlin("jvm") version "1.9.23"
    kotlin("plugin.spring") version "1.9.23"
    id("org.springframework.boot") version "3.2.5"
    id("io.spring.dependency-management") version "1.1.4"
    application
}

group = "hse"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.3.0")

    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.kotlin:kotlin-reflect")
}

application {
    mainClass.set("hse.gateway.GatewayApplicationKt")
}

kotlin {
    jvmToolchain(21)
}