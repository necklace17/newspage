FROM gradle:jdk17 as builder

COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon

FROM openjdk:17-slim

RUN mkdir /app
WORKDIR /app

COPY --from=builder /home/gradle/src/build/libs/*SNAPSHOT.jar /app/application.jar

ENTRYPOINT ["java", "-jar", "application.jar"]
EXPOSE 8080
