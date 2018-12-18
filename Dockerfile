FROM openjdk:8-jdk-alpine
COPY target/RMSReservationService-0.0.1-SNAPSHOT.jar
CMD ["java", "-jar", "/RMSReservationService-0.0.1-SNAPSHOT.jar", "--server.servlet.context-path=/rms-reservation" ,"&"]
