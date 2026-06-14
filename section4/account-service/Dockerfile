FROM maven:3.9.14-amazoncorretto-17-alpine AS builder

WORKDIR /app

COPY pom.xml .

COPY src ./src

RUN mvn clean package -DskipTests


# Start with a base image containing Java runtime
FROM openjdk:17-ea-17-slim
WORKDIR /app
#Information around who maintains the image
LABEL maintainer="tai05122001@gmail.com"\
       version="1.0"\
       description="Account service of T3Bank"

#Add the application's jar to the image
COPY --from=builder /app/target/*.jar app.jar

# exec the image
ENTRYPOINT ["java", "-jar","app.jar"]