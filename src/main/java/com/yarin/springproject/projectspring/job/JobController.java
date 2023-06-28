package com.yarin.springproject.projectspring.job;

import com.yarin.springproject.projectspring.company.Company;
import com.yarin.springproject.projectspring.exception.JobNotFoundException;
import com.yarin.springproject.projectspring.recruiter.Recruiter;
import com.yarin.springproject.projectspring.service.TalentService;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import jakarta.validation.Valid;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * Represents one of the Restful API controllers, in this case, of Job.
 * @author Yarin Mag
 * @author yarin0600@gmail.com
 */
@RestController
public class JobController {
    private final TalentService talentService;
    private final JobDtoAssembler jobDtoAssembler;
    private final JobEntityAssembler jobEntityAssembler;

    /**
     * Creates an JobController with the specified service, and assemblers.
     * @param talentService
     * @param jobDtoAssembler
     * @param jobEntityAssembler
     */
    public JobController(TalentService talentService, JobDtoAssembler jobDtoAssembler, JobEntityAssembler jobEntityAssembler) {
        this.talentService = talentService;
        this.jobDtoAssembler = jobDtoAssembler;
        this.jobEntityAssembler = jobEntityAssembler;
    }


    /**
     * Retrieves all jobs from the job repository.
     *
     * @return ResponseEntity containing a CollectionModel of EntityModel of Job, representing all the jobs,
     *         along with links to each individual job and the collection of jobs.
     */
    @GetMapping("/jobs") // getting response 200 altogether with all jobs each together with link to itself and all.
    public ResponseEntity<CollectionModel<EntityModel<Job>>> allJobs(){
        return ResponseEntity.ok(jobEntityAssembler.toCollectionModel(talentService.getJobRepo().findAll()));
    }

    /**
     * Retrieves information about all jobs from the job repository unlike before each Job is wrapped a JobDTO.
     * It helps us reveal the fields we wanted the client to see / hide those we wanted the client not to see.
     *
     * @return ResponseEntity containing a CollectionModel of EntityModel of JobDTO, representing information about all the jobs,
     *         along with links to each individual job and the collection of jobs.
     */
    @GetMapping("/jobs/info")
    public ResponseEntity<CollectionModel<EntityModel<JobDTO>>> allJobsInfo(){
        return ResponseEntity.ok(jobDtoAssembler.toCollectionModel(
                StreamSupport.stream(talentService.getJobRepo().findAll().spliterator(), false)
                        .map(JobDTO::new).collect(Collectors.toList())));
    }
    /**
     * Retrieves information about a specific job from the job repository (same as above inside a container of JobDTO).
     *
     * @param id The ID of the job to retrieve information for.
     * @return ResponseEntity containing an EntityModel of JobDTO, representing information about the specified job,
     *         along with links to the job.
     * @throws JobNotFoundException if the specified job ID is not found in the repository.
     */
    @GetMapping("/jobs/{id}/info") // same as above just for specific job
    public ResponseEntity<EntityModel<JobDTO>> singleJobInfo(@PathVariable long id) {
        return talentService.getJobRepo().findById(id)
                .map(JobDTO::new)
                .map(jobDtoAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new JobNotFoundException("id " + id)); // creating an formatted error message nicer than we had
    }
    /**
     * Retrieves jobs by partial title from the job repository (inside a container of JobDTO).
     *
     * @param title The partial title used to search for jobs.
     * @return ResponseEntity containing a CollectionModel of EntityModel of JobDTO, representing jobs matching the partial title,
     *         along with links to each individual job and the collection of jobs.
     */
    @GetMapping("/jobs/{title}") // get a job by partial title, if none found, returns link to all
    public ResponseEntity<CollectionModel<EntityModel<JobDTO>>> jobByPartialTitle(@PathVariable String title) {
        return ResponseEntity.ok(jobDtoAssembler.toCollectionModel(
                StreamSupport.stream(talentService.getJobRepo().findByTitleContaining(title).spliterator(), false)
                        .map(JobDTO::new).collect(Collectors.toList())));
    }

    /**
     * Retrieves jobs by recruiter name from the job repository.
     *
     * @param name The name of the recruiter used to search for jobs.
     * @return ResponseEntity containing a CollectionModel of EntityModel of JobDTO, representing jobs associated with the specified recruiter,
     *         along with links to each individual job and the collection of jobs.
     */
    @GetMapping("/jobs/byrecruiter/{name}")
    public ResponseEntity<CollectionModel<EntityModel<JobDTO>>> jobsByRecruiter(@PathVariable String name){
        return ResponseEntity.ok(jobDtoAssembler.toCollectionModel(
                StreamSupport.stream(talentService.getJobRepo().findByRecruiterNameContaining(name).spliterator(), false)
                        .map(JobDTO::new).collect(Collectors.toList())));
    }
    /**
     * Retrieves jobs by company name from the job repository.
     *
     * @param name The name of the company used to search for jobs.
     * @return ResponseEntity containing a CollectionModel of EntityModel of JobDTO, representing jobs associated with the specified company,
     *         along with links to each individual job and the collection of jobs.
     */
    @GetMapping("/jobs/bycompany/{name}")
    public ResponseEntity<CollectionModel<EntityModel<JobDTO>>> jobsByCompany(@PathVariable String name){
        return ResponseEntity.ok(jobDtoAssembler.toCollectionModel(
                StreamSupport.stream(talentService.getJobRepo().findByCompanyNameContaining(name).spliterator(), false)
                        .map(JobDTO::new).collect(Collectors.toList())));
    }

