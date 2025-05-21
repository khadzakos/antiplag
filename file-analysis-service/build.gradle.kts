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
    // Spring Boot
    implementation("org.springframework.boot:spring-boot-starter-web")

    // Для работы с базой данных
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.postgresql:postgresql:42.7.3")

    // Миграции
    implementation("org.flywaydb:flyway-core:11.8.0")
    implementation("org.flywaydb:flyway-database-postgresql:11.8.0")

    // Для Swagger
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.3.0")

    // Kotlin стандартные библиотеки
    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    // Тесты
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

application {
    mainClass.set("hse.fileanalysis.FileAnalysisServiceApplicationKt")
}

kotlin {
    jvmToolchain(21)
}