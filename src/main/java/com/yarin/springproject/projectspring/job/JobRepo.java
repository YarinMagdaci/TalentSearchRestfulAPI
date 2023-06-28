package com.yarin.springproject.projectspring.job;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing Job entities in the database.
 * Extends the JpaRepository interface, providing CRUD operations and additional querying capabilities.
 * Job is the entity class being stored in the database, and Long is the type of the entity's primary key.
 */
public interface JobRepo extends JpaRepository<Job, Long> {

    /**
     * Retrieves a list of jobs by searching for a specific title.
     *
     * @param title the title to search for
     * @return a list of jobs matching the given title
     */
    List<Job> findByTitleContaining(String title);

    /**
     * Retrieves a list of jobs by searching for a specific recruiter's name.
     *
     * @param name the name of the recruiter to search for
     * @return a list of jobs associated with recruiters matching the given name
     */
    List<Job> findByRecruiterNameContaining(String name);

    /**
     * Retrieves a list of jobs by searching for a specific company name.
     *
     * @param name the name of the company to search for
     * @return a list of jobs associated with companies matching the given name
     */
    List<Job> findByCompanyNameContaining(String name);
}
