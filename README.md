# antiplag
Инструмент анализа отчетов о плагиате (.txt) и их статистической обработки

**Кадждый микросервис содержит в себе README.md файл с описанием его работы.**

### Использование
Рекомендую работать в таком порядке:
- Запомните имя загружаемого файла(без расширения)
- Cначала загружаете файл и узнаете его ID
- Получаете текст файла по ID
- Потом анализируете его
- Затем получаете облако слов

Все сделано исходя из данных в условии задачи пайплайнов.

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
