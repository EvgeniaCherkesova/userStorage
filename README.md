# userStorage (Java + Postgres)

Простое REST-приложение для учета пользователей:
- все CRUD операции (`GET/POST/PUT/DELETE`)
- хранение в Postgres через Spring Data JPA

## Запуск Postgres

Если Postgres ещё не поднят, выполните в корне проекта:

```powershell
docker compose up -d
```

## Запуск приложения

```powershell
mvn spring-boot:run
```

## API

Базовый путь: `http://localhost:8080/api/users`

Примеры (curl):

```bash
# создать пользователя
curl -X POST http://localhost:8080/api/users ^
  -H "Content-Type: application/json" ^
  -d "{\"name\":\"Ivan\",\"email\":\"ivan@example.com\"}"

# получить список
curl http://localhost:8080/api/users

# получить по id
curl http://localhost:8080/api/users/1

# обновить
curl -X PUT http://localhost:8080/api/users/1 ^
  -H "Content-Type: application/json" ^
  -d "{\"name\":\"Ivan Petrov\",\"email\":\"ivan@example.com\"}"

# удалить
curl -X DELETE http://localhost:8080/api/users/1
```

Ошибки:
- `404` если пользователь не найден
- `409` если `email` уже используется
- `400` если не прошла валидация

