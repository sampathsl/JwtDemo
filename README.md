# Spring Boot Security JWT Test Application

## Table of Contents

1. [Introduction](#introduction)
2. [Requirements](#requirements)
3. [Setup](#setup)
4. [Building the Application](#building-the-application)
5. [Running the Application](#running-the-application)
6. [UI Components](#ui-components)
7. [API Endpoints](#api-endpoints)
8. [Testing](#testing)
9. [Troubleshooting](#troubleshooting)
10. [Contributing](#contributing)
11. [License](#license)

## Introduction

This is a test application demonstrating the use of Spring Boot Security with JWT (JSON Web Token) for authentication
and authorization.

## Requirements

- Java 17
- Gradle
- Docker (Optional)
- An IDE like IntelliJ IDEA

## Setup

1. **Clone the repository:**

   ```bash
   git clone https://github.com/sampathsl/JwtDemo.git
   cd JwtDemo
   ```

2. **Configure your IDE:**

    - Open the project in IntelliJ IDEA.
    - Make sure the Java SDK is set to version 17.
    - Configured with gradle (otherwise it will be automatically downloaded gradle.zip)

## Building the Application

### Using Gradle

```bash
./gradlew clean build
```

## Running the Application

### Using Gradle

```bash
./gradlew bootRun
```

### Using Docker

1. **Build the Docker image:**

   ```bash
   docker build -t spring-boot-security-jwt-app .
   ```

2. **Run the Docker container:**

   ```bash
   docker run -p 8080:8080 spring-boot-security-jwt-app
   ```

## UI Components

1. http://localhost:8080/ - Home Page
2. http://localhost:8080/login - Login Page
3. http://localhost:8080/home - After Login, Access Token Can Be Seen Here
4. http://localhost:8080/access-denied - Unauthorized Access

## API Endpoints

Below are some of the key API endpoints:

- **Sign Up (Register Application User):**
  POST /api/v1/register/user
    - Request Body:
      ```json
      { 
        "username": "sampath", 
        "password": "sampath@123", 
        "roles": ["USER"]
      }
      ```
    - Response:
      ```json
      {
        "id": 1,
        "username": "sampath",
        "password": "$2a$10$OL8rzYprRf8AhF9/b8RDsuIOjJr5RtmXrcL3mG.Zw9fAAB/cENpQ2",
        "role": "USER"
      }
      ```

- **Sign In:**
  POST /api/v1/user-login
    - Request Body:
      ```json
      { 
        "username": "sampath", 
        "password": "sampath@123" 
      }
      ```
    - Response:
      ```json
      { 
        "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyMTIzIiwiaWF0IjoxNzI2MjU0NDY3LCJleHAiOjE3MjYyNTgwNjd9.3ucSIxd986hHq57_0Ioigmp0eNurF4nmpR0STRLcSYFWap7YDeHZn70bE4FQKDW5RErnBCC2ipIi_NTSwemmLg" 
      }
      ```

**User Resource:**

*GET /api/v1/users*

    - Headers: 
    - Authorization: Bearer <JWT_TOKEN> 

**Post Resource:**

*GET /api/v1/posts*

    - Headers: 
    - Authorization: Bearer <JWT_TOKEN>

*GET /api/v1/posts/user/{user_id}*

    - Headers: 
    - Authorization: Bearer <JWT_TOKEN>

**Comment Resource:**

*GET /api/v1/comments*

    - Headers: 
    - Authorization: Bearer <JWT_TOKEN>

*GET /api/v1/comments/post/{post_id}*

    - Headers: 
    - Authorization: Bearer <JWT_TOKEN>

## Testing

1. **Unit Tests:**

   ```bash
   ./gradlew test
   ```

2. **Integration Tests Using Bearer Token:**

    - Sign in to get the JWT token.
    - Access the protected resources using the JWT token.

   Here is an example using `curl`:

   ```bash
   # Sign In
   curl -X POST http://localhost:8080/api/v1/user-login \
   -H "Content-Type: application/json" \
   -d '{"username":"user123", "password":"password123"}'
   
   # Access protected resource /api/v1/users
   curl -X GET http://localhost:8080/api/v1/users \
   -H "Authorization: Bearer $TOKEN"
   
   # Access protected resource /api/v1/posts
   curl -X GET http://localhost:8080/api/v1/posts \
   -H "Authorization: Bearer $TOKEN"
   
   # Access protected resource /api/v1/comments
   curl -X GET http://localhost:8080/api/v1/comments \
   -H "Authorization: Bearer $TOKEN"
   
   # Access protected resource /api/v1/posts/user/{user_id}
   curl -X GET http://localhost:8080/api/v1/posts/user/{user_id} \
   -H "Authorization: Bearer $TOKEN"
   
   # Access protected resource /api/v1/comments/post/{post_id}
   curl -X GET http://localhost:8080/api/v1/comments/post/{post_id} \
   -H "Authorization: Bearer $TOKEN"
   ```

## Troubleshooting

- **Common Issues:**
    - Ensure Java version is set to 17.
    - In-memory hard coded user database.
    - Verify JWT secret key consistency between environment configurations.


- **Logging:**
    - Logs can be found in the console output by default.
    - Configure log levels in `application.properties`.

## Contributing

1. Fork the repository from https://github.com/sampathsl/JwtDemo.git.
2. Create a new branch (`git checkout -b feature-xyz`).
3. Make your changes and commit (`git commit -m 'Add some feature'`).
4. Push to the branch (`git push origin feature-xyz`).
5. Create a Pull Request.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
