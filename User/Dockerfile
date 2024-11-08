FROM openjdk:22-jdk-slim

WORKDIR /app

# Copy the JAR file to the container
COPY target/User-0.0.1-SNAPSHOT.jar /app/app.jar

# Run the application
CMD ["java", "-jar", "/app/app.jar"]
