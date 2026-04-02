## 🛒 Karma Shop — E-Commerce MVC Ecosystem

#  📝 Description

Karma Shop is a comprehensive e-commerce platform designed to solve the complexity of online retail management. It provides a seamless bridge between administrative control and customer experience.

## What problem it solves:

For Businesses: Automates inventory tracking, coupon management, and order processing via a structured Admin Dashboard.

For Users: Offers a secure and intuitive shopping flow, from product discovery to secure checkout and order tracking.

Technically: Demonstrates how to maintain a clean, modular N-Tier architecture in a high-traffic simulation environment.

## 🛠️ Tech Stack
Backend: Java 17, Spring Boot 3.x, Spring Security (Role-based Auth).

Data: Spring Data JPA, Hibernate, PostgreSQL.

Frontend: Thymeleaf, HTML5, CSS3.

Mapping & Logic: MapStruct/ModelMapper, Lombok.

Build Tool: Maven.

## ⚙️ Setup Instructions

To run this project locally, follow these steps:

Clone the repository:

Bash
git clone https://github.com/alievaleila/karma-shop.git
Database Setup: Create a PostgreSQL database named karma_db.

Build the project:

Bash
mvn clean install
Run the application:

Bash
mvn spring-boot:run

## 🔑 Environment Variables

To run this application, you will need to add the following variables to your application.properties or system environment:

SPRING_DATASOURCE_USERNAME - postgres

SPRING_DATASOURCE_PASSWORD - 12345

SPRING_MAIL_USERNAME - SMTP email for password reset functionality.

SPRING_MAIL_PASSWORD - SMTP app password.

## 🚀 Usage Examples

1. Authentication Flow
Admin Access: Use /admin/** routes to manage products and view orders.

User Registration: Access /register to create a new profile and start shopping.

2. Applying Coupons
During checkout, enter a valid coupon code (e.g., SAVE20) handled by the CouponController to apply dynamic discounts to the CartTotal.

3. Order Tracking
Users can navigate to /orders/tracking to check the real-time status of their purchases (e.g., PENDING, SHIPPED, DELIVERED).

📂 Architecture Preview
The project follows a strict package structure as seen in the source:

config/: Security and Bean initializations.

dto/: Request/Response data encapsulation.

model/: Relational Database Entities.

service/: Business logic orchestration.
