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

# Set environment variables for SSL
ARG DOMAIN
ENV DOMAIN=${DOMAIN}
ARG PASSWORD
ENV PASSWORD=${SSL_KEYSTORE_PASSWORD}

# Copy SSL certificates from the host machine
COPY /etc/letsencrypt/live/${DOMAIN}/fullchain.pem /app/certs/fullchain.pem
COPY /etc/letsencrypt/live/${DOMAIN}/privkey.pem /app/certs/privkey.pem

# Install OpenSSL to convert certificates
RUN apt-get update && apt-get install -y openssl

# Convert the SSL certificates to PKCS12 format
RUN mkdir -p /app/certs && \
    openssl pkcs12 -export \
    -in /app/certs/fullchain.pem \
    -inkey /app/certs/privkey.pem \
    -out /app/certs/keystore.p12 \
    -name tomcat \
    -CAfile /app/certs/fullchain.pem \
    -caname root \
    -password pass:${PASSWORD}

# Clean up OpenSSL
RUN apt-get remove --purge -y openssl && apt-get autoremove -y && rm -rf /var/lib/apt/lists/*

# Expose port 8080
EXPOSE 8443

# Set environment variable to specify the Spring profile
ENV SPRING_PROFILES_ACTIVE=ssl

# Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]