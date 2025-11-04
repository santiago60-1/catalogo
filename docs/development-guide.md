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