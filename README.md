# Spring Boot Playground

This project is an example Spring Boot RESTful web service application component.

## License

This project is [MIT licensed](./LICENSE).

## Getting Started

### Dependencies

- Java 17

## Installing

Clone the project [repository](https://github.com/mwarman/spring-boot-playground). Open the project in your favorite code editor such as VS Code or Spring Tool Suite.

## Running

> **NOTE:** As this project is for demonstration purposes only, randomly generated passwords for the `user` and `admin` are printed to the log at application startup. Access the API endpoints using Basic Auth with these credentials.

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

## Using Docker

### Building the Image

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

### Starting a Container

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
  --publish 8080:8080 \
  --env SPRING_PROFILES_ACTIVE=ci \
  --name svc-todos \
  --rm
  mwarman/sb-playground:latest
```

### Docker Compose

The Docker Compose configuration is defined in the file `docker-compose.yml`. Data is persisted on Docker volumes between restarts.

The stack contains the following services:

- The Spring Boot Application
- MySQL 5.7
- Adminer (formerly PhpMyAdmin)

> **NOTE:** The Spring Boot application is accessible on port `8080` and Adminer on port `8081`.

To start the docker compose stack, issue the following command:

```
# optionally use the --build option the first time to create the Docker image
docker compose up --build [--detach]

# on subsequent starts, omit the --build option
docker compose up [--detach]
```

The stack logs are printed directly to the console, unless started with `--detach`.

Press <kbd>Ctrl</kbd>+<kbd>C</kbd> or <kbd>Cmd</kbd>+<kbd>C</kbd> to stop the stack.

> **NOTE:** If started with `--detach` use `docker compose down` to stop the stack.

To clean up all Docker compose stack resources, **including** volumes, run the following command even if the stack is stopped:

```
docker compose down -v
```

This is useful to reset the database back to an empty state.

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
