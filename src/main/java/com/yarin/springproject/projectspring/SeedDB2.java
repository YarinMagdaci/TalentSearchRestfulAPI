package com.yarin.springproject.projectspring;

import com.yarin.springproject.projectspring.company.Company;
import com.yarin.springproject.projectspring.company.CompanyRepo;
import com.yarin.springproject.projectspring.job.Job;
import com.yarin.springproject.projectspring.job.JobRepo;
import com.yarin.springproject.projectspring.recruiter.Recruiter;
import com.yarin.springproject.projectspring.recruiter.RecruiterRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
/**
 * The SeedDB2 class is a configuration class that is responsible for seeding dummy data into the database when the application is run.
 *
 * <p>
 * This class is annotated with @Configuration, indicating that it is a configuration class for the application.
 * It is used to define one or more @Bean methods that will be managed by the Spring IoC container.
 * </p>
 *
 * <p>
 * The main purpose of this class is to initialize the database with dummy data. It implements CommandLineRunner,
 * which allows the initDatabase() method to be executed when the application context is loaded.
 * </p>
 *
 * <p>
 * The initDatabase() method retrieves instances of JobRepo, CompanyRepo, and RecruiterRepo from the application context
 * and uses them to save dummy data to the respective repositories. It creates instances of Company, Recruiter, and Job,
 * establishes relationships between them, and saves them to the repositories.
 * </p>
 *
 * <p>
 * The objects created and saved during initialization will be managed by Spring and can be used throughout the application.
 * </p>
 */
@Configuration
public class SeedDB2 {
    private static final Logger logger = LoggerFactory.getLogger(SeedDB2.class);

    /**
     * Initializes the database with dummy data.
     *
     * @param jobRepo the repository for managing job entities
     * @param companyRepo the repository for managing company entities
     * @param recruiterRepo the repository for managing recruiter entities
     * @return a CommandLineRunner that performs the database initialization
     */

    @Bean
    CommandLineRunner initDatabase(JobRepo jobRepo, CompanyRepo companyRepo, RecruiterRepo recruiterRepo){
        return args -> {
            Company facebook = new Company("Facebook");
            Company twitter = new Company("Twitter");
            companyRepo.saveAll(Arrays.asList(facebook, twitter));
            Recruiter barak = new Recruiter("Barak Itzhaki","barakItzhaki@gmail.com");
            Recruiter pogba = new Recruiter("Paul Pogba","paulPogba@hotmail.co.il");
            recruiterRepo.saveAll(Arrays.asList(barak, pogba));
            barak.addCompany(facebook); // will add Barak to facebook.recruiters in this method
            pogba.addCompany(twitter);

            // Save the updated objects after adding the relationships
            companyRepo.saveAll(Arrays.asList(facebook, twitter));
            recruiterRepo.saveAll(Arrays.asList(barak, pogba));

            jobRepo.save(new Job("Java Developer", "15K", "Tel-Aviv", facebook, barak));
            jobRepo.save(new Job("Java Developer", "20K", "Holon", twitter, pogba));
            jobRepo.save(new Job("CPP Developer", "12K", "Ness-Ziona", twitter, pogba));
            jobRepo.save(new Job("Front-end Developer", "25K", "Haifa", twitter, pogba));
            jobRepo.save(new Job("Devops", "10K", "Jerusalem", facebook, barak));
        };
    }
}