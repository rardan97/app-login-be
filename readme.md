## Spring Boot - Login Jwt BE

## System Requirements

- Java openjdk : Version 17.0.2
- Spring Boot : version 3.4.1
- Database : MySQL
- Maven : Apache Maven 3.9.3
- Editor : Intellij IDEA 2023.1.1 Community Edition

## Dependencies

- Spring Web
- Spring Data JPA
- Spring Boot DevTools
- Mysql Driver
- Lombok
- jjwt-api
- jjwt-impl
- jjwt-jackson

## Run Project

1. clone project Spring Boot - Login Jwt BE
```
git clone https://github.com/rardan97/app-login-be.git
```
2. add database name "db_spring_auth_login" in MySQL

3. open project with intellij IDEA then edit config database in application.properties
```
path : upload-image-be/src/main/resources/application.properties
```

```
spring.application.name=app-login-be

spring.jpa.hibernate.ddl-auto=update
spring.datasource.url=jdbc:mysql://localhost:3306/db_spring_auth_login
spring.datasource.username=root
spring.datasource.password=
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect

blackcode.app.jwtSecret=5pAq6zRyX8bC3dV2wS7gN1mK9jF0hL4tUoP6iBvE3nG8xZaQrY7cW2fA
blackcode.app.jwtExpirationMs=3600000
blackcode.app.jwtRefreshExpirationMs=360000000

```

4. open terminal input command
```
mvn clean install 
```
5. if success next input command
```
mvn spring-boot:run
```