FROM gradle:jdk11 AS builder
WORKDIR /home/gradle/patronage21-java-student-record
COPY --chown=gradle:gradle build.gradle settings.gradle ./
COPY --chown=gradle:gradle src/ ./src
RUN gradle build --no-daemon

FROM adoptopenjdk/openjdk11:alpine
RUN mkdir /app
COPY --from=builder /home/gradle/patronage21-java-student-record/build/libs/*.jar /app/patronage21-student-record.jar
EXPOSE 8080
ENTRYPOINT [ "java", "-jar", "/app/patronage21-student-record.jar" ]
