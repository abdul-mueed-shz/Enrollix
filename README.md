# Enrollix - Student Management System

Welcome to the **Enrollix** repository! Enrollix is a comprehensive student management system designed for institutions to manage student registration, course registration, course assignments, and much more. This project is built using **Spring Boot** and follows a **monolithic architecture**. It leverages **Apache Ignite** as its primary database and includes secure, scalable RESTful APIs.

## Features

- **Student Registration**: Register new students with detailed personal and academic information.
- **Course Management**: Manage course creation, updates, and deletion.
- **Course Registration**: Students can register for available courses.
- **Assignment Management**: Assign students to courses and manage their course load.
- **API-Driven**: Utilizes Spring Boot to create robust RESTful APIs for all functionalities.
- **Secure Authentication**: Integrated with secure authentication and authorization.
  
## Technology Stack

- **Spring Boot 3.1.8**: Used for creating the backend RESTful APIs.
- **Apache Ignite 2.14.0**: Serves as the distributed, in-memory database solution for fast and scalable data management.
- **Maven**: Project build and dependency management.
- **Java 17**: Used for development and ensuring up-to-date Java features and performance.
- **Lombok**: Reduces boilerplate code by generating getters, setters, and constructors.
- **MapStruct**: For mapping DTOs to domain models.

## Project Structure

- **Student Service**: Manages student registration, update, and deletion processes.
- **Course Service**: Handles course creation, management, and deletion.
- **Course Registration Service**: Manages the association of students with their courses.
- **Controllers**: Exposes the services via RESTful APIs.

## How to Get Started

### Prerequisites

- Java 17+
- Maven 3.6+
- Docker (optional for database management)

### Running the Application

1. Clone the repository:

   ```bash
   git clone https://github.com/abdul-mueed-shz/ServiceSphere
    ```

2. Navigate to the project directory:

  ```bash
  cd ServiceSphere
  ```

3. Run the application using Maven:

  ```bash
  mvn spring-boot:run
  ```

### Build and Deploy

  #### Maven Build
  You can build the application using Maven:

  ```bash
  mvn clean install
  ```

