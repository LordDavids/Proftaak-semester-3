# Build stage
FROM maven:3.9.9-amazoncorretto-21 AS build

WORKDIR /app

# Copy the pom.xml and source files
COPY pom.xml .
COPY src ./src

# Run the build command
RUN mvn clean package -DskipTests

# Runtime stage
FROM openjdk:22-jdk

# Copy the built .jar file from the build stage
COPY --from=build /app/target/User-0.0.1-SNAPSHOT.jar /app/app.jar

WORKDIR /app

EXPOSE 8080

# Run the application
CMD ["java", "-jar", "app.jar"]
