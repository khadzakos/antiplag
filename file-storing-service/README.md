# File Storing Service

## Документация к API
```bash
    http://localhost:8081/swagger-ui/index.html#/
```

File Storing Service - это микросервис, который отвечает за хранение файлов. Он принимает файлы, а затем сохраняет их в локальном файловом хранилище. В базе данных db-storage хранится информация о файлах, которые были загружены.

Сборка происходит с помощью Gradle, а конфигурация приложения хранится в файле application.yml.

В директории /src/main/kotlin/hse/filestoring находятся классы, которые отвечают за хранение файлов:
- FileStoringService - сервис, который отвечает за хранение файлов
- HashService - сервис, который отвечает за создание хешей файлов
- FileController - контроллер, который обрабатывает запросы на загрузку файлов
- FileMetaRepository - репозиторий, который отвечает за работу с базой данных db-storage
- FileMeta - сущность, которая представляет собой метаданные файла, который был загружен

## Зависимости
- spring-boot-starter-web - для создания RESTful веб-приложений на основе фреймворка Spring Boot
- org.jetbrains.kotlin:kotlin-reflect - для работы с рефлексией в Kotlin
- org.springdoc:springdoc-openapi-starter-webmvc-ui - для генерации документации OpenAPI для RESTful веб-приложений на основе Spring Boot
- org.springframework.boot:spring-boot-starter-data-jp a - для работы с базой данных на основе Spring Data JPA
- org.postgresql:postgresql - для работы с PostgreSQL
- org.flywaydb:flyway-core - для работы с миграциями базы данных
- org.flywaydb:flyway-database-postgresql - для работы с миграциями базы данных PostgreSQL
