# User management example
---
Simple spring boot + mysql + angular application to demonstrate how to add/delete users.

## Getting started
---
These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

* [Prerequisites](#prerequisites)
* [Summary of set up](#summary-of-set-up)

### Prerequisites

The things you need to install the software and how to install them.

1. Java 12+ download [here][1]
2. Maven 3.5.0+
3. MySLQ 8.0.16+
4. GIT

### Summary of set up
1. Prepare your MySQL DB instance by execution following commands (you need to connect to your MySQL instance first):
    - `create database user_management;`
    - `create user 'admin'@'%' identified by 'admin';`
    - `grant all on user_management.* to 'admin'@'%';`
2. To get the project up and running first of all clone or pull the repository to your local machine and then navigate to the project in terminal and run the following commands:
    - `mvn spring-boot:run`: It will build the whole project with the front-end part. The project will be running on default profile with mysql db.
    - alternatively, if you don't wanna install latest mysql db version, you can run the project with `dev` profile, so it means embedded h2 db will be used: `mvn spring-boot:run -Dspring-boot.run.profiles=dev`. NOTE: in such case step 1 is optional.    
3. Navigate to http://localhost:8080
4. Default email is `admin@g.com` and password is `admin`

[1]: https://jdk.java.net/12/
