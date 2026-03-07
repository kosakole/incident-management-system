FROM eclipse-temurin:17-jdk-jammy
WORKDIR /opt/configServerBack
COPY target/*.jar configServer.jar


ENTRYPOINT ["java", "-jar", "configServer.jar"]