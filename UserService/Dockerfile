FROM eclipse-temurin:17-jdk-jammy
WORKDIR /opt/userServiceBack
COPY target/*.jar userService.jar
ENTRYPOINT ["java", "-jar", "userService.jar"]