package com.yarin.springproject.projectspring.service;

import com.yarin.springproject.projectspring.company.Company;
import com.yarin.springproject.projectspring.company.CompanyRepo;
import com.yarin.springproject.projectspring.job.JobRepo;
import com.yarin.springproject.projectspring.recruiter.Recruiter;
import com.yarin.springproject.projectspring.recruiter.RecruiterRepo;
import org.springframework.stereotype.Service;
/**
 * The TalentService is a service class that provides functionality related to talent management.
 * It is used by the JobController and RecruiterController, both of which are RESTful API controllers.
 *
 * <p>
 * The TalentService interacts with the following repositories:
 * - JobRepo: An interface that extends JpaRepository for managing job entities.
 * - CompanyRepo: An interface that extends JpaRepository for managing company entities.
 * - RecruiterRepo: An interface that extends JpaRepository for managing recruiter entities.
 * </p>
 *
 * <p>
 * This service class is responsible for performing various operations related to talent management,
 * such as checking the existence of a company, checking the existence of a recruiter, saving a company,
 * and saving a recruiter.
 * </p>
 *
 * <p>
 * The TalentService class is annotated with @Service to indicate that it is a service component in the application.
 * </p>
 */
@Service
public class TalentService {
    private final JobRepo jobRepo;
    private final CompanyRepo companyRepo;
    private final RecruiterRepo recruiterRepo;
    /**
     * Constructs a new TalentService with the provided repositories.
     *
     * @param jobRepo the repository for managing job entities
     * @param companyRepo the repository for managing company entities
     * @param recruiterRepo the repository for managing recruiter entities
     */

    public TalentService(JobRepo jobRepo, CompanyRepo companyRepo, RecruiterRepo recruiterRepo) {
        this.jobRepo = jobRepo;
        this.companyRepo = companyRepo;
        this.recruiterRepo = recruiterRepo;
    }
    /**
     * Checks if the given company already exists in the repository.
     *
     * @param company the company to check for existence
     * @return true if the company exists, false otherwise
     */

    public boolean isThisCompanyExists(Company company) {
        return companyRepo.findByName(company.getName()).isPresent();
    }

    /**
     * Checks if a recruiter with the given email already exists in the repository.
     *
     * @param recruiter the recruiter to check for existence
     * @return true if the recruiter exists, false otherwise
     */

    public boolean existsByRecruiter(Recruiter recruiter) {
        return recruiterRepo.findByName(recruiter.getEmail()).isPresent();
    }

    /**
     * Saves the provided company in the repository.
     *
     * @param company the company to save
     * @return the saved company
     */

    public Company saveCompany(Company company) {
        return companyRepo.save(company);
    }

    /**
     * Saves the provided recruiter in the repository.
     *
     * @param recruiter the recruiter to save
     * @return the saved recruiter
     */

    public Recruiter saveRecruiter(Recruiter recruiter) {
        return recruiterRepo.save(recruiter);
    }

    /**
     * Returns the JobRepo instance associated with this TalentService.
     *
     * @return the JobRepo instance
     */

    public JobRepo getJobRepo(){
        return jobRepo;
    }

    /**
     * Returns the CompanyRepo instance associated with this TalentService.
     *
     * @return the CompanyRepo instance
     */

    public CompanyRepo getCompanyRepo() {
        return companyRepo;
    }

    /**
     * Returns the RecruiterRepo instance associated with this TalentService.
     *
     * @return the RecruiterRepo instance
     */

    public RecruiterRepo getRecruiterRepo() {
        return recruiterRepo;
    }
}
