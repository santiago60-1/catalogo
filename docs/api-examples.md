# API Examples

## Venues API

### 1. Create Venue

#### Request
```http
POST /venues
Content-Type: application/json

{
  "name": "Central Stadium",
  "address": "Main Ave #123",
  "capacity": 20000
}
```

#### Response
```http
HTTP/1.1 201 Created
Location: /venues/1
Content-Type: application/json

{
  "id": 1,
  "name": "Central Stadium",
  "address": "Main Ave #123",
  "capacity": 20000
}
```

### 2. List Venues

#### Request
```http
GET /venues
```

#### Response
```http
HTTP/1.1 200 OK
Content-Type: application/json

[
  {
    "id": 1,
    "name": "Central Stadium",
    "address": "Main Ave #123",
    "capacity": 20000
  }
]
```

## Events API

### 1. Create Event

#### Request
```http
POST /events
Content-Type: application/json

{
  "name": "Rock Concert",
  "description": "Rock Festival 2026",
  "venueId": 1,
  "date": "2026-03-15T20:00:00"
}
```

#### Response
```http
HTTP/1.1 201 Created
Location: /events/1
Content-Type: application/json

{
  "id": 1,
  "name": "Rock Concert",
  "description": "Rock Festival 2026",
  "venueId": 1,
  "date": "2026-03-15T20:00:00"
}
```

## Common Errors

### 1. Resource Not Found

```http
HTTP/1.1 404 Not Found
Content-Type: application/json

{
  "timestamp": "2025-10-28T10:00:00Z",
  "status": 404,
  "error": "Not Found",
  "message": "Venue not found",
  "path": "/venues/999"
}
```

### 2. Validation Error

```http
HTTP/1.1 400 Bad Request
Content-Type: application/json

{
  "timestamp": "2025-10-28T10:00:00Z",
  "status": 400,
  "error": "Bad Request",
  "message": "name: Venue name must not be blank",
  "path": "/venues"
}
```

## Examples with cURL

### Create Venue
```bash
curl -X POST http://localhost:8080/venues \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Central Stadium",
    "address": "Main Ave #123",
    "capacity": 20000
  }'
```

### Create Event
```bash
curl -X POST http://localhost:8080/events \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Rock Concert",
    "description": "Rock Festival 2026",
    "venueId": 1,
    "date": "2026-03-15T20:00:00"
  }'
```

Note: if your application runs on a different port, replace `8080` in the examples above with the port you configured (for example `8081`).

## Examples with HTTPie

### List Venues
```bash
http GET http://localhost:8080/venues
```

### Get Event by ID
```bash
http GET http://localhost:8080/events/1
```