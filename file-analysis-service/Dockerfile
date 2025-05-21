FROM openjdk:21-jdk-slim AS builder

WORKDIR /app

COPY gradlew .
COPY gradle ./gradle/

COPY build.gradle.kts .
COPY settings.gradle.kts .

RUN ./gradlew dependencies --no-daemon --no-watch-fs

COPY src ./src

RUN ./gradlew bootJar --no-daemon --no-watch-fs

# --- Стадия 2: Создание финального, легковесного образа для запуска ---
FROM openjdk:21-jdk-slim

WORKDIR /app

COPY --from=builder /app/build/libs/file-analysis-service-1.0-SNAPSHOT.jar ./app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]