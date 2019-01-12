FROM openjdk:11-jdk-slim
COPY target/register.jar /opt/register.jar
ENTRYPOINT ["java",  "-Djava.net.preferIPv4Stack=true",  "-jar","/opt/register.jar"]
EXPOSE 5003
