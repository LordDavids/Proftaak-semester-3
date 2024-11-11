# Use a lightweight Node.js base image
FROM node:lts-alpine

# Install simple HTTP server for serving static content
RUN npm install -g http-server

# Make the 'app' folder the current working directory
WORKDIR /app

# Copy both 'package.json' and 'package-lock.json' (if available)
COPY package*.json ./

# Install project dependencies
RUN npm install

# Copy project files and folders to the current working directory (i.e., 'app' folder)
COPY . .

# Build app for production with minification
RUN npm run build

# Ensure the build output directory exists
RUN mkdir -p dist

# Expose the port that the HTTP server will run on
EXPOSE 5173

# Start the HTTP server and serve files from the 'dist' directory
CMD ["http-server", "dist", "-p", "5173", "-b"]