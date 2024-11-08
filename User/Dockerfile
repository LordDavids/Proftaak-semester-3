# build the maven project
FROM openjdk:22-jdk

COPY pom.xml .
COPY src src

RUN chmod +x ./mvn
# Run the build command
RUN mvn clean package -DskipTests

# Use JDK 22 for the runtime stage
COPY --from=build /target/User-0.0.1-SNAPSHOT.jar /app/app.jar

WORKDIR /app

# Run the application
CMD ["java", "-jar", "app/app.jar"]

