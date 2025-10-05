FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

# Variables de entorno para la DB
ENV DB_URL=jdbc:mariadb://mariadb:3306/test
ENV DB_USER=root
ENV DB_PASS=secret

# Copiar JAR
COPY target/JavaFX-MariaDB-App-1.0.0.jar /app/app.jar

# Copiar recursos
COPY src/main/resources /app/resources

# Comando para ejecutar
CMD ["java", "-jar", "app.jar"]
