# Use Eclipse Temurin JDK 24 as the base image
FROM eclipse-temurin:24-jdk-jammy as builder

# Set working directory
WORKDIR /app

# Copy the Maven/Gradle files first (for better caching)
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

# Build the application
RUN ./mvnw clean package -DskipTests

# Create the final image with JRE only
FROM eclipse-temurin:24-jre-jammy

WORKDIR /app

# Copy the built artifact from builder stage
COPY --from=builder /app/target/*.jar app.jar

# Expose the port your application runs on (typically 8080 for Spring Boot)
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]