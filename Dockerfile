FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY target/mi-app.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
