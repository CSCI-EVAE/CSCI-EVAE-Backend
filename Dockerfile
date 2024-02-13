FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY build/libs/*.jar /app/app.jar

EXPOSE 8080

CMD ["java", "-jar", "/app/app.jar"]


