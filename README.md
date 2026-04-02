## 🛒 Karma Shop — E-Commerce MVC Ecosystem

##  📝 Description

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

<img width="1841" height="892" alt="image" src="https://github.com/user-attachments/assets/ceb71958-7a7d-4fb3-936f-59f23e38635f" />
<img width="1886" height="899" alt="image" src="https://github.com/user-attachments/assets/f89d5800-9de3-4c3c-abfc-c040f3b83caa" />
<img width="1820" height="890" alt="image" src="https://github.com/user-attachments/assets/ca4e269b-7113-472d-b4bf-9c3529912fd9" />
<img width="1810" height="882" alt="image" src="https://github.com/user-attachments/assets/e35feda0-ed0f-4e11-b64a-0378dc885c3a" />
<img width="1821" height="895" alt="image" src="https://github.com/user-attachments/assets/3673365a-5b02-4aca-bfae-958608275840" />
<img width="1784" height="902" alt="image" src="https://github.com/user-attachments/assets/52ff4234-da6d-4352-b016-1650d1583b24" />
<img width="1810" height="894" alt="image" src="https://github.com/user-attachments/assets/8be26013-551c-457d-9400-362c40f215b1" />
<img width="1797" height="881" alt="image" src="https://github.com/user-attachments/assets/5780f94f-fe96-4586-aab5-82e5a93985e5" />
<img width="1888" height="894" alt="image" src="https://github.com/user-attachments/assets/21275ec6-07c1-4498-9974-306d227065d8" />
<img width="1849" height="872" alt="image" src="https://github.com/user-attachments/assets/de05190c-9c96-4b23-bae7-2684ecac7198" />
<img width="1863" height="900" alt="image" src="https://github.com/user-attachments/assets/8007626f-4eee-459b-b8d2-a7410b10e6e1" />
<img width="1919" height="884" alt="image" src="https://github.com/user-attachments/assets/e0135b2e-1dab-480a-8556-07936997a009" />
<img width="1903" height="889" alt="image" src="https://github.com/user-attachments/assets/d153a4d6-8792-440e-9c2b-fcc8d276fe96" />

