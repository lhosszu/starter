# Web service starter

### The bare essentials for starting web service development

### Ready to use

- build management (Maven)
- containerization (Docker)
- SpringBoot / embedded Tomcat
- data persistence (H2 / Hibernate)
- database migration with Flyway

### Features

- simple domain-driven package structure
- Rest API with Basic Authentication
- global exception handler

### Build & run

```shell
./mvnw clean package
docker build . -t starter
docker run -d -p 8080:8080 starter
```

### API documentation

http://localhost:8080/swagger-ui.html
