
## This is a Spring Boot project on Maven build .

### Overview

This application performs OCR on bills and extract relevant information from them.
For OCR I have used Nanonets service free version.

### H2 Database Setup

To run the H2 database locally, follow these steps:

1. Go to [H2 Console](http://localhost:8080/h2-console).
2. Set the JDBC URL to `jdbc:h2:mem:testdb`.
3. Use the username `sa` and password `password`.

This setup allows you to interact with the H2 database during development.

### API Documentation
Use the API collection present in the folder as OCR.postman_collection.json by importing in POSTMAN.

#### Build
````markdown
mvn clean install

