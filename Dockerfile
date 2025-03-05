# Use an official Maven image to build the JAR
FROM maven:3.8.6-openjdk-17 AS build
WORKDIR /app

# Copy the project files and build the JAR
COPY . .
RUN mvn clean package -DskipTests

# Use a smaller image to run the application
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copy the JAR from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the port
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "app.jar"]
