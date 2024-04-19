
Основные компоненты:
- frontend (Vue.js)
- backend
  - Spring boot 2.x
- DB
  - PostgreSQL
  - MinIO (default profile)
- Admin panels
  - Swagger (rest api docs)
  - MinIO admin panel
  - Pgadmin


## Запуск
```shell
docker-compose up
```

## Схемы в БД Postgres
Все необходимые схемы в БД Postgres создаются с помощью Flyway миграции.

### Пользователи
С помощью Flyway создается 3 пользователя:

Login/Password:
```text
admin@localhost/admin
test@localhost/admin
writer@localhost/admin
```

#### docker-compose

Этот профиль можно явно не указывать, он по умолчанию активирован.

```yaml
    environment:
      APP_PROFILE: default
```
## Тестирование

Для тестов отдельный docker-compose файл: [docker-compose-db-only.yml](src%2Ftest%2Fresources%2Fdocker-compose-db-only.yml)

Использовал testcontainers и MockMvc.

Также были создан отдельный файл postman для проведения unit тестирования.

