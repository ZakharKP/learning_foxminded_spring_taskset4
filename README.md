# Task 4: Rest Service

This repository contains my solutions for the Rest Service task set of the [Foxminded](https://foxminded.ua/) Java Spring Development course. The tasks in this set focus on develop Car Database microservice with rest API.

## Introduction

In this project, I have implemented a Car Database microservice with RESTful API endpoints. The main goal is to provide a reliable and efficient way to manage information about car manufacturers, models, and years, with security features and documentation.



<br>
<details>
<summary>Task List</summary>
<br>


<br>
<details>
  <summary>Task 4.1 Planning: Car Database</summary>
  <br>

Important: In the next series of tasks you're going to develop Car Database microservice with rest API, make sure to give repo a meaningful name (ex. car-rest-service)

**Assignment**

Analyze and decompose Car DB Reset service  (create UML class diagram for application) based on attached csv data.

Decompose provided data into db entities
</details>

<details>
  <summary>Task 4.2 Create RestApi endpoints</summary>
  <br>
  
**Assignment**

1.  Create new Spring Boot project using [Initializer](https://start.spring.io/) with dependencies:

*   **Spring Web** (Build web, including RESTful, applications using Spring MVC. Uses Apache Tomcat as the default embedded container.)
*   **Spring Data JPA** (Persist data in SQL stores with Java Persistence API using Spring Data and Hibernate.)
*   **Flyway Migration** (Version control for your database so you can migrate from any version (incl. an empty database) to the latest version of the schema.)
*   **H2 Database** or **PostgreSQL** Driver of your choice

2.  Create model and schema initalizing sql migration script according with your UML diagrama
3.  Create JPA repositories and service layer with base CRUD operations
4.  Following best practices on RestAPI design - implement required endpoints to manage API model  
    *   Implement create/update/list/delete operations for provided data
        *   manufacturers
        *   manufacturers/model
        *   manufacturers/model/year  
            ex: \`POST /api/v1/manufacturers/toyota/models/corolla/2001\`
    *   Implement search endpoint with parameters like \`manufacturer\` ,'model', 'minYear', 'maxYear, 'category'  
        ex: \`GET /api/v1/cars?manufacturer=mercedes&minYear=2005\`
    *   all list endpoints should support pagination and sorting
5.  Cover controllers with tests
6.  Add additional components tests if required
</details>

<br>
<details>
  <summary>Task 4.3 Adding Security</summary>
  <br>
  
**Assignment**  
  
Add security to your service, so that  

*   GET requests accessible for all users
*   POST requests - only authorized users.

Consult with mentor and add secure your create/update/delete endpoints with Auth0 or KeyCloak  
  

*   Follow [tutorial](https://auth0.com/blog/spring-boot-authorization-tutorial-secure-an-api-java/) to secure your endpoints with Auth0 
*   Follow [tutorial](https://docs.codenow.com/docs/java-spring-boot-complex-examples/secured-spring-boot-rest-api-with-keycloak) to secure your endpoints with KeyCloak
 
</details>


<br>
<details>
  <summary>Task 4.4 OpenApi V3</summary>
  <br>
 
**Assignment**

1.  Add [SpringDocV2](https://springdoc.org "https://springdoc.org") to your project.
2.  Annotate classes with [OpenApi annotations](https://www.baeldung.com/spring-rest-openapi-documentation)  
    
3.  [Make sure](https://stackoverflow.com/a/59898875) that generated spec include your JWT security scheme
 
</details>
<br>
<details>
  <summary>Task 4.5 Dockerization</summary>
  <br>
  
**Assignment**

1.  [Create docker image](https://spring.io/guides/topicals/spring-boot-docker/ "https://spring.io/guides/topicals/spring-boot-docker/") for your rest service app
2.  [Create docker-compose file](https://www.baeldung.com/spring-boot-postgresql-docker) to include all required infrastructure to run your service app
 
</details>
</details>
<br>
<details>
  <summary>Technologies Used</summary>
  <br>
  
### The project is built using the following Technologies and Dependencies

- **Spring Boot:** 2.7.16
- **Java:** 1.8

#### Dependencies

- **Spring Boot Dependencies:**
  - spring-boot-starter-data-jpa
  - spring-boot-starter-thymeleaf
  - spring-boot-starter-web
  - spring-boot-starter-tomcat (provided scope)
  - spring-boot-starter-security
  - spring-boot-starter-test

- **Database and Persistence Dependencies:**
  - flyway-core
  - postgresql (runtime scope)

- **Additional Libraries:**
  - lombok (optional)
  - bson
  - commons-csv (version 1.10.0)

- **Security Dependencies:**
  - spring-security-config
  - spring-security-test (test scope)

- **Testing Dependencies:**
  - junit-jupiter (test scope)
  - testcontainers-bom (imported from org.testcontainers)
  - mockito-inline (test scope)

- **OpenAPI and Documentation Dependencies:**
  - springdoc-openapi-ui (version 1.7.0)
  - springdoc-openapi-webmvc-core (version 1.7.0)
  - springdoc-openapi-webflux-ui (version 1.7.0)
  - springdoc-openapi-hateoas (version 1.7.0)
  - springdoc-openapi-data-rest (version 1.7.0)
  - springdoc-openapi-security (version 1.7.0)
  - springdoc-openapi-javadoc (version 1.7.0)

- **Other Dependencies:**
  - spring-security-oauth2-resource-server
  - spring-security-oauth2-jose
</details>
<br>
<details>
  <summary>How to try:</summary>
  <br>


To try this project, you'll need to have [Docker](https://www.docker.com/get-started/) and [Git](https://git-scm.com/book/en/v2/Getting-Started-Installing-Git) installed. Follow the instructions below:

  1. Clone this repository:
      ```
      git clone https://github.com/ZakharKP/learning_foxminded_spring_taskset4.git
      ```

  2. Navigate to the directory with the project:
  
      ```
      cd <path to project dir>/task4_rest_service
      ```
      (probably)
      ```
      cd learning_foxminded_spring_taskset4/task4_rest_service
      ```

  3. Run Docker Compose:
      ```
      docker-compose up
      ```
      (or)
      ```
      sudo docker-compose up
      ```

  4. After the app is running, you can use your browser to access the documentation at [Swagger UI: http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html).

  5. You can also try sending requests to `localhost:8080` using tools like [Insomnia](https://insomnia.rest/) or [Postman](https://www.postman.com/) or any other REST API client.

<details><summary>Settings for autorised requests(tested with insomnia and postman):</summary>

- Grant type: Client Credentals
- Access Token URL: https://dev-svkj8psj3uogwg2e.eu.auth0.com/oauth/token
- Client ID: BXupNE2xrxZutuNCGAaPB3qX6UfX0XX7
- Client Secret: 1M1A8M0hRROxS_JU6HWNmZdrxg_63OYoJlGp4pmgyKjyKad2npU6WA5RRjg6LTml
- Audience: https://quickstarts/api
</details>
