package com.yarin.springproject.projectspring.recruiter;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.yarin.springproject.projectspring.company.Company;
import com.yarin.springproject.projectspring.job.Job;
import lombok.Value;

import java.util.List;
import java.util.Set;
/**
 * A data transfer object (DTO) representing a simplified view of a Recruiter.
 *
 * This class is used as a wrapper or container to pass specific data of a Recruiter to the client. It provides an immutable
 * representation of a Recruiter's information by exposing selected fields while hiding others. It is annotated with @Value
 * to make all fields private and final, ensuring immutability.
 *
 * The fields exposed in the RecruiterDTO include the name, email, companies, and jobs associated with the Recruiter. The ID
 * field of the underlying Recruiter object is hidden and not exposed in the DTO.
 *
 * Example usage:
 * RecruiterDTO dto = new RecruiterDTO(recruiter);
 * String name = dto.getName(); // Retrieves the name of the recruiter from the DTO
 * Set<Company> companies = dto.getCompanies(); // Retrieves the set of companies associated with the recruiter from the DTO
 *
 * Note: This class serves as a wrapper around the Recruiter class, providing a controlled and simplified representation of
 * its data to the client. It is not intended for direct modification or persistence.
 */
@Value // Immutable variant of @Data which makes all private and final fields
@JsonPropertyOrder({"name", "email", "companies", "jobs"})
public class RecruiterDTO {
    @JsonIgnore // Ignore serialization/deserialization of the underlying Recruiter object
    Recruiter recruiter;

    /**
     * Retrieves the ID of the recruiter from the DTO.
     *
     * @return the ID of the recruiter
     */
    @JsonIgnore // Exclude the ID field when serializing the DTO
    public long getId() {
        return this.recruiter.getId();
    }

    /**
     * Retrieves the name of the recruiter from the DTO.
     *
     * @return the name of the recruiter
     */
    public String getName() {
        return this.recruiter.getName();
    }

    /**
     * Retrieves the email of the recruiter from the DTO.
     *
     * @return the email of the recruiter
     */
    public String getEmail() {
        return this.recruiter.getEmail();
    }

    /**
     * Retrieves the set of companies associated with the recruiter from the DTO.
     *
     * @return the set of companies associated with the recruiter
     */
    public Set<Company> getCompanies() {
        return this.recruiter.getCompanies();
    }

    /**
     * Retrieves the list of jobs associated with the recruiter from the DTO.
     *
     * @return the list of jobs associated with the recruiter
     */
    public List<Job> getJobs() {
        return this.recruiter.getJobs();
    }
}
