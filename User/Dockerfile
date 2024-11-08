FROM openjdk:22-jdk-slim

COPY ./target/User-0.0.1-SNAPSHOT.jar /app/app.jar

WORKDIR /app

# Run the application
CMD ["java", "-jar", "app/app.jar"]
