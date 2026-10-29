# сборка
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /build
COPY pom.xml .
RUN mvn -B dependency:go-offline
COPY src ./src
RUN mvn -B clean package -DskipTests

# запуск
FROM eclipse-temurin:17-jre
COPY --from=build /build/target/*.jar /app/app.jar
# data.txt пишется в рабочую папку, её будем выносить на хост
WORKDIR /app/data
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "/app/app.jar"]