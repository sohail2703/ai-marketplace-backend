# AI Marketplace Backend

A Spring Boot backend application for an AI Marketplace platform where users can discover AI tools, get AI-powered recommendations, and interact with an AI conversational assistant.

## Features

* JWT-based authentication
* Google OAuth2 authentication
* Role-Based Access Control (RBAC)
* AI tool marketplace
* AI-powered tool recommendations using Spring AI and Ollama
* Conversational AI chat
* Conversation history storage
* Tool categories and search
* Pagination
* Favorites
* User reviews and ratings
* Admin user management
* PostgreSQL database
* Redis integration
* Swagger API documentation
* Docker support

## Tech Stack

* Java 21
* Spring Boot
* Spring Security
* Spring Data JPA
* Spring AI
* Ollama
* PostgreSQL
* Redis
* JWT
* Google OAuth2
* Docker
* Maven

## Architecture

The application follows a layered architecture:

* Controller
* Service
* Repository
* Entity
* DTO
* Mapper
* Security
* Configuration
* Exception Handling

## Running the Application

### 1. Start Infrastructure

```bash
docker compose up -d
```

This starts:

* PostgreSQL
* Redis
* Ollama


### 2. Run the Application

```bash
mvn spring-boot:run
```

The application runs on:

```text
http://localhost:8080
```

## API Documentation

Swagger UI:

```text
http://localhost:8080/swagger-ui.html
```

OpenAPI documentation:

```text
http://localhost:8080/api-docs
```

## Docker

Build the application:

```bash
mvn clean package -DskipTests
```

Run the complete application using Docker:

```bash
docker compose up --build
```

## Author

Sohail Shaik
