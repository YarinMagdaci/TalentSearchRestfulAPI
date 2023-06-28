package com.yarin.springproject.projectspring.company;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yarin.springproject.projectspring.job.Job;
import com.yarin.springproject.projectspring.recruiter.Recruiter;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.*;

/**
 * Represents a company entity.
 * The table "Company" is created in the database to persist company information.
 */
@Data //  A shortcut for @ToString, @EqualsAndHashCode, @Getter on all fields, @Setter on all non-final fields, and @RequiredArgsConstructor!
@Entity
@NoArgsConstructor
public class Company {

    /**
     * The primary key of the company table.
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * The name of the company.
     */
    private String name;

    /**
     * The list of jobs associated with the company.
     * This represents a one-to-many relationship between Company and Job entities.
     */
    @JsonIgnore
    @OneToMany(mappedBy = "company")
    private List<Job> jobs = new ArrayList<>();

    /**
     * The set of recruiters associated with the company.
     * This represents a many-to-many relationship between Company and Recruiter entities.
     */
    @JsonIgnore
    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "Company_Recruiter",
            joinColumns = { @JoinColumn(name = "company_id") },
            inverseJoinColumns = { @JoinColumn(name = "recruiter_id") }
    )
    private Set<Recruiter> recruiters = new HashSet<>();

    /**
     * Constructs a Company object with the given name.
     *
     * @param name the name of the company
     */
    public Company(String name) {
        this.name = name;
    }

    /**
     * Adds a recruiter to the company's set of recruiters.
     * Also updates the recruiter's set of companies.
     *
     * @param recruiter the recruiter to add
     */
    public void addRecruiter(Recruiter recruiter) {
        this.recruiters.add(recruiter);
        recruiter.getCompanies().add(this);
    }

    /**
     * Removes a recruiter from the company's set of recruiters.
     * Also updates the recruiter's set of companies.
     *
     * @param recruiter the recruiter to remove
     */
    public void removeRecruiter(Recruiter recruiter) {
        this.recruiters.remove(recruiter);
        recruiter.getCompanies().remove(this);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

}
