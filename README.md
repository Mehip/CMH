# Company Management Helper in Spring

Its the app for company owners to manage their employees.
It enables us to check informations about empolees if they are currently in the company and if they fulfilling their job.
Its a perfect app to control your company even from the other side of the world.

## Technology stack:
Java, Spring, Maven, Hibernate, JUnit, Docker

# How to run application
1
  * Run postgres database on port 5432
    - docker command : `docker run -e POSTGRES_PASSWORD=docker -d -p 5432:5432 postgres`
  * Run the application, application by default will be available on port 8085

2
  * In terminal run command : `docker-compose up`
  * Run the application, application by default will be available on port 8085

# Swagger
1. Swagger documentation will be available on endpoint `*/swagger-ui.html`
