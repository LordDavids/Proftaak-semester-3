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

# Expose port 8080 to the outside world
EXPOSE 5173

# Start the HTTP server and serve files from the 'dist' directory
CMD ["http-server", "dist", "-b"]
