# Logistics Hub

![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.7-green)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-42.7.8-blue)

Java/Spring Boot REST API для управління логістичними операціями.

## Tech Stack

| Категорія | Технології |
|-----------|-----------|
| **Core** | Java 21, Spring Boot 3.5.7, Spring Security (JWT) |
| **Database** | PostgreSQL, HikariCP, Hibernate ORM 6.4, Flyway |
| **Tools** | MapStruct, Lombok, SpringDoc OpenAPI |
| **Testing** | JUnit 5, Mockito, AssertJ, TestContainers, H2 |
| **Quality** | JaCoCo (мінімум 80% покриття) |

## Prerequisites

- Java 21+
- Gradle 8.14+
- PostgreSQL (порт 6543 для dev профілю)

## Getting Started

```bash
git clone <repository-url>
cd logistics-hub
```

Створи базу даних PostgreSQL та налаштуй підключення в `application-dev.yml`.

```bash
./gradlew bootRun           # Запуск (dev профіль)
./gradlew build             # Збірка
./gradlew test              # Тести
./gradlew jacocoTestReport  # Покриття коду
```

## Environment Profiles

| Профіль | БД | Hibernate DDL | Swagger | Логування |
|---------|-----|---------------|---------|-----------|
| **dev** | PostgreSQL (localhost:6543) | validate | Увімкнений | DEBUG |
| **test** | H2 in-memory | create-drop | Вимкнений | DEBUG |
| **prod** | PostgreSQL (SSL) | validate | Вимкнений | WARN |

## Project Structure

```
src/main/java/com/translogistics/hub/logistics_hub/
├── config/          # Конфігурація (Security, Hibernate)
├── controller/      # REST контролери
├── dto/
│   ├── request/     # Request DTOs
│   └── response/    # Response DTOs
├── entity/          # JPA сутності
├── exception/       # Обробка помилок
├── mapper/          # MapStruct маппери
├── repository/      # Spring Data репозиторії
├── security/        # JWT, фільтри автентифікації
├── service/         # Бізнес-логіка
└── util/            # Утиліти
```

## Architecture

```
Controller → Service → Repository → Entity
     ↕            ↕
    DTO        Mapper (MapStruct)
```

- **Controller** — REST endpoints, валідація вхідних даних (`@Valid`)
- **Service** — бізнес-логіка, транзакції (`@Transactional`)
- **Repository** — доступ до даних (Spring Data JPA)
- **Entity** — JPA сутності
- **DTO** — request/response об'єкти (Entity не повертається напряму)
- **Mapper** — MapStruct для конвертації Entity <-> DTO

## API Documentation

Swagger UI доступний у dev профілі: `/api/v1/swagger-ui.html`

## Testing

```bash
./gradlew test                                          # Всі тести
./gradlew test --tests "*.UserServiceTest"              # Конкретний клас
./gradlew test --tests "*.UserServiceTest.shouldCreate*" # Конкретний метод
./gradlew jacocoTestReport                              # Звіт покриття
```

- **Unit тести**: JUnit 5 + Mockito + AssertJ
- **Інтеграційні тести**: `@SpringBootTest` + H2 або TestContainers
- **Покриття**: JaCoCo, мінімум 80%

## Database Migrations

Flyway міграції розташовані в `src/main/resources/db/migration/`.

Формат іменування: `V{номер}__{опис}.sql` (наприклад, `V1__create_users_table.sql`).
