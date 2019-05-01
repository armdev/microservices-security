FROM amazoncorretto:8
VOLUME /tmp
ADD target/eureka1.jar /app.jar
RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
EXPOSE 8762
