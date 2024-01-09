FROM docker.io/eclipse-temurin:17.0.7_7-jre

WORKDIR /app

COPY target/ProfileNotification.jar /app/ProfileNotification.jar

EXPOSE 8085

CMD ["java", "-jar", "ProfileNotification.jar"]