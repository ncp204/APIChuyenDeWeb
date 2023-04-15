#
# Build stage
#
FROM maven:3.8.4-jdk-11-slim AS build
COPY . .
RUN mvn clean package -DskipTests

#
# Package stage
#
FROM openjdk:11-jdk-slim
COPY --from=build /target/ChuyenDeWeb-1.0.0.jar ChuyenDeWeb.jar
ENV PORT=8080
# EXPOSE 8080
ENTRYPOINT ["java","-jar","ChuyenDeWeb.jar"]
