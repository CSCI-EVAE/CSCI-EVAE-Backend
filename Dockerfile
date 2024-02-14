#FROM openjdk:17-jdk-alpine
#
#WORKDIR /app
#
#COPY build/libs/*.jar /app/app.jar
#
#EXPOSE 8080

#CMD ["java", "-jar", "/app/app.jar"]
FROM gradle:8.5.0-jdk21-alpine

WORKDIR /app

COPY build.gradle .
COPY settings.gradle .
COPY gradlew .

COPY src src

RUN gradle build -Pwar -x test

EXPOSE 9091

CMD ["java", "-jar", "build/libs/csa-back-0.0.1-SNAPSHOT.war"]