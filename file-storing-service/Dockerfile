# 1. Используем официальный JDK 21 образ
FROM openjdk:21-jdk as builder

# 2. Указываем рабочую директорию внутри контейнера
WORKDIR /app

# 3. Копируем Gradle wrapper и build файлы
COPY gradlew build.gradle.kts settings.gradle.kts gradle.properties ./
COPY gradle ./gradle

# 4. Копируем исходный код
COPY src ./src

# 5. Собираем проект (bootJar)
RUN ./gradlew bootJar --no-daemon

# 6. Новый образ только с JAR-файлом (final image)
FROM openjdk:21-jdk

WORKDIR /app

# 7. Копируем собранный JAR из предыдущего слоя
COPY --from=builder /app/build/libs/*.jar app.jar

# 8. Порт, на котором работает Spring Boot
EXPOSE 8081

# 9. Точка входа
ENTRYPOINT ["java", "-jar", "app.jar"]