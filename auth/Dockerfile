FROM openjdk:11-jdk-slim
COPY target/auth.jar /opt/auth.jar
ENTRYPOINT ["java",  "-Djava.net.preferIPv4Stack=true",  "-jar","/opt/auth.jar"]
EXPOSE 5001
