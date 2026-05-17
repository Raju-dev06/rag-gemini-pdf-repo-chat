# Stage 1: Build
FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .

# Build jar (skip tests for faster deploy)
RUN mvn clean package -DskipTests

# Stage 2: Runtime
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copy the built jar
COPY --from=build /app/target/ragGeminiWorking-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

# Limit memory for Render free tier
CMD ["java","-Xmx300m","-Xms128m","-jar","app.jar"]