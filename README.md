# Web service starter kit

### The bare essentials for starting web service development

### Ready to use

- build management (Maven)
- containerization (Docker)
- SpringBoot / embedded Tomcat
- data persistence (H2 / Hibernate)
- database migration (Flyway)
- API docs, Swagger UI

### Features

- domain-driven/feature based package structure
- Rest API with Basic Authentication
  - USER API:
    - find user
      ```
      curl -X GET localhost:8080/users/admin
      ```
    - create user
      ```
      curl -X POST localhost:8080/users \
        -d '{"name":"test", "rawPassword":"test"}'
      ```
    - update user (disable = patch the 'enabled' attribute)
      ```
      curl -X PATCH localhost:8080/users/test \
        -H 'Content-Type: application/json-patch+json' \
        -d '{"operation":"replace","path":"enabled","value":"false"}'
      ```
- global exception handler
- enabled health check for probing

### Build & run

```shell
./mvnw clean package
docker build . -t starter
docker run -d -p 8080:8080 starter
```

### API documentation

http://localhost:8080/swagger-ui.html
