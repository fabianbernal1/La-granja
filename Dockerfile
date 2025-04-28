# Usa una imagen oficial de Java 21 con soporte JDK
FROM eclipse-temurin:21-jdk

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el JAR generado al contenedor
COPY target/lagranja-0.0.1-SNAPSHOT.jar app.jar

# Expone el puerto 8080 (el mismo que usa tu Spring Boot)
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
