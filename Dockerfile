FROM eclipse-temurin:21

COPY target/starter.jar starter.jar

RUN jar -xf starter.jar

COPY script/entrypoint.sh /entrypoint.sh

RUN chmod +x /entrypoint.sh

ENTRYPOINT ["/entrypoint.sh"]
