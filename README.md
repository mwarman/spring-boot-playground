# Spring Boot Playground

## Getting Started

## Installing

## Running

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
