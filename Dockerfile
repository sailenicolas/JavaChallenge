
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /build

ARG MODULE
ENV MODULE=${MODULE}

COPY . .

RUN mvn -B -e -ntp -DskipTests -pl ${MODULE} -am package \
    && cp $(find ${MODULE}/target -name "*.jar" | head -n 1) /build/app.jar

FROM eclipse-temurin:21-jre-alpine AS runtime
WORKDIR /app

COPY --from=build /build/app.jar app.jar

ENTRYPOINT ["java","-jar","app.jar"]
