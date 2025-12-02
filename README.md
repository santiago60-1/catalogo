# Catalogo (Events Service)

**Overview**
- **Project:** Spring Boot microservice to manage `Venue` and `Event` entities.
- **Language / JDK:** Java 17
- **Build tool:** Maven
- **Database:** MySQL (default database configured as `tests`)

**Project structure**
- **Source:** `src/main/java/com/ticket/catalogo`
  - `adapters/in/web` — REST controllers (`EventoController`, `VenueController`)
  - `application/DTO` — API DTOs (`EventoDto`, `VenueDto`)
  - `application/service` — use cases / services (`EventoUseCase`, `VenueUseCase`)
  - `adapters/out/persistence/jpa/entity` — JPA entities (infrastructure)
  - `adapters/out/persistence/jpa/repository` — Spring Data JPA repositories
  - `domain/model` — clean domain models (`Evento`, `Venue`)

- **Resources / config:** `src/main/resources`
  - `application.yaml` — Spring Boot configuration (datasource, flyway, springdoc)
  - `db/migration` — Flyway migrations (e.g. `V1__init_schema.sql`)

**Key dependencies (see `pom.xml`)**
- `spring-boot-starter-web`, `spring-boot-starter-data-jpa`
- `spring-boot-starter-flyway` (runs DB migrations on startup)
- `springdoc-openapi-starter-webmvc-ui` (Swagger UI / OpenAPI)
- `mysql-connector-j` (MySQL JDBC driver)
- `lombok` (compile-time getters/setters)

**API endpoints (summary)**
- **Venue** (`/api/venues`)
  - `POST /api/venues` — Create a venue. Body: `VenueDto`.
  - `GET /api/venues` — List all venues. Response: `List<VenueDto>`.
  - `GET /api/venues/{id}` — Get venue by id. Response: `VenueDto`.
  - `PUT /api/venues/{id}` — Update venue. Body: `VenueDto`.
  - `DELETE /api/venues/{id}` — Delete venue.

- **Event** (`/api/eventos`)
  - `POST /api/eventos` — Create an event. Body: `EventoDto` (includes `venueId`).
  - `PUT /api/eventos/{id}` — Update an event. Body: `EventoDto`.
  - `GET /api/eventos/{id}` — Get event by id. Response: `EventoDto`.
  - `GET /api/eventos/search` — Search with optional filters: `estado`, `inicio`, `fin`, `venueId`.
  - `DELETE /api/eventos/{id}` — Delete event.

**DTOs and domain separation**
- DTOs are located in `src/main/java/com/ticket/catalogo/application/DTO` and include OpenAPI `@Schema` annotations with examples. The domain models in `domain/model` are kept clean (plain POJOs without presentation annotations).
- `EventoDto.toDomain()` builds an `Evento` and assigns a `Venue` containing only the `id`. The `EventoUseCase` then validates and loads the full `Venue` from the repository before persisting.

**Database and migrations**
- Flyway migrations live in `src/main/resources/db/migration`. There is an initial migration `V1__init_schema.sql` that creates the `venue` and `evento` tables.
- Flyway is enabled in `application.yaml` (`spring.flyway.enabled: true`) and configured with `baseline-on-migrate: true`.
- Default database connection uses the `tests` schema. These defaults can be overridden with environment variables:
  - `DB_HOST` (default: `localhost`)
  - `DB_PORT` (default: `3306`)
  - `DB_NAME` (default: `tests`)
  - `DB_USER` (default: `santiago`)
  - `DB_PASSWORD` (default: `Qwe.123*`)

**Run locally**
1. Ensure MySQL is available and the `tests` database exists (or create it):

```bash
mysql -u root -p -e "CREATE DATABASE IF NOT EXISTS tests CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;"
```

2. Build the project (downloads dependencies):

```bash
mvn -DskipTests package
```

3. Run the application (Flyway will apply migrations at startup):

```bash
mvn -DskipTests spring-boot:run
```

4. Verify:
- Swagger UI: `http://localhost:8080/swagger-ui/index.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`

**curl examples (using DTOs)**
- Create a venue:

```bash
curl -X POST http://localhost:8080/api/venues \
  -H "Content-Type: application/json" \
  -d '{"nombre":"Auditorio Nacional","capacidad":5000,"ubicacion":"Ciudad X"}'
```

- Create an event (use the `venueId` returned from the venue creation):

```bash
curl -X POST http://localhost:8080/api/eventos \
  -H "Content-Type: application/json" \
  -d '{"nombre":"Concierto Rock","fechaInicio":"2025-12-10T20:00:00","fechaFin":"2025-12-10T23:00:00","estado":"ACTIVO","venueId":1}'
```

**Notes about validation and dependencies**
- You may see a warning in logs about `NoProviderFoundException` for Bean Validation. If you want to use `@Valid` and annotations like `@NotNull`, `@Size`, etc., add the Hibernate Validator dependency:

```xml
<dependency>
  <groupId>org.hibernate.validator</groupId>
  <artifactId>hibernate-validator</artifactId>
</dependency>
```

- DTOs currently use Lombok. If your IDE shows compilation or annotation-processing errors, install the Lombok plugin or run `mvn -DskipTests package` to build.

**OpenAPI / Swagger**
- The project includes `springdoc-openapi-starter-webmvc-ui`. Springdoc exposes these endpoints:
  - `/v3/api-docs` (OpenAPI JSON)
  - `/swagger-ui/index.html` (UI)

If you want example payloads to appear directly in endpoint bodies (not just in schemas), I can either:
- Add `@RequestBody(content = @Content(examples = @ExampleObject(...)))` to the controllers, or
- Enrich `EventoDto` and `VenueDto` with more detailed examples.

**Contribution / recommended next steps**
- Add validation annotations (`jakarta.validation`) and use `@Valid` in controllers.
- Add integration tests for API endpoints (via `spring-boot-starter-test`).
- Add CI (GitHub Actions) for build and tests.
- Keep Flyway migrations ordered and incremental when changing the schema.

If you want, I can:
- generate additional docs in `docs/` (ERD, diagrams),
- add explicit request/response examples for Swagger UI, or
- create basic integration tests for `Venue` and `Event` endpoints.

---
_Automatically generated: project summary and quick start guide._

---
_Generado automáticamente: resumen y guía rápida para trabajar con el repositorio._
