# Build stage (creates the JAR)
FROM maven:3.8.6-eclipse-temurin-17 as builder
WORKDIR /app
COPY pom.xml .
COPY src src
RUN mvn clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copy the built JAR
COPY --from=builder /app/target/*.jar app.jar

# MySQL connection settings (override with Render env vars)
ENV SPRING_DATASOURCE_URL=jdbc:mysql://mysql-host:3306/your_db?useSSL==true
ENV SPRING_DATASOURCE_USERNAME=db_user
ENV SPRING_DATASOURCE_PASSWORD=db_pass
ENV SPRING_JPA_HIBERNATE_DDL_AUTO=update

EXPOSE 8080
CMD ["java", "-jar", "app.jar"]