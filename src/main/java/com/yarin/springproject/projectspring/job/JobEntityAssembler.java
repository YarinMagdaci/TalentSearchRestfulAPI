package com.yarin.springproject.projectspring.job;

import com.yarin.springproject.projectspring.SimpleIdentifiableRepresentationModelAssembler;
import org.springframework.stereotype.Component;

/**
 * A component that assembles links for the Job entity.
 * Extends the SimpleIdentifiableRepresentationModelAssembler class to build links based on a Spring web controller and a LinkRelationProvider.
 */
@Component
public class JobEntityAssembler extends SimpleIdentifiableRepresentationModelAssembler<Job> {

    /**
     * Constructs a new instance of JobEntityAssembler.
     * Initializes the assembler with the JobController class as the Spring MVC controller for building links.
     */
    public JobEntityAssembler() {
        super(JobController.class);
    }
}
