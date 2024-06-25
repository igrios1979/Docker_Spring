# Usar la imagen base oficial de OpenJDK 17
#FROM openjdk:22
FROM openjdk:24-slim-bullseye as bu

# Establecer el directorio de trabajo en /app/msvc-usuarios
WORKDIR /app/msvc-cursos

# Copiar el archivo pom.xml y los archivos de Maven Wrapper al directorio de trabajo
COPY ./pom.xml /app
COPY ./msvc-cursos/msvc-cursos/.mvn ./.mvn
COPY ./msvc-cursos/msvc-cursos/mvnw .
COPY ./msvc-cursos/msvc-cursos/pom.xml .
# Copiar el resto de los archivos del proyecto al directorio de trabajo


# Dar permisos de ejecución al Maven Wrapper si no están dados ya
#RUN chmod +x ./mvnw

 #### Ejecutar el comando Maven Wrapper para compilar el proyecto
#RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/
RUN ./mvnw dependency:go-offline
COPY msvc-usuarios/msvc-usuarios/src ./src
# Exponer el puerto en el que la aplicación escuchará
RUN ./mvnw  clean package -DskipTests

FROM openjdk:24-slim-bullseye



WORKDIR /app

RUN mkdir ./logs

COPY --from=bu /app/msvc-cursos/target/msvc-cursos-0.0.1-SNAPSHOT.jar .
EXPOSE 8082

# Definir el comando por defecto para ejecutar la aplicación al iniciar el contenedor
CMD ["java", "-jar", "msvc-cursos-0.0.1-SNAPSHOT.jar"]
