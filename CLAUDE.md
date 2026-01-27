# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Communication and Work Principles

### Language
**ВАЖЛИВО**: Завжди спілкуйся українською мовою при роботі в цьому репозиторії.

### Information Gathering
**ВАЖЛИВО**: Коли потрібна технічна інформація, документація або рішення проблем:
- Використовуй веб-пошук (WebSearch) для знаходження актуальних відповідей
- Перевіряй офіційну документацію бібліотек та фреймворків
- Шукай best practices та proven solutions в інтернеті
- Не висловлюй власну думку або припущення без підтвердження з надійних джерел

### Code Quality Standards
**ВАЖЛИВО**: Весь код має відповідати принципам чистого коду (Clean Code) Роберта Мартіна:
- **Змістовні назви**: Використовуй описові імена для змінних, функцій, класів
- **Функції**: Малі, виконують одну задачу, мають мало параметрів
- **SRP (Single Responsibility)**: Один клас/функція = одна відповідальність
- **DRY (Don't Repeat Yourself)**: Уникай дублювання коду
- **Коментарі**:
  - Код має бути самодокументованим
  - **НЕ ГЕНЕРУЙ коментарі до коду** - код повинен бути зрозумілим без коментарів
  - Коментарі допустимі ТІЛЬКИ для складної бізнес-логіки, яку неможливо спростити
  - Замість коментарів використовуй змістовні назви змінних та функцій
- **Форматування**: Консистентне, читабельне
- **Обробка помилок**: Явна, без ігнорування
- **Тести**: Пиши тести для нового функціоналу

### Refactoring Policy
**ВАЖЛИВО**: При внесенні будь-яких змін у існуючий код:
1. **Завжди робити рефакторинг** якщо бачиш:
   - Дублювання коду (порушення DRY)
   - Довгі функції (>20-30 рядків)
   - Складну вкладену логіку (>2-3 рівні)
   - Незрозумілі назви змінних/функцій
   - Порушення принципів SOLID
   - Code smells (великі класи, довгі списки параметрів, тощо)

2. **Процес рефакторингу**:
   - Перевір наявність тестів перед рефакторингом
   - Рефактори невеликими кроками
   - Переконайся, що тести проходять після кожного кроку
   - Якщо тестів немає - спочатку напиши тести

3. **Не рефактори**, якщо:
   - Це legacy код, який критично важливий і не покритий тестами
   - Рефакторинг виходить за межі поточної задачі (обговори з командою)
   - Немає часу для якісного тестування змін

### Test-Driven Development (TDD)
**ВАЖЛИВО**: При розробці нового функціоналу дотримуйся принципів TDD:

**Цикл TDD (Red-Green-Refactor)**:
1. **Red**: Спочатку напиши тест, який не проходить
2. **Green**: Напиши мінімальний код, щоб тест пройшов
3. **Refactor**: Покращуй код, зберігаючи тести зеленими

**Коли застосовувати TDD**:
- Нові API endpoints
- Нова бізнес-логіка в service-класах
- Складні алгоритми та обчислення
- Валідація даних
- Критичні функції системи

**Коли TDD не обов'язковий**:
- Прості CRUD операції без бізнес-логіки
- Конфігураційні класи
- Прості зміни в існуючому коді (якщо тести вже є)

**Структура тестів**:
```java
@ExtendWith(MockitoExtension.class)
class FeatureServiceTest {

    @Mock
    private FeatureRepository featureRepository;

    @InjectMocks
    private FeatureService featureService;

    @Test
    void shouldDoExpectedBehavior_whenGivenSpecificInput() {
        // Arrange
        var input = ...;

        // Act
        var result = featureService.method(input);

        // Assert
        assertThat(result).isEqualTo(expected);
    }
}
```

**Правила написання тестів**:
- Один тест = один сценарій (не тестуй декілька речей в одному тесті)
- Назви тестів мають описувати очікувану поведінку
- Використовуй AAA pattern: Arrange, Act, Assert
- Мокай зовнішні залежності через Mockito (@Mock, @InjectMocks)
- Тести мають бути ізольованими та детермінованими

**Команди для тестування**:
```bash
./gradlew test                                    # Запустити всі тести
./gradlew test --tests "*.UserServiceTest"        # Конкретний клас
./gradlew test --tests "*.UserServiceTest.shouldCreate*"  # Конкретний метод
./gradlew jacocoTestReport                        # Покриття коду (мінімум 80%)
```

### Library License Policy
**КРИТИЧНО ВАЖЛИВО**: Заборонено використовувати бібліотеки з GPL ліцензіями:
- **ЗАБОРОНЕНІ ліцензії**: GPL, LGPL, AGPL, GPL-2.0, GPL-3.0 (будь-які варіанти)
- **ДОЗВОЛЕНІ ліцензії**: MIT, Apache-2.0, BSD (2-Clause, 3-Clause), ISC, MPL-2.0
- **Dual-license**: Допустимі тільки якщо є можливість вибрати НЕ-GPL варіант

**Перед додаванням нової залежності**:
1. Перевір ліцензію на mvnrepository.com або в POM файлі бібліотеки
2. Якщо ліцензія містить GPL - шукай альтернативу
3. Якщо альтернативи немає - обговори з командою

**Перевірка ліцензій**:
```bash
./gradlew dependencies
```

---

## Project Overview

Logistics Hub — Java/Spring Boot backend для управління логістикою.

### Key Technologies

- **Java** 21
- **Spring Boot** 3.5.7
- **Spring Security** (JWT через JJWT 0.12.3)
- **PostgreSQL** 42.7.8 + **HikariCP** 7.0.2
- **Hibernate ORM** 6.4.1.Final (+ Envers для аудиту)
- **Flyway** 11.0.0 (міграції БД)
- **MapStruct** 1.5.5.Final (маппінг об'єктів)
- **Lombok** (зменшення boilerplate)
- **SpringDoc OpenAPI** 2.3.0 (Swagger UI)
- **Spring Actuator** (моніторинг)
- **JaCoCo** 0.8.11 (покриття коду, мінімум 80%)

### Testing Stack

- **JUnit 5** (Jupiter)
- **Mockito** (мокання залежностей)
- **AssertJ** (fluent assertions)
- **TestContainers** 1.19.3 (PostgreSQL для інтеграційних тестів)
- **H2** 2.2.224 (in-memory для unit тестів)
- **Spring Security Test**

## Repository Structure

```
logistics-hub/
├── src/main/java/com/translogistics/hub/logistics_hub/
│   ├── LogisticsHubApplication.java
│   ├── config/          # Конфігурація (Security, Hibernate, тощо)
│   ├── controller/      # REST контролери
│   ├── dto/
│   │   ├── request/     # Request DTOs
│   │   └── response/    # Response DTOs
│   ├── entity/          # JPA сутності
│   ├── exception/       # Обробка помилок
│   ├── mapper/          # MapStruct маппери
│   ├── repository/      # Spring Data / Hibernate репозиторії
│   ├── security/        # Безпека (JWT, фільтри)
│   ├── service/         # Бізнес-логіка
│   └── util/            # Утиліти
├── src/main/resources/
│   ├── application.yml          # Основна конфігурація
│   ├── application-dev.yml      # Dev профіль (PostgreSQL localhost:6543)
│   ├── application-prod.yml     # Production профіль
│   ├── logback-spring.xml       # Логування
│   └── db/migration/            # Flyway міграції (V0__init.sql, ...)
├── src/test/
│   ├── java/                    # Тести
│   └── resources/
│       └── application-test.yml # Test профіль (H2 in-memory)
├── build.gradle                 # Gradle конфігурація
└── settings.gradle
```

## Development Commands

```bash
./gradlew bootRun                 # Запуск (dev профіль)
./gradlew build                   # Збірка
./gradlew test                    # Тести
./gradlew jacocoTestReport        # Покриття коду
./gradlew dependencies            # Перелік залежностей
./gradlew clean build             # Чиста збірка
```

## Architecture Conventions

### Шари додатку

```
Controller → Service → Repository → Entity
     ↕            ↕
    DTO        Mapper (MapStruct)
```

- **Controller**: REST endpoints, валідація вхідних даних (@Valid), делегування в Service
- **Service**: Бізнес-логіка, транзакції (@Transactional)
- **Repository**: Доступ до даних (Spring Data JPA або Hibernate)
- **Entity**: JPA сутності з Lombok анотаціями
- **DTO**: Окремі request/response об'єкти (ніколи не повертай Entity напряму)
- **Mapper**: MapStruct інтерфейси для конвертації Entity <-> DTO

### Конвенції

- **Валідація**: Spring Validation (@Valid, Jakarta Validation анотації)
- **Міграції БД**: Flyway (формат: `V{номер}__{опис}.sql`). Не використовувати `hibernate.ddl-auto` в production
- **API документація**: SpringDoc OpenAPI (Swagger UI на `/api/v1/swagger-ui.html`)
- **Профілі**: `dev` (локальна PostgreSQL), `test` (H2 in-memory), `prod`
- **Порт**: 8080, контекст `/api/v1`
- **Логування**: SLF4J + Logback (конфігурація в `logback-spring.xml`)

## Common Development Workflows

### Додавання нового API endpoint

1. Створи Flyway міграцію в `src/main/resources/db/migration/`
2. Створи Entity в `entity/`
3. Створи Repository в `repository/`
4. Створи Request/Response DTO в `dto/`
5. Створи MapStruct Mapper в `mapper/`
6. Створи Service в `service/`
7. Створи Controller в `controller/`
8. Напиши тести (unit + інтеграційні)

### Тестування

- **Unit тести**: JUnit 5 + Mockito + AssertJ (для service-класів)
- **Інтеграційні тести**: `@SpringBootTest` + H2 (test профіль) або TestContainers (PostgreSQL)
- **Покриття**: JaCoCo, мінімум 80%
- Тестовий профіль: `application-test.yml` (H2 in-memory, Flyway вимкнений, `ddl-auto: create-drop`)

### Environment Profiles

| Профіль | БД | Hibernate DDL | Swagger | Логування |
|---------|-----|---------------|---------|-----------|
| **dev** | PostgreSQL (localhost:6543) | validate | Увімкнений | DEBUG |
| **test** | H2 in-memory | create-drop | Вимкнений | DEBUG |
| **prod** | PostgreSQL (SSL) | validate | Вимкнений | WARN |
