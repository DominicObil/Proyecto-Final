FROM eclipse-temurin:21-jdk-alpine as build
WORKDIR /app
COPY . .
RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
COPY jwt-keystore.jks /app/jwt-keystore.jks
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
