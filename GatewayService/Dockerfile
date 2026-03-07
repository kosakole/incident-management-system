FROM eclipse-temurin:17-jdk-jammy
WORKDIR /opt/gatewayServiceBack
COPY target/*.jar gatewayService.jar


ENTRYPOINT ["java", "-jar", "gatewayService.jar"]