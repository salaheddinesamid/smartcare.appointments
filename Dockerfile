FROM eclipse-temurin:17-jdk
LABEL authors="salaheddine"

WORKDIR app/

COPY target/appointment-service-0.0.1-SNAPSHOT.jar appointment-service-0.0.1-SNAPSHOT.jar

EXPOSE 7070

ENTRYPOINT ["java", "-jar", "appointment-service-0.0.1-SNAPSHOT.jar"]