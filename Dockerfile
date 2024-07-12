FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests


FROM openjdk:17.0.1-jdk-slim
MAINTAINER phunguyen
COPY --from=build /target/Demo-MultiTenant-MongoDB-0.0.1-SNAPSHOT.jar Demo-MultiTenant-MongoDB.jar
ENTRYPOINT ["java","-jar","Demo-MultiTenant-MongoDB.jar"]