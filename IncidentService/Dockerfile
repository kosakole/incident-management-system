FROM eclipse-temurin:17-jdk-jammy
WORKDIR /opt/incidentServiceBack
COPY target/*.jar incidentService.jar


ENTRYPOINT ["java", "-jar", "incidentService.jar"]