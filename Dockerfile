FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY target/dwese-ticket-logger-api-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
