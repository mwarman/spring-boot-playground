package net.leanstacks.todosvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@SpringBootApplication
@EnableTransactionManagement
public class TodoServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodoServiceApplication.class, args);
	}

	@Bean
	public OpenAPI openAPI() {
		return new OpenAPI()
				.info(new Info().title("Spring Boot Playground API")
						.description("A playground project for a Spring Boot RESTful API application component.")
						.termsOfService("https://leanstacks.net/terms.html")
						.license(
								new License().name("MIT License")
										.url("https://github.com/mwarman/spring-boot-playground/blob/main/LICENSE"))
						.contact(new Contact().name("LeanStacks").url("https://leanstacks.com/")));
	}

}
