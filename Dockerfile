# Use an official Java runtime
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the source code
COPY . .

# Install Maven
RUN apt-get update && apt-get install -y maven

# Build the application inside the container
RUN mvn clean package

# Expose the application port
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "target/pet-project.jar"]
