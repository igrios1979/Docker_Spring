# Usar la imagen base oficial de OpenJDK 17
#FROM openjdk:22
FROM openjdk:24-slim-bullseye as bu

# Establecer el directorio de trabajo en /app/msvc-usuarios
WORKDIR /app/msvc-usuarios

# Copiar el archivo pom.xml y los archivos de Maven Wrapper al directorio de trabajo
COPY ./pom.xml /app
COPY ./msvc-usuarios/msvc-usuarios/.mvn ./.mvn
COPY ./msvc-usuarios/msvc-usuarios/mvnw .
COPY ./msvc-usuarios/msvc-usuarios/pom.xml .
# Copiar el resto de los archivos del proyecto al directorio de trabajo


# Dar permisos de ejecución al Maven Wrapper si no están dados ya
#RUN chmod +x ./mvnw

 #### Ejecutar el comando Maven Wrapper para compilar el proyecto
RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/

COPY msvc-usuarios/msvc-usuarios/src ./src
# Exponer el puerto en el que la aplicación escuchará
RUN ./mvnw  clean package -DskipTests

FROM openjdk:24-slim-bullseye

WORKDIR /app

COPY --from=bu /app/msvc-usuarios/target/msvc-usuarios-0.0.1-SNAPSHOT.jar .
EXPOSE 8001

# Definir el comando por defecto para ejecutar la aplicación al iniciar el contenedor
ENTRYPOINT ["java", "-jar", "./target/msvc-usuarios-0.0.1-SNAPSHOT.jar"]
