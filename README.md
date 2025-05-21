# antiplag
Инструмент анализа отчетов о плагиате (.txt) и их статистической обработки

**Кадждый микросервис содержит в себе README.md файл с описанием его работы.**

### Запуск
```bash
    docker compose up -d
```
### Остановка
```bash
    docker compose down
```

### Open API
API Gateway:
```bash
    http://localhost:8080/swagger-ui/index.html#/
```
File Storing Service:
```bash
    http://localhost:8081/swagger-ui/index.html#/
```
File Processing Service:
```bash
    http://localhost:8082/swagger-ui/index.html#/
```