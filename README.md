# Tasks API with JUnit 5 Tests

## Requirements
- JDK 17+
- Maven 3.9+

## Run the API
```bash
mvn spring-boot:run
```
API base URL: `http://localhost:8055/api/tasks`

## Run tests
```bash
mvn test
```

## Endpoints
- `GET /api/tasks` — list tasks
- `GET /api/tasks/{id}` — one task (404 if not found)
- `POST /api/tasks` — create task, JSON:
```json
{ "title": "Learn JUnit", "completed": false }
```
