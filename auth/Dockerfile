#FROM openjdk:11-jdk-slim
FROM amazoncorretto:8
COPY target/auth.jar /opt/auth.jar
ENTRYPOINT ["java",  "-Xms1024m", "-Xmx2048m", "-XX:MetaspaceSize=96m", "-XX:+UseG1GC", "-XX:MaxGCPauseMillis=20",  "-XX:InitiatingHeapOccupancyPercent=35", "-XX:G1HeapRegionSize=16M", "-XX:MinMetaspaceFreeRatio=50", "-XX:MaxMetaspaceFreeRatio=80", "-jar","/opt/auth.jar"]
EXPOSE 5001
