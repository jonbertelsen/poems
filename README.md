# Poem API

Simple REST API for managing poems, built with Javalin + Hibernate + PostgreSQL.

## Tech Stack

- Java 25
- Maven
- Javalin 7 (HTTP server + routing)
- Hibernate ORM 7 + JPA (`EntityManagerFactory`)
- PostgreSQL
- Jackson (JSON serialization/deserialization)
- SLF4J + Logback (logging)
- JUnit 6 + Testcontainers (testing dependencies in `pom.xml`)

## Architecture Overview

The project follows a layered structure:

- `routes` layer: declares HTTP endpoints
- `controllers` layer: request/response handling and HTTP status codes
- `daos` layer: database operations
- `entities` layer: JPA entities mapped to tables
- `dtos` layer: API-facing data objects
- `config` layer: app and database bootstrapping

Request flow:

1. `Main` starts the app and creates an `EntityManagerFactory`.
2. `ApplicationConfig` sets Javalin base path to `/api`.
3. `ApplicationConfig` enables route overview at `/routes` and registers endpoints from `Routes`.
4. `Routes` mounts poem routes under `/poems`.
5. `PoemRoutes` maps HTTP methods to `PoemController` methods.
6. `PoemController` calls `PoemDAO` for persistence.
7. `PoemDAO` uses Hibernate/JPA to read/write PostgreSQL.

## Endpoint Overview

Base URL: `http://localhost:7070/api`

| Method | Endpoint | Description | Controller method |
|---|---|---|---|
| GET | `/` | Health/hello endpoint | inline lambda (`"Hello World"`) |
| GET | `/poems` | Get all poems | `getPoems` |
| GET | `/poems/{id}` | Get poem by id | `getById` |
| POST | `/poems` | Create one poem | `createPoem` |
| POST | `/poems/batch` | Create multiple poems from JSON array | `createPoems` |
| PUT | `/poems/{id}` | Update poem by id | `update` |
| DELETE | `/poems/{id}` | Delete poem by id | `delete` |

## Example Request Bodies

Create single poem (`POST /api/poems`):

```json
{
  "title": "Sunrise paints the sky",
  "poem": "Sunrise paints the sky, Gentle waves kiss sandy shores, Day awakens slow.",
  "style": "Haiku"
}
```

Create batch (`POST /api/poems/batch`):

```json
[
  {
    "title": "Sunrise paints the sky",
    "poem": "Sunrise paints the sky, Gentle waves kiss sandy shores, Day awakens slow.",
    "style": "Haiku"
  },
  {
    "title": "Whispers of the breeze",
    "poem": "Whispers of the breeze, Autumn leaves dance on the ground, Silent moonrise glow.",
    "style": "Haiku"
  }
]
```

## Useful Files

- `src/main/java/app/Main.java`
- `src/main/java/app/config/ApplicationConfig.java`
- `src/main/java/app/routes/Routes.java`
- `src/main/java/app/routes/PoemRoutes.java`
- `src/main/java/app/controllers/PoemController.java`
- `src/main/resources/http/poem.http` (ready-made HTTP client requests)
