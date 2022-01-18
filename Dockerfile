FROM alpine:3.14

RUN  apk update \
  && apk upgrade \
  && apk add --update openjdk11 tzdata curl unzip bash \
  && rm -rf /var/cache/apk/*

VOLUME /tmp
COPY target/monitor-*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]