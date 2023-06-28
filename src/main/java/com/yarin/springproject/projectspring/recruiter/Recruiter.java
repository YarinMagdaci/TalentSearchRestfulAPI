package com.yarin.springproject.projectspring.recruiter;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yarin.springproject.projectspring.company.Company;
import com.yarin.springproject.projectspring.job.Job;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.Email;

import java.util.*;

/**
 * The Recruiter class represents a recruiter entity in the system. It is used to store information about recruiters,
 * such as their name, email, and the jobs they are associated with.
 * This class is annotated with various annotations for ORM (Object-Relational Mapping) and Lombok to simplify common
 * boilerplate code. The @Entity annotation specifies that instances of this class should be persisted in a database,
 * and a table will be generated for this class in the database. The @Data annotation is provided by Lombok and automatically
 * generates boilerplate code for getters, setters, toString, equals, and hashCode methods. The @NoArgsConstructor annotation
 * generates a no-args constructor using Lombok.
 */
@Data
@Entity
@NoArgsConstructor
public class Recruiter {

    /**
     * The primary key of the recruiter table is represented by the 'id' field.
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * The name of the recruiter.
     */
    private String name;

    /**
     * The email address of the recruiter.
     */
    @Email(message = "Invalid email address")
    private String email;

    /**
     * The list of jobs associated with the recruiter.
     *
     * @JsonIgnore annotation is used to indicate that this field should be ignored when serializing the Recruiter object
     * into JSON format.
     */
    @JsonIgnore
    @OneToMany(mappedBy = "recruiter", cascade = CascadeType.REMOVE)
    private List<Job> jobs = new ArrayList<>();

    /**
     * The set of companies associated with the recruiter.
     *
     * @JsonIgnore annotation is used to indicate that this field should be ignored when serializing the Recruiter object
     * into JSON format.
     */
    @JsonIgnore
    @ManyToMany(mappedBy = "recruiters")
    private Set<Company> companies = new HashSet<>();

    /**
     * Constructs a Recruiter object with the specified name and email.
     *
     * @param name  the name of the recruiter
     * @param email the email address of the recruiter
     */
    public Recruiter(String name, String email) {
        this.name = name;
        this.email = email;
    }

    /**
     * Adds a company to the set of companies associated with the recruiter.
     * Also updates the reciprocal relationship on the company side.
     *
     * @param company the company to add
     */
    public void addCompany(Company company) {
        this.companies.add(company);
        company.getRecruiters().add(this);
    }

    /**
     * Removes a company from the set of companies associated with the recruiter.
     * Also updates the reciprocal relationship on the company side.
     *
     * @param company the company to remove
     */
    public void removeCompany(Company company) {
        this.companies.remove(company);
        company.getRecruiters().remove(this);
    }

    /**
     * Generates a hash code for the Recruiter object based on its id, name, and email.
     *
     * @return the hash code value
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, name, email);
    }
}