    /**
    * Creates a new job based on the provided Job object.
    *
    * @param job The Job object containing the details of the job to be created.
    *            The expected JSON format of the request body:
    {
    "title": "BackEnd Developer",
    "salary": "28K",
    "company": {
    "name": "Facebook"
    },
    "recruiter": {
    "name": "Zlatan Ibraimovic",
    "email": "ZlatanIbraimovic@walla.com"
    },
    "location": "Tel-Aviv"
    }
    * @return ResponseEntity containing an EntityModel of JobDTO representing the newly created job,
    *         along with the HTTP status of the response.
    * @throws ConstraintViolationException if the input Job object fails the validation constraints defined in the Job class.
    *         The validation is performed using the @Valid annotation.
    *         calls handleMethodArgumentNotValid at CustomizedResponseEntityExceptionHandler package.
    * @throws DataIntegrityViolationException if there is a data integrity violation while saving the job entity.
    *         This could occur if the provided company or recruiter already exist in the database
    *         with conflicting details.
    *         The uniqueness of the company name and recruiter email is enforced in the database.
    *         calls handleMethodArgumentNotValid at CustomizedResponseEntityExceptionHandler package.
    */



    @PostMapping("/jobs")
    public ResponseEntity<EntityModel<JobDTO>> createJob(@Valid @RequestBody Job job) {
        // Use the company name from the job
        String companyName = job.getCompany().getName();

        // Check if the company already exists in the database
        Optional<Company> existingCompany = talentService.getCompanyRepo().findByName(companyName);

        Company company;
        if (existingCompany.isPresent()) {
            company = existingCompany.get();
        } else {
            // Create a new Company entity
            company = new Company(companyName);
            talentService.getCompanyRepo().save(company);
        }
        // Use the recruiter email from the job
        String recruiterEmail = job.getRecruiter().getEmail();

        // Check if the recruiter already exists in the database
        Optional<Recruiter> existingRecruiter = talentService.getRecruiterRepo().findByEmail(recruiterEmail);

        Recruiter recruiter;
        if (existingRecruiter.isPresent()) {
            recruiter = existingRecruiter.get();
        } else {
            // Create a new Recruiter entity
            recruiter = job.getRecruiter();
            talentService.getRecruiterRepo().save(recruiter);
        }
        // Associate the Recruiter with the Company
        recruiter.addCompany(company);

        // save after updating the list.
        talentService.saveCompany(company);
        talentService.saveRecruiter(recruiter);

        // Create a new Job entity with the provided data
        Job newJob = new Job(job.getTitle(), job.getSalary(), job.getLocation(), company, recruiter);

        // Save the Job entity using JobRepo
        Job savedJob = talentService.getJobRepo().save(newJob);

        // Build the URI for the newly created Job resource
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedJob.getId())
                .toUri();

        // Return a ResponseEntity with the created Job resource and an appropriate HTTP status
        return ResponseEntity.created(location).build();
    }

    /**
     * Deletes a job with the specified ID.
     *
     * @param id The ID of the job to be deleted.
     * @return ResponseEntity with no content, indicating the successful deletion of the job,
     *         along with an appropriate HTTP status.
     * @throws JobNotFoundException if no job is found with the provided ID in the job repository.
     */

    @DeleteMapping("/jobs/{id}")
    public ResponseEntity<Void> deleteJob(@PathVariable Long id) {
        // Check if the job exists
        if (!talentService.getJobRepo().existsById(id)) {
            throw new JobNotFoundException("Job not found with ID: " + id);
        }

        // Delete the job from the repository
        talentService.getJobRepo().deleteById(id);

        // Return a ResponseEntity with no content and an appropriate HTTP status
        return ResponseEntity.noContent().build();
    }
    /**
     * Updates a job with the specified ID based on the fields provided in the request body.
     *
     * @param id      The ID of the job to be updated.
     * @param request The request body containing the fields to be updated.
     *                The expected JSON format of the request body:
     *                {
     *                    "title": "DevOps Updated",
     *                    "salary": "25K",
     *                    "location": "Yokneam Elite"
     *                }
     * @return ResponseEntity containing an EntityModel of JobDTO representing the updated job,
     *         along with the HTTP status of the response.
     * @throws JobNotFoundException if no job is found with the provided ID in the job repository.
     */
    @PutMapping("/jobs/{id}")
    public ResponseEntity<EntityModel<JobDTO>> updateJob(@PathVariable Long id, @RequestBody Map<String, Object> request) {
        // Retrieve the Job by id from the JobRepository
        Optional<Job> optionalJob = talentService.getJobRepo().findById(id);
        if (optionalJob.isEmpty()) {
            throw new JobNotFoundException("id " + id);
        }
        Job job = optionalJob.get();

        // Iterate through the fields in the request body
        for (Map.Entry<String, Object> entry : request.entrySet()) {
            String field = entry.getKey();
            Object value = entry.getValue();

            // Update the corresponding field of the retrieved Job object
            switch (field) {
                case "title" -> {
                    if (value instanceof String) {
                        job.setTitle((String) value);
                    }
                }
                case "salary" -> {
                    if (value instanceof String) {
                        job.setSalary((String) value);
                    }
                }
                case "location" -> {
                    if (value instanceof String) {
                        job.setLocation((String) value);
                    }
                }
                // Add cases for other fields that can be updated

                default -> {
                }
                // Ignore unknown fields or handle them as needed
            }
        }

        // Save the updated Job to the JobRepository
        talentService.getJobRepo().save(job);

        // Return a ResponseEntity with the updated Job and an appropriate HTTP status
        return ResponseEntity.ok(jobDtoAssembler.toModel(new JobDTO(job)));
    }


}
