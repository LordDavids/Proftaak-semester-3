# build the maven project
FROM maven:3.9.4-openjdk-22 AS build

COPY pom.xml .
COPY src src

# Run the build command
RUN mvn clean package -DskipTests

# Use JDK 22 for the runtime stage
FROM openjdk:22-jdk-slim

COPY --from=build /target/User-0.0.1-SNAPSHOT.jar /app/app.jar

WORKDIR /app

# Run the application
CMD ["java", "-jar", "app/app.jar"]

