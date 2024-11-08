FROM openjdk:22-jdk-slim

WORKDIR /app

# Copy the JAR file to the container
COPY target/User-0.0.1-SNAPSHOT.jar app.jar

# Run the application
CMD ["java", "-jar", "app.jar"]
