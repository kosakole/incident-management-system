FROM eclipse-temurin:17-jdk-jammy
WORKDIR /opt/discoveryServerBack
COPY target/*.jar discoveryServer.jar


ENTRYPOINT ["java", "-jar", "discoveryServer.jar"]