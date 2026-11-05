# ===== 1. BUILD =====
FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /app

# Copiamos el pom primero para aprovechar el cache de dependencias
COPY pom.xml .

RUN mvn dependency:go-offline -B

# Copiamos todo el código
COPY src ./src

# Construimos el jar
RUN mvn clean package -DskipTests

# ===== 2. RUN =====
FROM eclipse-temurin:21-jre

WORKDIR /app

# Copiamos el jar desde el build stage
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]