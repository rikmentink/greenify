# Stage 1: Build the application using Maven
FROM maven:3.8.4-openjdk-17-slim AS build

# Set the working directory
WORKDIR /app

# Copy the pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the rest of the application code and build the application
COPY src ./src
RUN mvn package -DskipTests

# Stage 2: Run the application using OpenJDK
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the built jar file from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose port 8080
EXPOSE 8443

# Set environment variable to specify the Spring profile
ENV SPRING_PROFILES_ACTIVE=ssl

# Run the jar file
ENTRYPOINT ["sh", "-c", "openssl pkcs12 -export -in /etc/letsencrypt/live/${DOMAIN}/fullchain.pem -inkey /etc/letsencrypt/live/${DOMAIN}/privkey.pem -out /app/certs/keystore.p12 -name tomcat -CAfile /etc/letsencrypt/live/${DOMAIN}/fullchain.pem -caname root -password pass:${PASSWORD} && java -jar /app/your-app.jar"]