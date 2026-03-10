# Use OpenJDK 17 JRE slim as the base image (more reliable than JDK slim)
FROM eclipse-temurin:17-jre

# Set the working directory inside the container
WORKDIR /app

# Copy the built JAR file into the container
COPY target/job-tracker-0.0.1-SNAPSHOT.jar app.jar

# Expose the port the app runs on (default Spring Boot port)
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "app.jar"]
