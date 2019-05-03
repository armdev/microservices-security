FROM amazoncorretto:8
#FROM fabric8/java-alpine-openjdk8-jdk:1.6.2
ADD target/zipkin.jar app.jar
VOLUME /tmp
VOLUME /target
RUN bash -c 'touch /app.jar'
EXPOSE 9411
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
