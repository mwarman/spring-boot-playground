# Spring Boot Playground

This project is an example Spring Boot RESTful web service component.

## Getting Started

### Dependencies

- Java 17

## Installing

Clone the project [repository](https://github.com/mwarman/spring-boot-playground). Open the project in your favorite code editor such as VS Code or Spring Tool Suite.

## Running

To interact with the application, run any of the following commands from a terminal prompt in the project base directory.

### `bootRun`

Runs this project as a Spring Boot application.

```
./gradlew bootRun
```

The application is available available at base URL: http://localhost:8080. To test a specific endpoint, for example, fetching a single Todo, use the URL: http://localhost:8080/api/todos/1

### `bootJar`

Assembles an executable jar archive containing the main classes and their dependencies.

```
./gradlew bootJar
```

The command produces build artifacts in the `./build` directory. The JAR file is created in `./build/libs`. For example, `todo-ws-0.0.1-SNAPSHOT.jar`.

You can run the executable JAR file with (supply the name of your JAR file):

```
java -jar build/libs/todo-ws-0.0.1-SNAPSHOT.jar
```

### `clean`

Deletes the build directory.

```
./gradlew clean
```

### `build`

Assembles and tests this project.

```
./gradlew build
```

### `test`

Runs the test suite.

```
./gradlew test
```

### `check`

Runs all checks.

```
./gradlew check
```

### `tasks`

Displays the Gradle tasks.

```
./gradlew tasks
```

## API Documentation

API documentation for this application is generated with [SpringDoc][springdoc]. Documentation is created during the build and packaged with the application. It is accessible when running the application locally with `bootRun` or when running the executable JAR created with `bootJar`.

The Swagger UI is available at `[baseUrl]/api-docs/swagger-ui.html` and the OpenAPI 3.x specification is available at `[baseUrl]/api-docs`.

For example, try http://localhost:8080/api-docs/swagger-ui.html or http://localhost:8080/api-docs

## Reference Documentation

For further reference, please consider the following sections:

- [Official Spring Boot Reference Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/index.html)
- [Official Gradle documentation](https://docs.gradle.org)
- [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.1.4/gradle-plugin/reference/html/)
- [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.1.4/gradle-plugin/reference/html/#build-image)
- [Spring Projects](https://spring.io/projects)
- [SpringDoc Reference Documentation][springdoc]

### Additional Links

These additional references should also help you:

- [Gradle Build Scans – insights for your project's build](https://scans.gradle.com#gradle)

[springdoc]: https://springdoc.org/ 'SpringDoc'
