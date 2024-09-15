# Use an official OpenJDK runtime as a parent image
FROM eclipse-temurin:17-jdk-alpine

# Set the working directory in the container
WORKDIR /app

# Copy the application's JAR file to the container
COPY target/JwtDemo-0.0.1.jar /app/JwtDemo-0.0.1.jar

# Make port 8080 available to the world outside this container
EXPOSE 8080

# Run the JAR file
ENTRYPOINT ["java","-jar","/app/JwtDemo-0.0.1.jar"]