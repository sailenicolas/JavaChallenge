
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /build

ARG MODULE
ENV MODULE=${MODULE}

COPY . .
RUN mvn -B -e -ntp -DskipTests -pl core -am package \
    && cp $(find core/target -name "*.jar" | head -n 1) /build/core.jar

RUN mvn deploy:deploy-file -DgroupId=com.empresa -DartifactId=mi-proyecto-core -Dversion=1.0.0 -Durl=file:./repo/ -DrepositoryId=project.local -DupdateReleaseInfo=true -Dfile=/build/core.jar

RUN mvn -B -e -ntp -DskipTests -pl ${MODULE} -am package \
    && cp $(find ${MODULE}/target -name "*.jar" | head -n 1) /build/app.jar

FROM eclipse-temurin:21-jre-alpine AS runtime
WORKDIR /app

COPY --from=build /build/app.jar app.jar

ENTRYPOINT ["java","-jar","app.jar"]
