# Build the application using Node.js
FROM node:18 AS build
WORKDIR /app

COPY package.json package-lock.json ./
RUN npm install
COPY . .

ARG VITE_API_URL
RUN echo "VITE_API_URL=${VITE_API_URL}" > .env
RUN cat .env

RUN npm run build

# Serve the application
FROM node:14-alpine
WORKDIR /app
COPY --from=build /app/dist /app/dist

RUN npm install -g serve

EXPOSE 5000
CMD ["serve", "-s", "dist", "-l", "5000"]