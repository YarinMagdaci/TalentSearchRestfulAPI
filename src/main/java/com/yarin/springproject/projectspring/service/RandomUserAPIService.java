package com.yarin.springproject.projectspring.service;

import com.yarin.springproject.projectspring.randomuser.RandomUser;
import com.yarin.springproject.projectspring.randomuser.RandomUserResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;
/**
 * The RandomUserAPIService class is responsible for interacting with the RandomUser API
 * to retrieve random user data.

 * <p>
 * This service is used by the RecruiterController, which is a RESTful API controller.
 * When the RecruiterController receives a specific POST request to add a random user,
 * it calls the getRecruiters() method of RandomUserAPIService. This method sends a request
 * to the RandomUser API and retrieves a random user. The response is then converted to the
 * desired type, Recruiter, using the 'middle-class-converter'.
 * </p>

 * <p>
 * This class is annotated with @Service to indicate that it is a service component in the application.
 * </p>
 */
@Service
public class RandomUserAPIService {
    private final RestTemplate restTemplate; // main class that help us to call Rest API
    private static final Logger serviceLogger = LoggerFactory.getLogger(RandomUserAPIService.class);
    /**
     * Constructs a new RandomUserAPIService with the provided RestTemplateBuilder.
     *
     * @param templateBuilder the RestTemplateBuilder used to build the RestTemplate instance
     */
    public RandomUserAPIService(RestTemplateBuilder templateBuilder){
        this.restTemplate = templateBuilder.build();
    }
    /**
     * Retrieves a random user from the RandomUser API.
     * This method is asynchronous and returns a CompletableFuture.
     *
     * @return a CompletableFuture that will be completed with the random user
     */
    @Async
    public CompletableFuture<RandomUser> getRecruiters() {
        System.out.println("I am at method getRecruiters: ");
        String apiUrl = "https://randomuser.me/api";

        RandomUserResponse response = this.restTemplate.getForObject(apiUrl, RandomUserResponse.class);
        RandomUser randomUser = response.getResults().get(0); // Assuming you only want the first user
        System.out.println("Retrieved data from the API.");
        serviceLogger.info("Running in thread = " + Thread.currentThread().getName());
        System.out.println("About to return this data.");

        return CompletableFuture.completedFuture(randomUser);
    }
}
