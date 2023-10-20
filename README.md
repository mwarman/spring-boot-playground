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

## Docker

### Build the Image

Build an image for this application from the `Dockerfile`. See the [Docker guide](https://docs.docker.com/engine/reference/commandline/build/) for additional details.

**Usage:**

```
docker build [--tag <name>:<version>] .
```

**Examples:**

```
docker build --tag mwarman/sb-playground:1.0 .

docker build --tag mwarman/sb-playground:latest .
```

### Start a Container

Create and run a **new** container from an image. See the [Docker guide](https://docs.docker.com/engine/reference/commandline/container_run/) for additional details.

**Usage:**

```
docker container run [OPTIONS] IMAGE
```

**Example: Run with Spring Profile**  
Runs the application on port `8080` with the `ci` profile activated.

```
docker container run \
  --publish 8080:8080 \
  --env SPRING_PROFILES_ACTIVE=ci \
  --name svc-todos \
  mwarman/sb-playground:latest
```

**Example: Run on alternate port**  
Maps container port `8080` to host port `8081`.

```
docker container run \
  --publish 8081:8080 \
  --env SPRING_PROFILES_ACTIVE=ci \
  --name svc-todos \
  mwarman/sb-playground:latest
```

**Example: Remove container after shutdown**  
Automatically removes the container when shut down.

```
docker container run \
  --publish 8081:8080 \
  --env SPRING_PROFILES_ACTIVE=ci \
  --name svc-todos \
  --rm
  mwarman/sb-playground:latest
```

## Reference Documentation

For further reference, please consider the following sections:

- [Official Spring Boot Reference Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/index.html)
- [Official Gradle documentation](https://docs.gradle.org)
- [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.1.4/gradle-plugin/reference/html/)
- [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.1.4/gradle-plugin/reference/html/#build-image)
- [Spring Projects](https://spring.io/projects)
- [Docker Reference Guides][docker-ref]

### Additional Links

These additional references should also help you:

- [Gradle Build Scans – insights for your project's build](https://scans.gradle.com#gradle)

[docker-ref]: https://docs.docker.com/reference/ 'Docker Reference Guides'
