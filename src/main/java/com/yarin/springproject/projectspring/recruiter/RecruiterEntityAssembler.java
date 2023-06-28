package com.yarin.springproject.projectspring.recruiter;

import com.yarin.springproject.projectspring.SimpleIdentifiableRepresentationModelAssembler;
import org.springframework.stereotype.Component;

/**
 * The `RecruiterEntityAssembler` class is a specialized implementation of the `SimpleIdentifiableRepresentationModelAssembler`
 * used to assemble HATEOAS links for `Recruiter` entities.

 * This class extends the `SimpleIdentifiableRepresentationModelAssembler` and is responsible for adding self-relational
 * links and aggregate root links to the `Recruiter` resources. It uses a Spring MVC controller and a `LinkRelationProvider`
 * to build the links based on a certain strategy.

 * Example usage:
 * RecruiterEntityAssembler assembler = new RecruiterEntityAssembler();
 * EntityModel<Recruiter> resource = EntityModel.of(recruiter);
 * assembler.addLinks(resource); // Adds self-relational link and aggregate root link to the resource

 * Note: This class is a Spring `@Component` and can be autowired where needed to assemble and add links to `Recruiter` resources.
 */
@Component
public class RecruiterEntityAssembler extends SimpleIdentifiableRepresentationModelAssembler<Recruiter> {

    /**
     * Constructs a `RecruiterEntityAssembler` instance.
     * It initializes the assembler with the `RecruiterController` class as the Spring MVC controller.
     * The `RecruiterController` class will be used to base the links off of.
     */
    public RecruiterEntityAssembler() {
        super(RecruiterController.class);
    }
}