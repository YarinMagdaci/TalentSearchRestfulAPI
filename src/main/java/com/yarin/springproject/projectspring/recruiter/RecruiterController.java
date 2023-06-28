package com.yarin.springproject.projectspring.recruiter;

import com.yarin.springproject.projectspring.company.Company;
import com.yarin.springproject.projectspring.exception.RecruiterNotFoundException;
import com.yarin.springproject.projectspring.randomuser.RandomUser;
import com.yarin.springproject.projectspring.service.RandomUserAPIService;
import com.yarin.springproject.projectspring.service.TalentService;
import jakarta.validation.Valid;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import java.util.concurrent.CompletableFuture;

/**
 * The RecruiterController class handles incoming HTTP requests related to recruiters.
 * It provides endpoints for retrieving, creating, updating, and deleting recruiters.

 * This class is annotated with the @RestController annotation, which indicates that it is a specialized version
 * of the @Controller component used for RESTful APIs. It handles incoming requests and returns the response
 * in JSON format.
 */
@RestController
public class RecruiterController {

    private final TalentService talentService;
    private final RecruiterEntityAssembler recruiterEntityAssembler;
    private final RecruiterDtoAssembler recruiterDtoAssembler;

    /**
     * Constructs a new RecruiterController with the given dependencies.
     *
     * @param talentService              the TalentService used for managing recruiter-related operations
     * @param recruiterEntityAssembler   the RecruiterEntityAssembler used for converting Recruiter entities to DTOs
     * @param recruiterDtoAssembler      the RecruiterDtoAssembler used for converting Recruiter DTOs to entities
     */
    public RecruiterController(TalentService talentService, RecruiterEntityAssembler recruiterEntityAssembler,
                               RecruiterDtoAssembler recruiterDtoAssembler) {
        this.talentService = talentService;
        this.recruiterEntityAssembler = recruiterEntityAssembler;
        this.recruiterDtoAssembler = recruiterDtoAssembler;
    }
    /**
     * Retrieves all recruiters and returns them as a collection of EntityModel objects.
     * This method handles HTTP GET requests to the "/recruiters" endpoint. It fetches all recruiters from the
     * TalentService and converts them into EntityModel objects using the RecruiterEntityAssembler. The recruiters
     * are then wrapped in a CollectionModel and returned in the response body with a 200 OK status code.
     *
     * @return a ResponseEntity containing a CollectionModel of EntityModel objects representing the recruiters,
     *         or an empty collection if no recruiters are found
     */
    @GetMapping("/recruiters") // getting response 200 altogether with all recruiters each together with link to itself and all.
    public ResponseEntity<CollectionModel<EntityModel<Recruiter>>> allRecruiters(){
        return ResponseEntity.ok(recruiterEntityAssembler.toCollectionModel(talentService.getRecruiterRepo().findAll()));
    }

    /**
     * Retrieves information about all recruiters and returns them as a collection of EntityModel objects representing
     * RecruiterDTOs.
     * This method handles HTTP GET requests to the "/recruiters/info" endpoint. It fetches all recruiters from the
     * TalentService, converts them into RecruiterDTO objects, and wraps them in EntityModel objects using the
     * RecruiterDtoAssembler. The recruiter information is then returned in the response body as a CollectionModel of
     * EntityModel objects with a 200 OK status code.
     *
     * @return a ResponseEntity containing a CollectionModel of EntityModel objects representing the RecruiterDTOs,
     *         or an empty collection if no recruiters are found
     */
    @GetMapping("/recruiters/info") // same as /recruiters but it's exposing recruiterDTOs and not Recruiters, you can see the difference
    public ResponseEntity<CollectionModel<EntityModel<RecruiterDTO>>> allRecruiterInfo(){
        return ResponseEntity.ok(recruiterDtoAssembler.toCollectionModel(
                StreamSupport.stream(talentService.getRecruiterRepo().findAll().spliterator(), false)
                        .map(RecruiterDTO::new).collect(Collectors.toList())));
    }

    /**
     * Retrieves information about a specific recruiter based on the provided ID and returns it as an EntityModel
     * representing a RecruiterDTO.
     * This method handles HTTP GET requests to the "/recruiters/{id}/info" endpoint, where {id} is the ID of the
     * recruiter to retrieve. The method fetches the recruiter from the TalentService using the provided ID, converts
     * it into a RecruiterDTO object, and wraps it in an EntityModel using the RecruiterDtoAssembler. The recruiter
     * information is then returned in the response body as an EntityModel with a 200 OK status code.
     *
     * @param id the ID of the recruiter to retrieve
     * @return a ResponseEntity containing an EntityModel representing the RecruiterDTO, or a 404 Not Found response
     *         if no recruiter is found with the provided ID
     * @throws RecruiterNotFoundException if no recruiter is found with the provided ID
     */
    @GetMapping("/recruiters/{id}/info") // same as above just for specific recruiter
    public ResponseEntity<EntityModel<RecruiterDTO>> singleRecruiterInfo(@PathVariable long id) {
        return talentService.getRecruiterRepo().findById(id)
                .map(RecruiterDTO::new)
                .map(recruiterDtoAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new RecruiterNotFoundException("Recruiter not found with id: " + id)); // creating an formatted error message nicer than we had
    }


