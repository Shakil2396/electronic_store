#use any version as per your project demand
FROM openjdk:18-alpine

#now build jar file in target folder...*is name of the file
ARG JAR_FILE=target/*.jar

#now copy this jar file in app.jar file give any name here app.jar
COPY ${JAR_FILE} app.jar

#for run the application then command
ENTRYPOINT ["java","-jar","/app.jar"]

#now with the help of above things we build image