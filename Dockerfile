FROM eclipse-temurin:21

ADD target/starter.jar starter.jar

RUN jar -xf starter.jar

COPY script/entrypoint.sh /entrypoint.sh

RUN chmod +x /entrypoint.sh

EXPOSE 9090

ENTRYPOINT ["/entrypoint.sh"]
