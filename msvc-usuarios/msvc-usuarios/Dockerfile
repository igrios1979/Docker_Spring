# Usar la imagen base oficial de OpenJDK 17
FROM openjdk:22

# Establecer el directorio de trabajo en /app
WORKDIR /app

# Copiar el archivo JAR al directorio de trabajo
COPY ./target/msvc-usuarios-0.0.1-SNAPSHOT.jar .

# Exponer el puerto en el que la aplicación escuchará
EXPOSE 8001

# Definir el comando por defecto para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "msvc-usuarios-0.0.1-SNAPSHOT.jar"]
