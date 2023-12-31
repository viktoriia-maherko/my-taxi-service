
##  🚕TAXI-SERVICE 🚕

## Project description
```
This is my first project. It's a simple web-aplication 
that supports authentication, registration and other 
CRUD (Create, Read, Update, Delete) operations.
```
## 🎯 Features

- Registration like a driver;
- Authentication like a driver;
- Create/update/delete a manufacturer;
- Create/update/delete a driver;
- Create/update/delete a car;
- Display list of all manufacturers;
- Display list of all drivers;
- Display list of all cars;
- Add driver to a car;
- Remove driver from a car;
- Get all cars by drivers.

## 📄 Project structure (3-layer architecture)
- DAO (Data Access Layer): [src/main/java/taxi/dao](src/main/java/taxi/dao) 
- Service (Application Logic Layer): [src/main/java/taxi/service](src/main/java/taxi/service)
- Controllers (Presentation Layer): [src/main/java/taxi/controller](src/main/java/taxi/controller)

## ⚙️ Technologies
- JDK 11;
- Git;
- Apache Maven;
- Apache Tomcat 9.0.76;
- MySQL;
- ServletAPI;
- JSP;
- JSTL;
- Junit 5;
- Checkstyle plugin;
- HTML/CSS;
- Log4j2.

## 🦶 How to run the project on your computer
1. [x] Fork this repository;
2. [x] Clone forked repository;
3. [x] [Install](https://www.mysql.com/downloads/) MySQL;
4. [x] Configure Tomcat. !Note: let's install Tomcat 9.0.50. 
5. [x] Copy and run script from the [resources/init_db.sql](resources/init_db.sql) file.
6. [x] Configure `/src/main/java/taxi/util/ConnectionUtil.java` with your database connection details, including the URL, username, password, and JDBC driver.
7. [x] Done! Enjoy using the application.😉

P.S You can also test application [srs/test/java/taxi.service](srs/test/java/taxi.service).