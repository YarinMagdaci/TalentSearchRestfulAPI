package com.yarin.springproject.projectspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
// TODO:
// 1. Make a little representation of class diagram of the project, algorithms we have used and external libraries and flow of the server side.
// http://localhost:8080/swagger-ui.html - swagger ui link
// http://localhost:8080/v3/api-docs - open api in json more comfortable
// shows you every part of the API you have created and what are all the possible responses from the server to every request.


/**
 * The ProjectSpringApplication class is the entry point of the Spring Boot application.
 *
 * <p>
 * This class is annotated with @SpringBootApplication, which combines the @Configuration,
 * @EnableAutoConfiguration, and @ComponentScan annotations. It marks the class as the main
 * configuration class for the application, enabling auto-configuration and component scanning.
 * </p>
 *
 * <p>
 * The @EnableAsync annotation is used to enable asynchronous processing in the application.
 * It allows methods to be executed asynchronously by creating proxies for them and executing them
 * in a separate thread. This can improve the performance and responsiveness of the application.
 * </p>
 *
 * <p>
 * The main() method is the entry point of the application. It starts the Spring Boot application
 * by calling the SpringApplication.run() method with the ProjectSpringApplication class and the
 * command-line arguments. This method initializes the application context, performs auto-configuration,
 * and starts the embedded web server.
 * </p>
 */

@SpringBootApplication
@EnableAsync
public class ProjectSpringApplication {
	public static void main(String[] args) {
		SpringApplication.run(ProjectSpringApplication.class, args);
	}
}
