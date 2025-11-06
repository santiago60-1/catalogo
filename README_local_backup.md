## Tiquetera Catalog API

REST API for managing events and venues in the Tiquetera system.

## 📋 Table of Contents
1. [Features](#features)
2. [Requirements](#requirements)
3. [Installation](#installation)
4. [Usage](#usage)
5. [API Endpoints](#api-endpoints)
6. [Documentation](#documentation)
7. [Tests](#tests)
8. [Troubleshooting](#troubleshooting)

## ✨ Features

- Full CRUD for Venues
- Full CRUD for Events
- Input validation
- OpenAPI / Swagger documentation
- In-memory persistence (for demo/testing)
- Global error handling

## 📦 Requirements

- Java 17 or newer
- Maven 3.9+ (wrapper `mvnw` included)
- Port 8080 available (configurable)

Additional runtime details
- H2 is configured for local development in the `dev` profile. By default the project now uses a file-based H2 DB located at `data/catalogdb` inside the project when the `dev` profile is active.
- Spring profile default: `dev` (see `src/main/resources/application.yml`)
## 🚀 Installation

1. Clone the repository:
```bash
git clone https://github.com/tiquetera/catalog.git
cd catalog
```

2. Ensure the Maven wrapper is executable:
```bash
chmod +x ./mvnw
```

3. Build the project:
```bash
./mvnw clean package
```

## 💻 Usage

### Start the application

Using the Maven wrapper (recommended):
```bash
./mvnw spring-boot:run
```

Using the packaged JAR:
```bash
java -jar target/catalog-0.0.1-SNAPSHOT.jar
```

### Configuration

#### Available profiles
- `dev`: Local development
- `tes`: Testing

To run with a specific profile:
```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

#### Change port
```bash
./mvnw spring-boot:run -Dspring-boot.run.arguments="--server.port=8081"
```

## 🔗 API Endpoints

### Venues

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST   | `/venues` | Create a venue |
| GET    | `/venues` | List venues |
| GET    | `/venues/{id}` | Get venue by ID |
| PUT    | `/venues/{id}` | Update venue |
| DELETE | `/venues/{id}` | Delete venue |

### Events

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST   | `/events` | Create an event |
| GET    | `/events` | List events |
| GET    | `/events/{id}` | Get event by ID |
| PUT    | `/events/{id}` | Update event |
| DELETE | `/events/{id}` | Delete event |

## 📖 Documentation

Detailed documentation is available at:

1. Swagger UI (when the app is running):
   - Swagger UI: http://localhost:8080/swagger-ui.html (or http://localhost:8080/swagger-ui/index.html)
   - OpenAPI JSON: http://localhost:8080/v3/api-docs

2. Static docs:
   - [Development Guide](docs/development-guide.md)
   - [API Examples](docs/api-examples.md)
   - [Deployment Guide](docs/deployment-guide.md)

## 🗄 H2 Console (development)

When running with the `dev` profile the application enables the H2 web console at:

- http://localhost:8080/h2-console

Connection info to paste into the H2 Console (JDBC URL field):

- Relative (recommended when console and app run together):
   jdbc:h2:./data/catalogdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE

- Absolute (guaranteed path; adjust if you moved the project):
   jdbc:h2:file:/home/Coder/Descargas/catalogo-us1 (1)/catalogo-us1/data/catalogdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE

Driver: org.h2.Driver

User: sa (password empty)

Notes:
- If your filesystem path contains spaces or parentheses, paste the absolute URL exactly (including spaces). If the console rejects the path, create a symlink to a path without spaces and use that path instead (example below).

Create a symlink (example):

```bash
ln -s "/home/Coder/Descargas/catalogo-us1 (1)/catalogo-us1" /home/Coder/catalogo-us1_noespacios
```

Then use the JDBC URL:

`jdbc:h2:file:/home/Coder/catalogo-us1_noespacios/data/catalogdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE`

### Documentation structure
```
docs/
├── images/           # Screenshots and diagrams
│   ├── api/         # API examples
│   ├── setup/       # Setup guides
│   └── tests/       # Test results
├── api-examples.md   # API usage examples
├── development-guide.md # Developer guide
└── deployment-guide.md  # Deployment guide
```

## 🧪 Tests

Run all tests:
```bash
./mvnw test
```

Run a specific test class:
```bash
./mvnw test -Dtest=EventControllerTest
```

## 🔧 Troubleshooting

### Logs
Logs are stored at:
- Development: `./logs/catalog.log`
- Console: default level INFO

### Common Issues

1. Port already in use:
```bash
# start on an alternate port (example 8081)
./mvnw spring-boot:run -Dspring-boot.run.arguments="--server.port=8081"
```

Or set the environment variable when running the packaged JAR:
```bash
java -jar target/catalog-0.0.1-SNAPSHOT.jar --server.port=8081
```

2. Clean the project:
```bash
./mvnw clean
```

3. Check dependencies:
```bash
./mvnw dependency:tree
```

### H2 console: "Database '/home/Coder/test' not found"

- This message appears when the H2 console is using the default URL (`jdbc:h2:~/test`). Replace that value with one of the JDBC URLs shown in the "H2 Console" section above.
- Ensure the `data` directory exists in the project root and has read/write permissions for the user running the JVM.

### 500 Internal Server Error while hitting endpoints

- Check the application logs (`./logs/catalog.log` or console) for the stack trace. Common causes found in this project:
   - DTO/Entity field mismatches (e.g. `EventDTO.date` vs `EventEntity.date`) — recent fixes updated `EventEntity` to include a `date` field and the `EventMapper` to map it.
   - Missing or invalid `venueId` when creating events (service will throw `NotFoundException` if venue not found).

If you want, run the application and paste the stack trace here and I can help find the root cause.

## 📄 License

This project is licensed under the Apache 2.0 License - see the [LICENSE](LICENSE) file for details.

## 👥 Team

- Santiago Ortega
- Contact: santiago@59782@gmail.com
