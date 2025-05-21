# API Gateway

## Документация к API
```bash
    http://localhost:8080/swagger-ui/index.html#/
```
API Gateway - это точка входа для всех запросов к микросервисам. Он маршрутизирует запросы к соответствующим микросервисам и обрабатывает ответы.

Код рассположен в директории /src/main/kotlin/hse/gateway

Сборка происходит с помощью Gradle, а конфигурация приложения хранится в файле application.yml.

В config находится конфигурация приложения
- AppConfig - конфигурация приложения
- ServiceUrls - конфигурация URL-адресов микросервисов

В controller находится контроллер, который обрабатывает запросы
- GatewayController - контроллер, который обрабатывает запросы и маршрутизирует их к соответствующим микросервисам

В util находится MultipartInputStreamFileResource, который используется для обработки файловых запросов

## Зависимости
- spring-boot-starter-web - для создания RESTful веб-приложений на основе фреймворка Spring Boot
- com.fasterxml.jackson.module:jackson-module-kotlin - для работы с JSON в Kotlin
- org.jetbrains.kotlin:kotlin-reflect - для работы с рефлексией в Kotlin
- org.springdoc:springdoc-openapi-starter-webmvc-ui - для генерации документации OpenAPI для RESTful веб-приложений на основе Spring Boot
