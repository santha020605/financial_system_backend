# 💰 Finance Management System (Spring Boot)

A backend application to manage users and financial records with role-based access control, JWT authentication, and dashboard insights.

---

## 🚀 Features

* 👤 User Management (Create, Update, Delete)
* 🔐 JWT Authentication & Authorization
* 🛡️ Role-Based Access Control (ADMIN, ANALYST, VIEWER)
* 💸 Financial Records CRUD
* 🔍 Filter Records (by date, category, type)
* 📊 Dashboard Summary (Income, Expense, Balance)
* ⚠️ Input Validation & Error Handling
* 🗄️ Database Integration (MySQL)
* 📘 API Documentation using Swagger

---

## 🛠️ Tech Stack

* Java 17
* Spring Boot
* Spring Data JPA (Hibernate)
* MySQL
* JWT (JSON Web Token)
* Swagger (OpenAPI)

---

## 📂 Project Structure

com.zorvyn.finance
│── controller
│── service
│── repository
│── models
│── dto
│── configuration
│── DataInitializer.java

---

## 🔐 Authentication Flow

1. User logs in via `/users/login`
2. Server returns JWT token
3. Use token in header for secured APIs:

Authorization: Bearer <your_token>

---

## 👑 Default Admin Credentials

## Email: [santha@gmail.com](mailto:admin@gmail.com)

## Password: santha$123

> Created automatically when application starts.

---

## 📊 API Endpoints

### 🔹 User APIs

* POST `/users/create` → Create user
* POST `/users/login` → Login & get token
* GET `/users/all` → Get all users
* PUT `/users/update/{id}/role` → Update role
* PUT `/users/update/{id}/status` → Update status
* DELETE `/users/delete/{id}` → Delete user

---

### 🔹 Financial Record APIs

* POST `/records/create` → Create record
* GET `/records/all` → Get all records
* GET `/records/summary/month` → Summary by month
* GET `/records/summary/category` → Summary by category
* GET `/records/income/total` → Total income
* GET `/records/expense/total` → Total expense
* PUT `/records/update/{id}` → Update record
* DELETE `/records/delete/{id}` → Delete record
* GET `/records/filter` → Filter records

---

### 🔹 Dashboard API

* GET `/records/dashboard` → Income, Expense, Balance

---

## 🔍 Filtering Example

/records/filter?type=INCOME&category=Salary&date=2026-04-05

---

## ▶️ How to Run the Project

1. Clone the repository
2. Open in Eclipse / IntelliJ
3. Configure MySQL in `application.properties`

Example:

spring.datasource.url=jdbc:mysql://localhost:3306/finance_db
spring.datasource.username=root
spring.datasource.password=yourpassword

4. Run the application
5. Open Swagger:

[https://financial-system-backend.onrender.com](https://financial-system-backend.onrender.com/swagger-ui/index.html)

---

## 🌐 Live API Documentation

Add your deployed link here:

https://financial-system-backend.onrender.com/swagger-ui/index.html

---

## ⚙️ Key Technical Decisions

* Used **Spring Boot** for rapid backend development
* Implemented **JWT** for stateless authentication
* Used **DTO pattern** to avoid exposing entities and prevent serialization issues
* Applied **Role-Based Access Control** for secure endpoints
* Handled **lazy loading issues** using DTO mapping
* Used **COALESCE in JPQL** to avoid null aggregation errors
* Managed **transactions using @Transactional** for safe database operations

---

## ⚠️ Known Limitations

* No frontend UI (backend only)
* Basic role hierarchy (can be enhanced)
* No pagination implemented

---

## 🚀 Future Improvements

* Add pagination & sorting
* Add refresh tokens for JWT
* Implement frontend (React/Angular)
* Deploy using Docker

---

## 🙌 Author

Santhakumar

---

## ⭐ If you like this project

Give it a ⭐ on GitHub!
