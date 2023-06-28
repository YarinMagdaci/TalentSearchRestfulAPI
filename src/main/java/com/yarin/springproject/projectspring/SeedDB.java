/*
Implementation of SeedDB by Yarin
But it does not answer the requirements of the project demands.
Which demand usage of annotations: @Configuration & @Bean.
 */

package com.yarin.springproject.projectspring;

import com.yarin.springproject.projectspring.company.Company;
import com.yarin.springproject.projectspring.company.CompanyRepo;
import com.yarin.springproject.projectspring.job.Job;
import com.yarin.springproject.projectspring.job.JobRepo;
import com.yarin.springproject.projectspring.recruiter.Recruiter;
import com.yarin.springproject.projectspring.recruiter.RecruiterRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
// So in this class we are implementing the CommandLineRunner
// Because we want that as soon as we run the server, that moment
// what is written in the method 'run' here, runs
// basically it's just 'injection' of some dummy data into our database so we could test our methods and stuff.
//@Component
//public class SeedDB implements CommandLineRunner {
//    @Autowired
//    private JobRepo jobRepo;
//    @Autowired
//    private CompanyRepo companyRepo;
//    @Autowired
//    private RecruiterRepo recruiterRepo;
//
//    @Override
//    public void run(String... args) throws Exception {
//        Company facebook = new Company("Facebook");
//        Company twitter = new Company("Twitter");
//        companyRepo.saveAll(Arrays.asList(facebook, twitter));
//
//        Recruiter barak = new Recruiter("Barak Itzhaki","barakItzhaki@gmail.com");
//        Recruiter pogba = new Recruiter("Paul Pogba","paulPogba@hotmail.co.il");
//        recruiterRepo.saveAll(Arrays.asList(barak, pogba));
//        barak.addCompany(facebook); // will add Barak to facebook.recruiters in this method
//        pogba.addCompany(twitter);
//
//        // Save the updated objects after adding the relationships
//        companyRepo.saveAll(Arrays.asList(facebook, twitter));
//        recruiterRepo.saveAll(Arrays.asList(barak, pogba));
//
//        jobRepo.save(new Job("Java Developer", "15K", "Tel-Aviv", facebook, barak));
//        jobRepo.save(new Job("Java Developer", "20K", "Holon", twitter, pogba));
//        jobRepo.save(new Job("CPP Developer", "12K", "Ness-Ziona", twitter, pogba));
//        jobRepo.save(new Job("Front-end Developer", "25K", "Haifa", twitter, pogba));
//        jobRepo.save(new Job("Devops", "10K", "Jerusalem", facebook, barak));
//    }
//}


