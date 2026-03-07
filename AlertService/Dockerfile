FROM eclipse-temurin:17-jdk-jammy
WORKDIR /opt/alertServiceBack
COPY target/*.jar alertService.jar
ENTRYPOINT ["java", "-jar", "alertService.jar"]