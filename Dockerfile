
# Stage 1: Build the Spring Boot JAR
FROM maven:3.9.6-eclipse-temurin-17 AS builder
WORKDIR /app

# Copy pom.xml and download dependencies first (for caching)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy source code
COPY src ./src

# Build the application (skip tests for faster build)
RUN mvn clean package -DskipTests

# Stage 2: Run the Spring Boot app
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# Copy the JAR built in the first stage
COPY --from=builder /app/target/*.jar app.jar

# Run the app
ENTRYPOINT ["java","-jar","app.jar"]
