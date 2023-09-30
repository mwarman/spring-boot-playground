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

Builds and runs the application.

```
./gradlew bootRun
```

The application is available available at base URL: http://localhost:8080. To test a specific endpoint, for example, fetching a single Todo, use the URL: http://localhost:8080/todos/1

### `build`

Builds the application and packages it as an executable JAR file.

```
./gradlew build
```

The command produces build artifacts in the `./build` directory. The JAR file is created in `./build/libs`. For example, `todo-ws-0.0.1-SNAPSHOT.jar`.

You can run the executable JAR file with (supply the name of your JAR file):

```
java -jar build/libs/todo-ws-0.0.1-SNAPSHOT.jar
```

### `clean`

Cleans the project build artifacts.

```
./gradlew clean
```

## Reference Documentation

For further reference, please consider the following sections:

- [Official Gradle documentation](https://docs.gradle.org)
- [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.1.4/gradle-plugin/reference/html/)
- [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.1.4/gradle-plugin/reference/html/#build-image)

### Additional Links

These additional references should also help you:

- [Gradle Build Scans – insights for your project's build](https://scans.gradle.com#gradle)
