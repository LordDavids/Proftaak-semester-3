FROM openjdk-22-jdk-slim

WORKDIR /app

RUN mv target/*.jar /app/app.jar

CMD ["java", "-jar", "/app/app.jar"]
