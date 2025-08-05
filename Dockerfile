# Build stage
FROM maven:3.8.6-eclipse-temurin-17 AS builder
WORKDIR /app
# Copy POM first for caching
COPY StainlesSteel/pom.xml .
RUN mvn -f pom.xml dependency:go-offline
# Copy source code
COPY StainlesSteel/src src
RUN mvn -f pom.xml clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=builder /app/StainlesSteel/target/*.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]