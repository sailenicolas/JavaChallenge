
# Etapa 1: runtime con JRE
FROM eclipse-temurin:21-jre-jammy as runtime
WORKDIR /app

# Copiamos el jar ya construido (mvn package)
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

# Extraemos capas para mejores cachés
RUN java -Djarmode=layertools -jar app.jar extract

# Imagen final ligera
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
# Copiamos las capas
COPY --from=runtime /app/dependencies/ ./dependencies/
COPY --from=runtime /app/spring-boot-loader/ ./spring-boot-loader/
COPY --from=runtime /app/snapshot-dependencies/ ./snapshot-dependencies/
COPY --from=runtime /app/application/ ./application/

EXPOSE 8080
ENV JAVA_OPTS="-XX:MaxRAMPercentage=75.0"
ENTRYPOINT ["java","org.springframework.boot.loader.launch.JarLauncher"]
