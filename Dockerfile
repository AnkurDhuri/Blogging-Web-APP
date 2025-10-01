FROM maven:3.9.2-eclipse-temurin-22 AS build

WORKDIR /app


COPY pom.xml mvnw ./
COPY .mvn .mvn

RUN chmod +x mvnw && ./mvnw dependency:go-offline -B


COPY src src

RUN ./mvnw clean package -DskipTests


FROM eclipse-temurin:22-jdk-alpine

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar


EXPOSE 8080


ENTRYPOINT ["java", "-jar", "app.jar"]