    /**
     * Retrieves a collection of recruiters based on the provided company name and returns them as EntityModels
     * representing RecruiterDTOs.
     * This method handles HTTP GET requests to the "/recruiters/bycompany/{name}" endpoint, where {name} is the
     * name of the company to retrieve recruiters for. The method fetches the recruiters from the TalentService using
     * the provided company name and converts them into RecruiterDTO objects. The list of RecruiterDTOs is then wrapped
     * in a CollectionModel of EntityModels using the RecruiterDtoAssembler. The collection of recruiters is returned
     * in the response body as a ResponseEntity with a 200 OK status code.
     *
     * @param name the name of the company to retrieve recruiters for
     * @return a ResponseEntity containing a CollectionModel of EntityModels representing the RecruiterDTOs
     */
    @GetMapping("/recruiters/bycompany/{name}")
    public ResponseEntity<CollectionModel<EntityModel<RecruiterDTO>>> recruitersByCompany(@PathVariable String name) {
        List<Recruiter> recruiters = talentService.getRecruiterRepo().findByCompaniesNameContaining(name);
        List<RecruiterDTO> recruiterDTOs = recruiters.stream() // creates DTOs of the list we retrieved from the repo
                .map(RecruiterDTO::new)
                .collect(Collectors.toList());

        // wrap it all in Collection Model
        CollectionModel<EntityModel<RecruiterDTO>> collectionModel = recruiterDtoAssembler.toCollectionModel(recruiterDTOs);
        return ResponseEntity.ok(collectionModel); // returns the wrapped CollectionModel inside ResponseEntity.
    }

    /**
     * Creates a new recruiter with the provided details.
     * This method handles HTTP POST requests to the "/recruiters" endpoint. It expects a JSON payload in the request body
     * containing the details of the recruiter to be created. The payload should include the recruiter's name and email.
     * The recruiter object is validated using the @Valid annotation to ensure that the provided data meets the defined
     * constraints. If a recruiter with the same email already exists, a conflict response is returned with an error message.
     * Otherwise, the recruiter is saved using the TalentService, and a URI for the newly created resource is built.
     * The method returns a ResponseEntity with the appropriate HTTP status code, including the location URI in the response
     * header when the recruiter is successfully created.
     * Example request body:
     * {
     *   "name": "John Doe",
     *   "email": "johnDoe@example.com"
     * }
     *
     * @param recruiter the recruiter object to be created, provided in the request body
     * @return a ResponseEntity with an appropriate HTTP status code and location URI for the created resource, or a conflict
     * response if a recruiter with the same email already exists
     */

    @PostMapping("/recruiters")
    public ResponseEntity<?> createRecruiter(@Valid @RequestBody Recruiter recruiter) {
        if (talentService.getRecruiterRepo().findByEmail(recruiter.getEmail()).isPresent()) {
            String errorMessage = "Recruiter with email " + recruiter.getEmail() + " already exists.";
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorMessage);
        }

        // Save the recruiter
        Recruiter savedRecruiter = talentService.getRecruiterRepo().save(recruiter);

