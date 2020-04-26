# Developing and Securing RESTful APIs with Spring Boot
This sample application shows how to develop a Restful api with
SpringBoot and secure it using Spring Security via JSON Web Tokens.

# How To Setup The Application And Run It
* Make sure you have gradle installed on your system and an IDE. If 
you don't have an IDE don't worry you can still follow with a text editor and 
the terminal.
* Make sure you have Java 10 installed on your system. [Get it here](http://www.oracle.com/technetwork/java/javase/downloads/jdk10-downloads-4416644.html)
* Clone the repository using the command `git clone https://github.com/vladimirfomene/springboot-auth-updated.git`
* Run `gradle bootrun` to build and run the project or run the project from your ide(make sure you build it before running)

# Notes

blog: https://auth0.com/blog/implementing-jwt-authentication-on-spring-boot/

#### Requests to the RESTful Spring boot API

```bash
# issue a GET request to see the (empty) list of tasks
curl http://localhost:8080/tasks

# issue a POST request to create a new task
curl -H "Content-Type: application/json" -X POST -d '{
    "description": "Buy some milk(shake)"
}'  http://localhost:8080/tasks

# issue a PUT request to update the recently created task
curl -H "Content-Type: application/json" -X PUT -d '{
    "description": "Buy some milk"
}'  http://localhost:8080/tasks/1

# issue a DELETE request to remove the existing task
curl -X DELETE http://localhost:8080/tasks/1
```

### Should create

##### User registration

- implemented in user package.

##### Authentication & Authorization

- implement an authentication filter to issue JWTS to users sending credentials
- implement an authorization filter to validate requests containing JWTS,
- create a custom implementation of `UserDetailsService` to help Spring Security loading user-specific data in the 
framework,
- and extend the `WebSecurityConfigurerAdapter` class to customize the security framework to our needs.

##### Integrating the Security Filters on Spring Boot

- Now that we have both security filters properly created, 
- We have to configure them on the Spring Security filter chain.
- Spring Security doesn't come with a concrete implementation of UserDetailsService that we could use out of the box 
with our in-memory database.

#### Requests to check Authentication & Authorization

```bash
# issues a GET request to retrieve tasks with no JWT
# HTTP 403 Forbidden status is expected
curl http://localhost:8080/tasks

# registers a new user
curl -H "Content-Type: application/json" -X POST -d '{
    "username": "admin",
    "password": "password"
}' http://localhost:8080/users/sign-up

# logs into the application (JWT is generated)
curl -i -H "Content-Type: application/json" -X POST -d '{
    "username": "admin",
    "password": "password"
}' http://localhost:8080/login

# issue a POST request, passing the JWT, to create a task
# remember to replace xxx.yyy.zzz with the JWT retrieved above
curl -H "Content-Type: application/json" \
-H "Authorization: Bearer xxx.yyy.zzz" \
-X POST -d '{
    "description": "Buy watermelon"
}'  http://localhost:8080/tasks

# issue a new GET request, passing the JWT
# remember to replace xxx.yyy.zzz with the JWT retrieved above
curl -H "Authorization: Bearer xxx.yyy.zzz" http://localhost:8080/tasks
```
