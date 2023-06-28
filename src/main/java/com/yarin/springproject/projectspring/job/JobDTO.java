package com.yarin.springproject.projectspring.job;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.yarin.springproject.projectspring.company.Company;
import com.yarin.springproject.projectspring.recruiter.Recruiter;
import lombok.Value;


/**
 * Represents a Data Transfer Object (DTO) for a Job.
 * The class is annotated with various annotations to define its behavior during serialization/deserialization.
 * It provides a subset of properties from the Job class and is used for data transfer purposes.
 */
@Value
@JsonPropertyOrder({"title", "salary", "company", "recruiter", "location"})
public class JobDTO {
    /**
     * The Job object.
     * This field is ignored during serialization/deserialization.
     */
    @JsonIgnore
    Job job;

    /**
     * Constructs a new JobDTO with the provided Job object.
     *
     * @param job The Job object.
     */
    public JobDTO(Job job) {
        this.job = job;
    }

    /**
     * Returns the ID of the job.
     *
     * @return The ID of the job.
     */
    @JsonIgnore
    public Long getId() {
        return this.job.getId();
    }

    /**
     * Returns the title of the job.
     *
     * @return The title of the job.
     */
    public String getTitle() {
        return this.job.getTitle();
    }

    /**
     * Returns the salary of the job.
     *
     * @return The salary of the job.
     */
    public String getSalary() {
        return this.job.getSalary();
    }

    /**
     * Returns the company associated with the job.
     *
     * @return The company associated with the job.
     */
    public Company getCompany() {
        return this.job.getCompany();
    }

    /**
     * Returns the recruiter associated with the job.
     *
     * @return The recruiter associated with the job.
     */
    public Recruiter getRecruiter() {
        return this.job.getRecruiter();
    }

    /**
     * Returns the location of the job.
     *
     * @return The location of the job.
     */
    public String getLocation() {
        return this.job.getLocation();
    }
}
