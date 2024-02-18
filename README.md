
<h1 align="center"> Wishlist Management Application</h1>

<p align="center">
    <a href="https://www.java.com/" target="_blank">
        <img src="https://img.shields.io/badge/Java-17-red" alt="Java 17">
    </a>
    <a href="https://maven.apache.org/" target="_blank">
        <img src="https://img.shields.io/badge/Maven-3.9.1-blue" alt="Maven 3.8.1">
    </a>
    <a href="https://spring.io/projects/spring-boot" target="_blank">
        <img src="https://img.shields.io/badge/Spring Boot-3.2.2-brightgreen" alt="Spring Boot 3.2.2">
    </a>
   <a href="https://spring.io/projects/spring-security" target="_blank">
    <img src="https://img.shields.io/badge/Spring Security-6.2.1-brightgreen" alt="Spring Security Latest">
</a>
 
</p>

## Introduction:
This is a Wishlist Management REST API built with Spring Boot. It allows users to manage their wishlists by adding and deleting items.

## Prerequisites:
- Java 17
- MySQL
- Maven
- Postman (for testing API endpoints)

## Setup & Installation:

1. Clone the repository:
git clone https://github.com/itsmenitesh/wishlist_management.git

2. Update application properties:
## Update application properties:

properties
spring.datasource.url=jdbc:mysql://localhost:3306/databaseName
spring.datasource.username=username
spring.datasource.password=password
spring.datasource.driverClassName=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.show_sql=true
spring.jpa.properties.hibernate.use_sql_comments=true
spring.jpa.properties.hibernate.format_sql=true


3. Build the application: `mvn clean install`
4. Run the application: After the project is built successfully, you can run the Spring Boot application. Usually, Spring Boot applications have a main class annotated with @SpringBootApplication. You can run this class directly from your IDE, or by using Maven: mvn spring-boot:run

5. 
The application will start and run on: http://localhost:8080

## Running Tests:
To run the unit tests, use the following command: mvn test


## API Endpoints:
You can use Postman to test the API endpoints. Here are some examples:

- Register a new user:
- **POST** `http://localhost:8080/register`
- Body (raw JSON):
 ```json
 {
     "firstName":"firstName",
     "lastName":"lastName",
     "username":"username",
     "password":"password"   
 }
 ```

- Authenticate a user:
- **POST** `http://localhost:8080/login`
- Body (raw JSON):
 ```json
 {
     "username":"username",
     "password":"password"
 }
 ```

Note: after hitting this, you will get a JWT token you have to use that JWT token to do other tasks.

- Add an item to a user’s wishlist:
- **POST** `http://localhost:8080/api/wishlist`
- Body (raw JSON):
 ```json
 {
     "itemName": "New Wishlist Item"
 }
 ```

Note: You need to include the JWT token in the Authorization header for this request.

- Get a user’s wishlist:
- **GET** `http://localhost:8080/api/wishlist`

Note: You need to include the JWT token in the Authorization header for this request.

- Delete an item from user’s wishlist by id:
- **DELETE** `http://localhost:8080/api/wishlist/{id}`

## Deployment:
When deploying this application, consider the following:

- Secure your application by using HTTPS.
- Use a separate MySQL instance for production with secure credentials.
- Consider using a cloud provider like AWS, Google Cloud, or Azure for hosting your application.



    
## Author

👤 **Nitesh Choudhary**

* GitHub: [Nitesh choudhary](https://github.com/itsmenitesh)

* LinkedIn: [Nitesh choudhary](https://www.linkedin.com/in/itsmenitesh/)
    
---

## 🤝 Contributing

Contributions, issues and feature requests are welcome.
    
---
    
## Show your support

Give a ⭐️ if this project helped you!
    
---
    
## 📝 License

Copyright © 2024 [Nitesh Choudhary](https://github.com/itsmenitesh).<br />
    
---




