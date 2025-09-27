# 🐦 Blogging Web App - Spring Boot Project

A **Blogging web application** built with **Spring Boot, Spring Security, JPA/Hibernate, Thymeleaf, and MySQL**.  
It allows users to **register, log in, create posts, edit/delete posts, view profiles, and search users**, while ensuring **secure authentication and authorization**.  

---

## 🚀 Features
- ✅ User Registration & Login  
- ✅ Secure Authentication & Authorization (Spring Security)  
- ✅ Create, Edit, Delete Posts  
- ✅ View All Posts by Users  
- ✅ Profile Page & User Search  
- ✅ Password Encryption with BCrypt  
- ✅ MVC Architecture with JPA Repositories  

---

## 🛠️ Tech Stack
- **Backend:** Spring Boot, Spring Security, JPA/Hibernate  
- **Frontend:** Thymeleaf, HTML, CSS  
- **Database:** MySQL  
- **Build Tool:** Maven  

---

## ⚙️ Setup & Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/your-username/twitter-clone.git
   cd twitter-clone
   
2. **Configure the database**

Update your application.properties (or application.yml) with MySQL details:

spring.datasource.url=jdbc:mysql://localhost:3306/twitter_clone
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect

3. **Build the project**
mvn clean install

4. **Run the application**
mvn spring-boot:run


**The app will start at:**
👉 http://localhost:8080
