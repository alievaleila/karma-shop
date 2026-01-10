## 🛒 Karma Shop - eCommerce Web Application
This project is a modern eCommerce "Shop Category" page built with Spring Boot and Thymeleaf. It features a dynamic hierarchical category system, product filtering, and a responsive UI based on the Karma eCommerce template.

# 🚀 Technology Stack
Backend: Java 17, Spring Boot 3.x

Database: PostgreSQL / MySQL (Spring Data JPA)

Template Engine: Thymeleaf

Architecture: MVC (Model-View-Controller)

Data Mapping: ModelMapper (for Entity to DTO conversion)

Frontend: Bootstrap 4, HTML5, CSS3, Linearicons

# ✨ Key Features
1. Dynamic Hierarchical Categories
Self-Referencing Relationship: Categories are stored in the database with a parent_id to create a tree structure.

Optimized Fetching: The repository uses findAllByParentIsNull() to fetch only top-level categories, preventing data redundancy in the UI.

Recursive Rendering: Subcategories are automatically mapped and displayed under their respective parent categories in the sidebar.

# 2. Product Management
Grid View: Products are displayed in a clean, responsive grid layout with price, discount, and action buttons (Add to Bag, Wishlist, etc.).

DTO Pattern: Data Transfer Objects are used to ensure a clean separation between database entities and the frontend view.

# 3. Advanced Filtering & Sorting
Sidebar filters for Brands, Colors, and Price ranges.

Sorting options (Default, Price, etc.) and pagination support.

# 📂 Key Components
CategoryRepository: Custom JPA queries for hierarchical data fetching.

CategoryServiceImpl: Business logic handling mapping from Entity to CategoryDto.

category.html: A dynamic Thymeleaf template utilizing layout:decorate and th:each loops.

# 🛠️ Installation & Setup
Clone the repository:

Bash

git clone https://github.com/alievaleila/karma-shop.git
Configure Database: Update src/main/resources/application.properties with your database credentials.

Run the application:

Bash

mvn spring-boot:run
