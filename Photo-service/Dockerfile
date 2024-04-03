FROM gradle:7.5 as builder
WORKDIR /usr/src/app
COPY ../src ./src
COPY ../build.gradle.kts .
RUN ["gradle", "bootJar"]

EXPOSE 7001

FROM openjdk:17
ARG JAR_FILE=build/libs/*.jar
COPY --from=builder /usr/src/app/${JAR_FILE} app.jar
#RUN apt-get update; apt-get -y install curl
#RUN microdnf update \
#   && microdnf install curl \
#    && microdnf install jq
ENTRYPOINT ["java", "-jar", "/app.jar"]