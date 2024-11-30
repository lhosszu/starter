# BUILD
FROM maven:3.9.9-eclipse-temurin-21 AS build

COPY . /app
WORKDIR /app
RUN mvn package -DskipTests

# RUN
FROM eclipse-temurin:21@sha256:b5fc642f67dbbd1c4ce811388801cb8480aaca8aa9e56fd6dcda362cfea113f1

COPY target/starter.jar starter.jar
COPY script/entrypoint.sh /entrypoint.sh

RUN jar -xf starter.jar && chmod +x /entrypoint.sh

HEALTHCHECK --interval=10s --timeout=3s CMD curl -f http://localhost:8080/actuator/health || exit 1

ENTRYPOINT ["/entrypoint.sh"]
