FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/pet-project-3.4.3.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
