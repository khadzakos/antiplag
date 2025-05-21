# File Analysis Service

## Документация к API
```bash
    http://localhost:8082/swagger-ui/index.html#/
```
File Analysis Service - это микросервис, который отвечает за анализ файлов и создание облака слов. Он принимает текст файла и его метаданные из File Storing Service, а затем обрабатывает его, создавая облако слов и сохраняя его в локальном файловом хранилище. В базе данных db-analysis хранится информация о файлах, которые были проанализированы. В директории /pics хранятся облака слов, которые были созданы в процессе анализа.

Сборка происходит с помощью Gradle, а конфигурация приложения хранится в файле application.yml.

В директории /src/main/kotlin/hse/fileanalysis находятся классы, которые отвечают за анализ файлов и создание облака слов:

- FileAnalysisService - сервис, который отвечает за анализ файлов и создание облака слов
- AnalysisController - контроллер, который обрабатывает запросы на анализ файлов
- AnalysisMetaRepository - репозиторий, который отвечает за работу с базой данных db-analysis
- AnalysisMeta - сущность, которая представляет собой метаданные файла, который был проанализирован
- FileStorageClient - клиент, который отвечает за работу с File Storing Service
- WordCloudClient - клиент, который отвечает за работу с облаком слов
- FileMetaDto - класс, который представляет собой передаваемые метаданные файла между микросервисами
- AppConfig - класс, который отвечает за конфигурацию приложения

## Зависимости
- spring-boot-starter-web - для создания RESTful веб-приложений на основе фреймворка Spring Boot
- org.jetbrains.kotlin:kotlin-reflect - для работы с рефлексией в Kotlin
- org.springdoc:springdoc-openapi-starter-webmvc-ui - для генерации документации OpenAPI для RESTful веб-приложений на основе Spring Boot
- org.springframework.boot:spring-boot-starter-data-jp a - для работы с базой данных на основе Spring Data JPA
- org.postgresql:postgresql - для работы с PostgreSQL
- org.flywaydb:flyway-core - для работы с миграциями базы данных
- org.flywaydb:flyway-database-postgresql - для работы с миграциями базы данных PostgreSQL
