FROM openjdk:17-jdk-slim

ARG JAR_FILE=target/*.jar
COPY build/libs/*.jar app.jar


ENV PORT=8080
EXPOSE ${PORT}

ENTRYPOINT ["java","-jar","/app.jar"]