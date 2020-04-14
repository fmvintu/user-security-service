# Security Service Control

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. A few information:
```
Application:
 Running port: 8090
 Application context: /user-security
 Example: http://localhost:8090/user-security/

Authentication - AuthController
 Create: POST /auth/signup
 Signin: POST /auth/sigin
    Will return a JWT that must be sent as "Bearer" header on each request 

User Information - UserInfoController
 GET /userInfos/userInfo?userName=<username>
    Will return user information
```
 
### Prerequisites

You must a MySQL database setup locally/remote with "user_security" setup. 

1. Install MySQL With docker:
```
# Docker compose for MySQL and "adminer":
version: '3'

services:
  mysqlsrv:
    image: mysql:5.7
    container_name: mysqlsrv
    environment:
      MYSQL_ROOT_PASSWORD: "MySql2020!"
      MYSQL_DATABASE: "user_security"
    ports:
      - "3306:3306"
    networks:
      - mysql-network

  adminer:
    image: adminer
    container_name: adminer
    ports:
      - 8080:8080
    networks:
      - mysql-network

networks: 
  mysql-compose-network:
    driver: bridge
# End

# Then run
docker-compose --project-name usersecproject up -d

# Check Adminer
http://localhost:8080/
```

2. Create database user:
```
# Execute the script inside the project:
    src/main/resources/flyway/db/create-user.sql
```

### Installing

Execute the checked out application
```
# Set your JAVA_HOME environment variable
export JAVA_HOME=<YOUR JAVA HOME>
# Go where the application is and
./mvnw spring-boot:run
```

Nothing more is required, the application - SpringBoot - is going to load all the required contexts: MySQL connection/Hikari database pool, Caffeine cache, Spring Mvc (with Tomcat)

## Running the tests

The tests can be run by the following command:

```
./mvnw test
```

## Documentation

You can find the swagger documentation by starting the application (check Installing) and pointing your browser to the following location:
 http://localhost:8090/user-security/swagger-ui.html

## Built With

* [Maven](https://maven.apache.org/) - Dependency Management
* [Spring Framework/Springboot](https://spring.io/projects/spring-boot) - Microservices framework used
* [Caffeine](https://github.com/ben-manes/caffeine) - A high performance cache
* [Hibernate](https://hibernate.org/) - JPA implementation
* [Swagger](https://swagger.io/) - API documentation
