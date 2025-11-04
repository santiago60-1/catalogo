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

## 📄 License

This project is licensed under the Apache 2.0 License - see the [LICENSE](LICENSE) file for details.

## 👥 Team

- Santiago Ortega
- Contact: santiago@59782@gmail.com
