FROM openjdk:22-jdk-slim

WORKDIR /app

# Copy the JAR file to the container
COPY User/target/*.jar /app/app.jar

# Run the application
CMD ["java", "-jar", "/app/app.jar"]