        // Build the URI for the newly created Recruiter resource
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedRecruiter.getId())
                .toUri();

        // Return a ResponseEntity with the created Recruiter resource and an appropriate HTTP status
        return ResponseEntity.created(location).build();
    }
    /**
     * Creates a new recruiter with random name and email obtained from an external API.
     * This method handles HTTP POST requests to the "/recruiters/randomUser" endpoint. It calls an external API,
     * RandomUserAPIService, to generate a random name and email for the recruiter. The generated name and email are used
     * to create a new Recruiter object. If a recruiter with the same email already exists, a conflict response is returned
     * with an error message. Otherwise, the recruiter is saved using the TalentService, and a URI for the newly created
     * resource is built. The method returns a ResponseEntity with the appropriate HTTP status code, including the location
     * URI in the response header when the recruiter is successfully created.
     * Example request body: (empty)
     * @return a ResponseEntity with an appropriate HTTP status code and location URI for the created resource, or a conflict
     * response if a recruiter with the same email already exists
     * @throws ExecutionException   if an execution error occurs while retrieving data from the external API
     * @throws InterruptedException if the current thread is interrupted while waiting for the external API response
     */
  @PostMapping("/recruiters/randomUser")
  public ResponseEntity<?> createRandomRecruiter() throws ExecutionException, InterruptedException {
      RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();

      // Call the method on the instantiated RandomUserAPIService object

      CompletableFuture<RandomUser> completableFutureRecruiter = null;
      RandomUserAPIService randomUserAPIService = new RandomUserAPIService(restTemplateBuilder);
      completableFutureRecruiter = randomUserAPIService.getRecruiters();

      completableFutureRecruiter.join();

      StringBuilder sbName = new StringBuilder();
      sbName.append(completableFutureRecruiter.get().getName().getFirst());
      sbName.append(" ");
      sbName.append(completableFutureRecruiter.get().getName().getLast());
      String recName = sbName.toString();
      String recEmail = completableFutureRecruiter.get().getEmail();
      Recruiter recruiter = new Recruiter(recName , recEmail);

      sbName = null; // let the gc collect it maybe earlier, no more usage.

      System.out.println(recruiter);

      if (talentService.getRecruiterRepo().findByEmail(recruiter.getEmail()).isPresent()) {
          String errorMessage = "Recruiter with email " + recruiter.getEmail() + " already exists.";
          return ResponseEntity.status(HttpStatus.CONFLICT).body(errorMessage);
      }

      // Save the recruiter
      Recruiter savedRecruiter = talentService.getRecruiterRepo().save(recruiter);

      // Build the URI for the newly created Recruiter resource
      URI location = ServletUriComponentsBuilder.fromCurrentRequest()
              .replacePath("/recruiters/{id}/info")
              .buildAndExpand(savedRecruiter.getId())
              .toUri();

      // Return a ResponseEntity with the created Recruiter resource and an appropriate HTTP status
      return ResponseEntity.created(location).build();
  }

    /**
     * Deletes a recruiter by their ID.
     *
     * This method handles HTTP DELETE requests to the "/recruiters/{id}" endpoint. It deletes the recruiter with the
     * specified ID and all jobs associated with that recruiter. If the recruiter does not exist, a RecruiterNotFoundException
     * is thrown. The method removes the recruiter from the associated companies and deletes the recruiter from the repository.
     * It returns a ResponseEntity with no content and an appropriate HTTP status code.
     *
     * Example usage: DELETE /recruiters/1
     * This deletes recruiter with ID 1 and all jobs associated with this recruiter.
     *
     * @param id the ID of the recruiter to be deleted
     * @return a ResponseEntity with no content and an appropriate HTTP status code
     * @throws RecruiterNotFoundException if the recruiter with the specified ID does not exist
     */
  @DeleteMapping("/recruiters/{id}")
  public ResponseEntity<Void> deleteRecruiter(@PathVariable Long id) {
      // Check if the recruiter exists
      Optional<Recruiter> recruiterOptional = talentService.getRecruiterRepo().findById(id);
      if (recruiterOptional.isEmpty()) {
          throw new RecruiterNotFoundException("Recruiter not found with ID: " + id);
      }

      Recruiter recruiter = recruiterOptional.get();

      // Remove the recruiter from associated companies
      for (Company company : recruiter.getCompanies()) {
          company.getRecruiters().remove(recruiter);
      }

      // Delete the recruiter from the repository
      talentService.getRecruiterRepo().deleteById(id);

      // Return a ResponseEntity with no content and an appropriate HTTP status
      return ResponseEntity.noContent().build();
  }

    /**
     * Updates a recruiter with the specified ID.
     *
     * This method handles HTTP PUT requests to the "/recruiters/{id}" endpoint. It retrieves the recruiter with the specified
     * ID from the repository and updates its fields based on the provided request body. The request body should be a JSON object
     * containing the fields "name" and "email" to update the corresponding fields of the recruiter. If the recruiter does not exist,
     * a RecruiterNotFoundException is thrown. The updated recruiter is saved back to the repository, and the method returns a
     * ResponseEntity with the updated recruiter and an appropriate HTTP status code.
     *
     * Example usage: PUT /recruiters/1
     * Request body: {
     *   "name": "Mario Gomez",
     *   "email": "MarioGomez@walla.com"
     * }
     * This updates the recruiter with ID 1, setting the name to "Mario Gomez" and email to "MarioGomez@walla.com".
     *
     * @param id      the ID of the recruiter to be updated
     * @param request a Map representing the request body containing the fields to be updated
     * @return a ResponseEntity with the updated recruiter and an appropriate HTTP status code
     * @throws RecruiterNotFoundException if the recruiter with the specified ID does not exist
     */
    @PutMapping("/recruiters/{id}")
    public ResponseEntity<EntityModel<RecruiterDTO>> updateRecruiter(@PathVariable Long id,  @RequestBody Map<String, Object> request) {
        // Retrieve the Job by id from the JobRepository
        Optional<Recruiter> optionalRecruiter = talentService.getRecruiterRepo().findById(id);
        if (optionalRecruiter.isEmpty()) {
            throw new RecruiterNotFoundException("id " + id);
        }
        Recruiter recruiter = optionalRecruiter.get();

        // Iterate through the fields in the request body
        for (Map.Entry<String, Object> entry : request.entrySet()) {
            String field = entry.getKey();
            Object value = entry.getValue();

            // Update the corresponding field of the retrieved Job object
            switch (field) {
                case "name" -> {
                    if (value instanceof String) {
                        recruiter.setName((String) value);
                    }
                }
                case "email" -> {
                    if (value instanceof String) {
                        recruiter.setEmail((String) value);
                    }
                }
                default -> {
                }
                // Ignore unknown fields or handle them as needed
            }
        }

        // Save the updated Job to the JobRepository
        talentService.getRecruiterRepo().save(recruiter);

        // Return a ResponseEntity with the updated Job and an appropriate HTTP status
        return ResponseEntity.ok(recruiterDtoAssembler.toModel(new RecruiterDTO(recruiter)));
    }


}
