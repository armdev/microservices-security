FROM openjdk:11-jdk-slim
COPY target/browser.jar /opt/browser.jar
ENTRYPOINT ["java",  "-Djava.net.preferIPv4Stack=true",  "-jar","/opt/browser.jar"]
EXPOSE 5002
