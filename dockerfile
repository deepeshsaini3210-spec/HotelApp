FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

COPY hotel-app/target/hotel-app-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8081

CMD ["java", "-jar", "app.jar"]