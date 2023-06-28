package com.yarin.springproject.projectspring.job;

import com.yarin.springproject.projectspring.company.Company;
import com.yarin.springproject.projectspring.recruiter.Recruiter;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a job entity that is stored in the database.
 * The class is annotated with various annotations for code generation and validation purposes.
 * It defines the structure and attributes of a job.
 */
@Data
@Entity
@NoArgsConstructor
public class Job {
    /**
     * The ID of the job.
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * The title of the job.
     * Must have a minimum length of 2 characters.
     */
    @Size(min = 2, message = "Title should have at least 2 characters")
    private String title;

    /**
     * The salary of the job.
     * Must be in the format 'numK', where 'num' represents digits.
     */
    @Pattern(regexp = "\\d+K$", message = "Salary should be in the format 'numK'")
    private String salary;

    /**
     * The location of the job.
     */
    private String location;

    /**
     * The company associated with the job.
     */
    @ManyToOne
    private Company company;

    /**
     * The recruiter associated with the job.
     * Cascade type is set to REMOVE to delete the job if the recruiter is deleted.
     */
    @ManyToOne(cascade = CascadeType.REMOVE)
    private Recruiter recruiter;

    /**
     * Constructs a new Job with the provided title, salary, location, company, and recruiter.
     *
     * @param title     The title of the job.
     * @param salary    The salary of the job.
     * @param location  The location of the job.
     * @param company   The company associated with the job.
     * @param recruiter The recruiter associated with the job.
     */
    public Job(String title, String salary, String location, Company company, Recruiter recruiter) {
        this.title = title;
        this.salary = salary;
        this.location = location;
        this.company = company;
        this.recruiter = recruiter;
    }

    /**
     * Returns a string representation of the Job object.
     * The string includes the job's ID, title, salary, and the names of the associated company and recruiter.
     *
     * @return A string representation of the Job object.
     */
    @Override
    public String toString() {
        return "Job{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", salary='" + salary +
                ",belongs to company = " + company.getName() +
                ",belongs to recruiter = " + recruiter.getName() +
                '}';
    }
}
