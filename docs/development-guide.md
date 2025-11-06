# Development Guide

## Project structure

```
src/
├── main/
│   ├── java/
│   │   └── com/tiquetera/catalog/
│   │       ├── config/          # Configuration
│   │       ├── controller/      # REST controllers
│   │       ├── dto/             # Data Transfer Objects
│   │       ├── exception/       # Error handling
│   │       ├── mapper/          # DTO-Entity mappers
│   │       ├── model/           # Domain models
│   │       ├── repository/      # Repositories
│   │       └── service/         # Services
│   └── resources/
│       ├── application.yml      # Main configuration
│       ├── application-dev.yml  # Development profile
│       └── application-tes.yml  # Testing profile
```

## Main components

### 1. Controllers

Controllers implement the REST layer and are documented with OpenAPI:

```java
@RestController
@RequestMapping("/venues")
public class VenueController {
    @Operation(summary = "Create venue")
    @PostMapping
    public ResponseEntity<VenueDTO> create(@Valid @RequestBody VenueDTO dto) {
        // ...
    }
}
```

### 2. DTOs

DTOs include validation annotations and OpenAPI metadata:

```java
public class VenueDTO {
    @NotBlank(message = "The name must not be blank")
    @Schema(description = "Venue name", example = "Central Stadium")
    private String name;
    // ...
}
```

### 3. Mappers

Mappers convert between DTOs and domain entities:

```java
public class VenueMapper {
    public static Venue toDomain(VenueDTO dto) {
        // ...
    }
    
    public static VenueDTO toDto(Venue domain) {
        // ...
    }
}
```

## OpenAPI configuration

OpenAPI is configured in `OpenApiConfig.java`:

```java
@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI catalogOpenApi() {
        return new OpenAPI()
            .info(new Info()
                .title("Tiquetera Catalog API")
                .version("0.1.0")
                // ...
            );
    }
}
```

Notes:
- The project uses springdoc to generate OpenAPI documentation at runtime.
- When the application is running, the OpenAPI JSON is available at `/v3/api-docs` and the interactive Swagger UI at `/swagger-ui.html` (or `/swagger-ui/index.html`).
- Add `@Operation`, `@Tag` and `@ApiResponse` annotations to controllers and use `@Schema` on DTO fields to improve the generated docs.

## Project specifics and current contracts

- Database (development): H2 file-based database stored under `data/catalogdb` when running with the `dev` profile. The JDBC URL used in `application-dev.yml` is (example absolute path):

    `jdbc:h2:file:/home/Coder/Descargas/catalogo-us1 (1)/catalogo-us1/data/catalogdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE`

- Profiles:
    - `dev` — local development (H2 console enabled, debug logging)
    - `tes` — testing

- Important file locations:
    - `src/main/resources/application.yml` — base config (sets active profile to `dev` by default)
    - `src/main/resources/application-dev.yml` — development overrides (datasource, h2 console)
    - `logs/` — application logs (configured in `application.yml`)

## DTO / Entity contract notes

- `EventDTO` exposes:
    - `id` (Long)
    - `name` (String)
    - `description` (String)
    - `date` (String, ISO_LOCAL_DATE_TIME)
    - `category` (String)
    - `venueId` (Long)
    - `venueName` (String)

- `EventEntity` stores the event `date` as a `LocalDateTime` in the `date` column, and also has `createdAt`/`updatedAt` timestamps. The `EventMapper` performs conversion between the DTO `date` string and the entity `LocalDateTime`.

Edge cases to handle in code:
- When creating/updating an Event, the `venueId` must refer to an existing `VenueEntity` — otherwise the service throws `NotFoundException`.
- DTO `date` parsing: invalid ISO format will result in a null `date` conversion (mapper returns null); controllers may validate and return HTTP 400 if parsing fails.

## H2 console and path issues

- If the H2 web console complains that `Database '/home/Coder/test' not found`, you are using the default H2 URL. Replace it with the JDBC URL above or with the relative URL `jdbc:h2:./data/catalogdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE` from the console UI.
- If the console rejects the absolute path due to spaces/parentheses in the path, create a symlink to a path without spaces and use the alternate absolute URL.

## Troubleshooting common 500 errors

1. Start the app and reproduce the 500 error. Check logs (console and `logs/catalog.log`).
2. Look for stack traces referencing controller/service/mapper classes. Common root causes:
     - Mapping issues (null pointers when a required field is missing)
     - Validation failures not caught at controller level (MethodArgumentNotValidException handled globally)
     - Missing related entities (venue referenced by event not found)

## How to run and verify local environment

1. Create `data` directory and give correct permissions:

```bash
cd "catalogo-us1"
mkdir -p data
chmod u+rwx data
```

2. Run the app with the dev profile (default):

```bash
./mvnw spring-boot:run
```

3. Open Swagger UI: `http://localhost:8080/swagger-ui.html`

4. Open H2 Console: `http://localhost:8080/h2-console` and paste the JDBC URL shown above.

## Quick endpoint examples

- Create a venue (POST /venues):

```json
{
    "name": "Estadio Metropolitano",
    "address": "Avenida Principal 789",
    "city": "Bogotá",
    "capacity": 5000
}
```

- Create an event (POST /events):

```json
{
    "name": "Festival de Música Electrónica",
    "description": "El mejor festival de música electrónica",
    "category": "MUSIC",
    "date": "2025-12-15T18:00:00",
    "venueId": 1
}
```

## Error handling

The application uses a global exception handler in `GlobalExceptionHandler`:

```java
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ApiError> handleNotFound(/*...*/) {
        // ...
    }
}
```

## Development workflow

### 1. Add a new endpoint

1. Create a DTO with validation
2. Add required mappers
3. Implement business logic in the service
4. Create controller endpoint with OpenAPI annotations
5. Add unit tests

### 2. Code documentation

- Use JavaDoc for public classes and methods
- Include examples in `@Schema` annotations
- Document responses with `@ApiResponse`

Tip: keep JavaDoc and OpenAPI annotations in sync. JavaDoc helps developers while OpenAPI annotations are consumed by tools and the UI.

### 3. Tests

- Add unit tests for each component
- Include edge cases in test coverage
- Document test scenarios

## Code standards

1. Naming
   - CamelCase for classes and methods
   - Use descriptive names in English

2. Documentation
   - JavaDoc for public classes and methods
   - Add explanatory comments when necessary

3. Organization
   - One class per file
   - Group packages by feature

4. Validation
   - Use Jakarta Validation annotations
   - Validate in the service layer

## Git workflow

1. Create a branch for the feature
2. Develop and test locally
3. Update documentation
4. Open a pull request with a detailed description