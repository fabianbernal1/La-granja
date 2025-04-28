# Usa Maven para construir la app
FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /app

# Copia pom.xml y descarga dependencias
COPY pom.xml .
RUN mvn dependency:go-offline

# Copia todo el código fuente
COPY src ./src

# Empaqueta la app
RUN mvn clean package -DskipTests

# Ahora usa una imagen base de Java
FROM eclipse-temurin:21-jdk

WORKDIR /app

# Copia solo el JAR generado desde la etapa anterior
COPY --from=build /app/target/lagranja-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
