FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
COPY target/booking-service-*.jar /app/booking-service.jar
EXPOSE 8080
USER 10001
ENTRYPOINT ["java", "-XX:MaxRAMPercentage=70", "-jar", "/app/booking-service.jar"]
