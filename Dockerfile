# Imagen base con Java
FROM eclipse-temurin:20-jdk

# Copiar el JAR al contenedor
COPY target/JavaFX-MariaDB-App-1.0.0.jar app.jar

# Comando que se ejecuta al iniciar el contenedor
ENTRYPOINT ["java", "-jar", "/app.jar"]
