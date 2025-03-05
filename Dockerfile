# Use an official Java runtime
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy built JAR file into the container
COPY target/pet-project.jar app.jar

# Expose the application port
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "app.jar"]
