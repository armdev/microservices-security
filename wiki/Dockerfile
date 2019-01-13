FROM openjdk:11-jdk-slim
COPY target/wiki.jar /opt/wiki.jar
ENTRYPOINT ["java",  "-Djava.net.preferIPv4Stack=true",  "-jar","/opt/wiki.jar"]
EXPOSE 5004
