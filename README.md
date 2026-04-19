# userStorage

REST-приложение на Spring Boot для хранения пользователей в PostgreSQL.

Приложение поддерживает:
- создание пользователя
- получение списка пользователей
- получение пользователя по `id`
- обновление пользователя
- удаление пользователя

Стек проекта:
- Java 17
- Spring Boot 3
- Spring Data JPA
- PostgreSQL 15
- Docker / Docker Compose
- Maven

## Структура проекта

- `app` - Spring Boot приложение
- `db` - PostgreSQL и SQL-инициализация
- `docker-compose.yml` - запуск приложения и базы в контейнерах

## Требования

Для локального запуска понадобятся:
- JDK 17
- Maven 3.9+
- Docker Desktop или Docker Engine
- Docker Compose

Проверить установку можно командами:

```powershell
java -version
mvn -version
docker --version
docker compose version
```

## Переменные окружения

В корне проекта используется файл `.env`:

```env
POSTGRES_DB=user_storage
POSTGRES_USER=postgres
POSTGRES_PASSWORD=12345
```

Эти переменные используются при запуске через `docker compose`.

Для локального запуска приложения без Docker используются значения из
[application.properties](D:/IdeaProjects/userStorage/app/src/main/resources/application.properties:1):

- `DB_HOST` по умолчанию `localhost`
- `DB_PORT` по умолчанию `5432`
- `DB_NAME` по умолчанию `user_storage`
- `DB_USER` по умолчанию `postgres`
- `DB_PASSWORD` по умолчанию `12345`

## Вариант 1. Полный запуск через Docker Compose

### 1. Перейдите в корень проекта

### 2. Убедитесь, что существует файл `.env`

Если файл уже есть, можно использовать его как есть.

Пример содержимого:

```env
POSTGRES_DB=user_storage
POSTGRES_USER=postgres
POSTGRES_PASSWORD=12345
```

### 3. Запустите контейнеры

```powershell
docker compose up --build -d
```

Что произойдет:
- соберется образ PostgreSQL из папки `db`
- выполнится `db/init.sql`
- соберется образ приложения из папки `app`
- приложение станет доступно на `http://localhost:8080`

### 4. Проверьте, что контейнеры поднялись

```powershell
docker compose ps
```
### 5. Откройте API

Базовый URL:

```text
http://localhost:8080/api/users
```

## Проверка работы API

Ниже несколько быстрых примеров для PowerShell.

### Создать пользователя

```powershell
curl -X POST http://localhost:8080/api/users `
  -H "Content-Type: application/json" `
  -d "{\"name\":\"Ivan\",\"email\":\"ivan@example.com\"}"
```

### Получить список пользователей

```powershell
curl http://localhost:8080/api/users
```

### Получить пользователя по id

```powershell
curl http://localhost:8080/api/users/1
```

### Обновить пользователя

```powershell
curl -X PUT http://localhost:8080/api/users/1 `
  -H "Content-Type: application/json" `
  -d "{\"name\":\"Ivan Petrov\",\"email\":\"ivan@example.com\"}"
```

### Удалить пользователя

```powershell
curl -X DELETE http://localhost:8080/api/users/1
```

## Возможные HTTP-ответы

- `200 OK` - успешное чтение или обновление
- `201 Created` - пользователь успешно создан
- `204 No Content` - пользователь успешно удален
- `400 Bad Request` - ошибка валидации входных данных
- `404 Not Found` - пользователь не найден
- `409 Conflict` - пользователь с таким `email` уже существует

## Тесты и покрытие

Запуск тестов:

```powershell
mvn test
```

Запуск тестов с генерацией coverage:

```powershell
mvn verify
```

HTML-отчет JaCoCo будет доступен по пути:

[app/target/site/jacoco/index.html]

В проекте настроен минимальный порог покрытия:
- если покрытие строк меньше `50%`, команда `mvn verify` завершится с ошибкой

## Линтер

Проверка линтера:

```powershell
mvn checkstyle:check
```

Конфигурация линтера находится в файле:

[app/checkstyle.xml]

## CI/CD

Для открытого `pull request` в GitHub Actions запускаются этапы:
- `build`
- `lint`
- `test-and-coverage`
- `docker-build`
- `docker-push`

Pipeline описан в файле:

[.github/workflows/pr-ci-cd.yml](D:/IdeaProjects/userStorage/.github/workflows/pr-ci-cd.yml)

Для `docker-push` в GitHub необходимо настроить secrets:
- `DOCKERHUB_USERNAME`
- `DOCKERHUB_TOKEN`

## Полезные команды

Пересобрать и запустить проект заново:

```powershell
docker compose up --build -d
```

Посмотреть логи всех сервисов:

```powershell
docker compose logs -f
```

Остановить только приложение:

```powershell
docker compose stop app
```

Запустить только приложение:

```powershell
docker compose start app
```
