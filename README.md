# Purchase Application

## Overview
This is a Spring Boot application that provides services for managing purchases. It uses Maven, Actuator, and OpenAPI 3.

## Prerequisites
- Java 17
- Maven 3.8.4+
- Docker
- Docker Compose

## Setup

### Local Development

1. **Clone the repository:**
   ```bash
   git clone https://github.com/leo-def/purchase-microservice.git
   cd purchase-microservice
   ```

2. **Create a `.env` with content from `.env.backup` file :**


3. **Run the application:**
   ```bash
   ./mvnw spring-boot:run
   ```

### Health Check
The application exposes a health check endpoint using Spring Boot Actuator.
- URL: `http://localhost:8080/actuator/health`

### OpenAPI Documentation
The application uses OpenAPI 3 for API documentation.
- URL: `http://localhost:8080/swagger-ui.html`

## Git Flow

1. **Creating a feature branch:**
   ```bash
   git checkout -b feature/<<task-code>>-<<feature-desc>>
   ```

2. **Committing changes:**
   ```bash
   git add .
   git commit -m "<<prefix>>: commit desc"
   git push origin <<task-code>>-<<feature-desc>>
   ```

3. **Creating a pull request:**
   Go to your repository on GitHub and create a pull request from your feature branch to the `develop` branch.

## Running Tests
To run the tests locally:
```bash
./mvnw test
```

## Environment Variables
Environment variables are managed using `.env` and `.env.backup` files. Sensitive information should be placed in `.env`, which should not be committed to version control. Non-sensitive configuration values can be placed in `.env.backup`.

## Additional Information
Ensure that you follow the best practices for security and code quality. Review the Docker and Maven documentation for more details on configuration and usage.
