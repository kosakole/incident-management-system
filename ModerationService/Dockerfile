FROM eclipse-temurin:17-jdk-jammy
WORKDIR /opt/moderationServiceBack
COPY target/*.jar moderationService.jar


ENTRYPOINT ["java", "-jar", "moderationService.jar"]