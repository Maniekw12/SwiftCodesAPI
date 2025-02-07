# Use a Gradle image to build the application
FROM gradle:jdk17 AS build

# Set the working directory in the container
WORKDIR /app

# Copy Gradle project files
COPY build.gradle settings.gradle gradlew ./
COPY gradle ./gradle

# Make gradlew executable
RUN chmod +x gradlew

# Pre-download dependencies to cache them
RUN ./gradlew --no-daemon dependencies

# Copy the application source code
COPY src ./src

# Build the application
RUN ./gradlew --no-daemon clean bootJar

# Use a minimal JDK runtime for running the application
FROM eclipse-temurin:17-jre-ubi9-minimal

# Set the working directory in the container
WORKDIR /app

# Copy the built JAR file from the build stage
COPY --from=build /app/build/libs/*.jar app.jar

# Expose the default Spring Boot port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
