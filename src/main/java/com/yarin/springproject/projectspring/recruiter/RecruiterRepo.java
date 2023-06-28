package com.yarin.springproject.projectspring.recruiter;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * The `RecruiterRepo` interface extends the `JpaRepository` and provides database access and manipulation methods
 * for the `Recruiter` entity.

 * This interface defines standard CRUD (Create, Read, Update, Delete) operations for the `Recruiter` entity,
 * allowing retrieval and modification of `Recruiter` records in the underlying database.

 * Note: This interface is extended from `JpaRepository<Recruiter, Long>`, where `Recruiter` is the entity type
 * and `Long` is the type of the primary key for the `Recruiter` table.
 */
public interface RecruiterRepo extends JpaRepository<Recruiter, Long> {

    /**
     * Retrieves a `Recruiter` entity by the specified email.

     * This method queries the database for a `Recruiter` record matching the provided email.
     *
     * @param email the email address of the `Recruiter` to retrieve
     * @return an optional containing the retrieved `Recruiter` entity, or an empty optional if not found
     */
    Optional<Recruiter> findByEmail(String email);

    /**
     * Retrieves a `Recruiter` entity by the specified name.

     * This method queries the database for a `Recruiter` record matching the provided name.
     *
     * @param name the name of the `Recruiter` to retrieve
     * @return an optional containing the retrieved `Recruiter` entity, or an empty optional if not found
     */
    Optional<Recruiter> findByName(String name);

    /**
     * Retrieves a list of `Recruiter` entities by searching for the specified name in the associated companies.

     * This method queries the database for `Recruiter` records where the name of the associated company contains
     * the specified name.
     *
     * @param name the name to search for in the associated companies
     * @return a list of `Recruiter` entities matching the search criteria
     */
    List<Recruiter> findByCompaniesNameContaining(String name);
}

