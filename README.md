# 🚗 RideGo — Ride Booking Backend

RideGo is a ride-booking application backend developed using **Java and Spring Boot**. It provides REST APIs for handling client requests and demonstrates backend development concepts such as database integration, authentication, authorization, and API documentation.

The backend is deployed on Railway, and its REST APIs can be explored through Swagger UI.

## 🌐 Live Demo

* **Live Backend:** https://ridego-production-9696.up.railway.app
* **Swagger API Documentation:** https://ridego-production-9696.up.railway.app/swagger-ui/index.html
* **GitHub Repository:** https://github.com/Aarif519/Ridego

## 🛠️ Tech Stack

* **Language:** Java
* **Framework:** Spring Boot
* **Database:** MySQL
* **ORM:** Spring Data JPA / Hibernate
* **Security:** Spring Security and JWT
* **API Design:** REST APIs
* **API Documentation:** Swagger / OpenAPI
* **Build Tool:** Maven
* **Deployment:** Railway

## ✨ Key Areas

* REST API development using Spring Boot
* Database integration with MySQL
* Database operations using Spring Data JPA
* Authentication and authorization using Spring Security and JWT
* Interactive API documentation using Swagger UI
* Cloud deployment using Railway

## 📡 API Documentation

Swagger UI provides an interactive interface for exploring and testing the available API endpoints.

Using Swagger UI, you can:

* View available endpoints and HTTP methods.
* Inspect request parameters and JSON request bodies.
* Execute API requests.
* Examine HTTP status codes and responses.
* Provide a JWT Bearer token for secured endpoints.

**Explore the APIs:**
https://ridego-production-9696.up.railway.app/swagger-ui/index.html

## ⚙️ Running the Project Locally

### Prerequisites

* Java Development Kit (JDK)
* MySQL
* Git
* Maven Wrapper included in the repository

### 1. Clone the repository

```bash
git clone https://github.com/Aarif519/Ridego.git
```

### 2. Navigate to the project directory

```bash
cd Ridego
```

### 3. Configure environment variables

Configure the database connection and JWT secret using the environment variables expected by the application:

* `DB_HOST`
* `DB_PORT`
* `DB_NAME`
* `DB_USERNAME`
* `DB_PASSWORD`
* `JWT_SECRET`

Create the required MySQL database before starting the application.

**Security note:** Never commit database passwords, JWT secrets, or other sensitive credentials to GitHub.

### 4. Run the application

On Windows PowerShell:

```powershell
.\mvnw.cmd spring-boot:run
```

On Linux or macOS:

```bash
./mvnw spring-boot:run
```

Ensure the database configuration and required environment variables are set before running the application.

### 5. Explore the APIs

Once the application starts locally, open:

http://localhost:8080/swagger-ui/index.html

## 🎯 Project Objective

The objective of RideGo is to build a backend foundation for a ride-booking application while gaining practical experience with Java, Spring Boot, REST APIs, relational databases, application security, and deployment.

## 🚧 Future Improvements

* Develop a responsive frontend.
* Integrate the frontend with the backend REST APIs.
* Improve the end-to-end user experience.
* Expand and test ride-booking workflows as the application evolves.

## 👨‍💻 Developer

**Arif Ansari**

* GitHub: https://github.com/Aarif519

---

*Built as a learning project to strengthen practical Java backend development skills.*
