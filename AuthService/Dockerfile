FROM eclipse-temurin:17-jdk-jammy
WORKDIR /opt/authServiceBack
COPY target/*.jar authService.jar


ENTRYPOINT ["java", "-jar", "authService.jar"]