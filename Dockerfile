FROM openjdk:8-jre
ADD target/RMSReservationService-0.0.1-SNAPSHOT.jar /tmp/RMSReservationService-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/tmp/RMSReservationService-0.0.1-SNAPSHOT.jar", "&"]