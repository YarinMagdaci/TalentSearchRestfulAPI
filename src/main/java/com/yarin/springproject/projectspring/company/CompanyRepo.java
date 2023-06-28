package com.yarin.springproject.projectspring.company;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for managing Company entities.
 * Extends the JpaRepository interface for CRUD operations on Company objects.
 * The CompanyRepo interface handles the persistence and retrieval of Company entities in the database.
 * Company is the class representing the table, and Long is the type of the primary key of the Company table.
 */
public interface CompanyRepo extends JpaRepository<Company, Long> {

    /**
     * Checks if a company with the specified name exists in the database.
     *
     * @param name the name of the company to check
     * @return true if a company with the specified name exists, false otherwise
     */
    boolean existsByName(String name);

    /**
     * Retrieves a company by its name.
     *
     * @param name the name of the company to retrieve
     * @return an Optional containing the company with the specified name, or an empty Optional if not found
     */
    Optional<Company> findByName(String name);
}
