# #use any version as per your project demand
# FROM openjdk:18-alpine
#
# #now build jar file in target folder...*is name of the file
# ARG JAR_FILE=target/*.jar
#
# #now copy this jar file in app.jar file give any name here app.jar
# COPY ${JAR_FILE} app.jar
#
# #for run the application then command
# ENTRYPOINT ["java","-jar","/app.jar"]
#
# #now with the help of above things we build image

# Stage 1: Build the Spring Boot JAR
FROM maven:3.9.6-eclipse-temurin-18 AS builder
WORKDIR /app

#Copy pom.xml and source code
COPY pom.xml .
COPY src ./src

#Build the application (skip tests for faster build)
RUN mvn clean package -DskipTests

#Stage 2: Run the JAR
FROM eclipse-temurin:18-jdk-alpine
WORKDIR /app

# # Copy the JAR built in the first stage
COPY --from=builder /app/target/*.jar app.jar

# # Run the app
ENTRYPOINT ["java","-jar","app.jar"]