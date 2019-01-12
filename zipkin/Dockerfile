FROM openjdk:11-jdk-slim
ADD target/zipkin.jar app.jar
VOLUME /tmp
VOLUME /target
RUN bash -c 'touch /app.jar'
EXPOSE 9411
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
