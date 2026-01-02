# Stage 1: Build
# Use the official Maven Image to build the application
FROM maven:3.9.12-eclipse-temurin-21 AS build

# Set the working directory
WORKDIR /app

# COPY the pom.xml and download dependencies
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Run the JAR
# Use the smaller base image to run the application
FROM eclipse-temurin:21-jre

# Set the working directory
WORKDIR /app

# Copy the JAR file from the previous stage
COPY --from=build /app/target/*.jar app.jar

# Expose the application port (default is 8080)
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]


