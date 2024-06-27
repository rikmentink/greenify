# client/Dockerfile

# Use node image to build the frontend
FROM node:18 AS build

# Set working directory
WORKDIR /app

# Copy package.json and package-lock.json
COPY package.json package-lock.json ./

# Install dependencies
RUN npm install

# Copy the rest of the application code
COPY . .

# Set build arguments and create .env file dynamically
ARG VITE_API_URL
RUN echo "VITE_API_URL=${VITE_API_URL}" > .env

# Verify the created .env file
RUN cat .env

# Build the application
RUN npm run build

# Use nginx image to serve the frontend
FROM nginx:alpine

# Copy the built application from the build stage
COPY --from=build /app/dist /usr/share/nginx/html

# Copy the Nginx configuration files
COPY nginx.conf /etc/nginx/conf.d/default.conf

# Expose ports
EXPOSE 80 443

# Start nginx server
CMD ["nginx", "-g", "daemon off;"]