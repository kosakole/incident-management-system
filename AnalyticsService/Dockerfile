FROM eclipse-temurin:17-jdk-jammy
WORKDIR /opt/analyticsServiceBack
COPY target/*.jar analyticsService.jar
ENTRYPOINT ["java", "-jar", "analyticsService.jar"]