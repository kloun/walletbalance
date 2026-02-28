# multi-stage Dockerfile for building and running the Spring Boot walletbalance application

# -------- build stage --------
FROM maven:3.9.3-eclipse-temurin-17 AS build
WORKDIR /workspace

# copy pom and source code, then package the application
COPY pom.xml ./
COPY src ./src

# run maven to build the jar (skip tests to speed up)
RUN mvn -B package -DskipTests

# -------- runtime stage --------
FROM eclipse-temurin:21-jre

# copy the fat jar from the build stage
ARG JAR_FILE=target/*.jar
COPY --from=build /workspace/target/*.jar /app.jar

# expose default port
EXPOSE 8080

ENTRYPOINT ["java","-jar","/app.jar"]
