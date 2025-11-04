# Deployment Guide

## 1. Environment preparation

### System requirements
- Java 17 or newer
- Minimum 512MB RAM
- 1GB disk space
- Port 8080 available (configurable)

### Environment variables
```bash
JAVA_HOME=/path/to/java
SPRING_PROFILES_ACTIVE=dev
SERVER_PORT=8080
```

## 2. Build

### Using the Maven wrapper
```bash
./mvnw clean package
```

### Verify the build
```bash
ls -l target/catalog-0.0.1-SNAPSHOT.jar
```

## 3. Deployment options

### 1. Run as a systemd service

Create a systemd service file:
```ini
[Unit]
Description=Tiquetera Catalog API
After=network.target

[Service]
Type=simple
User=tiquetera
ExecStart=/usr/bin/java -jar /path/to/catalog-0.0.1-SNAPSHOT.jar
Restart=always
Environment="SPRING_PROFILES_ACTIVE=prod"
Environment="SERVER_PORT=8080"

[Install]
WantedBy=multi-user.target
```

### 2. Docker container

Included Dockerfile:
```dockerfile
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
```

Build and run:
```bash
docker build -t tiquetera/catalog .
docker run -p 8080:8080 tiquetera/catalog
```

## 4. Configuration

### application.yml
```yaml
spring:
  application:
    name: catalog
server:
  port: 8080
```

You can override the server port at runtime with either an argument or environment variable:

```bash
java -jar catalog-0.0.1-SNAPSHOT.jar --server.port=8081
# or
export SERVER_PORT=8081
java -jar catalog-0.0.1-SNAPSHOT.jar
```

### Profiles
- dev: Local development
- tes: Testing
- prod: Production

### Activate profile
```bash
java -jar catalog-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
```

## 5. Monitoring

### Actuator endpoints
- /actuator/health
- /actuator/info
- /actuator/metrics

### Logs
- Location: ./logs/catalog.log
- Level: INFO by default

### Logging configuration
```yaml
logging:
  level:
    root: INFO
    com.tiquetera: DEBUG
  file:
    name: ./logs/catalog.log
```

## 6. Backup and recovery

### Backup configuration files
```bash
cp -r src/main/resources/application*.yml /backup/
```

### Restore
```bash
cp /backup/application*.yml src/main/resources/
```

## 7. Security

### Recommendations
1. Use HTTPS in production
2. Configure CORS properly
3. Implement rate limiting
4. Monitor access logs

### HTTPS configuration
```yaml
server:
  ssl:
    key-store: classpath:keystore.p12
    key-store-password: ${KEYSTORE_PASSWORD}
    key-store-type: PKCS12
```

## 8. Scalability

### Horizontal
- Load balancing with nginx/haproxy
- Distributed sessions if required

### Vertical
- Increase heap: -Xmx512m
- Tune connection pools

## 9. Troubleshooting

### Common issues

1. Port already in use
```bash
netstat -tulpn | grep 8080
kill -9 PID
```

2. Out of memory
```bash
java -Xmx512m -jar catalog-0.0.1-SNAPSHOT.jar
```

3. Check health endpoint
```bash
curl http://localhost:8080/actuator/health
```

## 10. Maintenance

### Daily
- Monitor logs
- Check disk space
- Verify health endpoints

### Weekly
- Review metrics
- Backup configuration
- Analyze logs

### Monthly
- Update dependencies
- Review security
- Optimize performance