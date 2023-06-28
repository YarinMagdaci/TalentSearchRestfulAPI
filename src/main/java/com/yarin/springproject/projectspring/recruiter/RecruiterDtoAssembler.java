package com.yarin.springproject.projectspring.recruiter;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.SimpleRepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * The `RecruiterDtoAssembler` class is responsible for assembling HATEOAS links for `RecruiterDTO` resources.
 *
 * This class implements the `SimpleRepresentationModelAssembler` interface, which allows converting `RecruiterDTO` objects
 * into `EntityModel` representations and adding relevant HATEOAS links to the resources.
 *
 * Example usage:
 * RecruiterDtoAssembler assembler = new RecruiterDtoAssembler();
 * EntityModel<RecruiterDTO> resource = EntityModel.of(recruiterDto);
 * assembler.addLinks(resource); // Adds self-relational link to the resource
 *
 * Note: This class is a Spring `@Component` and can be autowired where needed to assemble and add links to `RecruiterDTO`
 * resources.
 */
@Component
public class RecruiterDtoAssembler implements SimpleRepresentationModelAssembler<RecruiterDTO> {

    /**
     * Adds HATEOAS links to the provided `EntityModel` of a `RecruiterDTO`.
     *
     * This method adds a self-relational link to the `RecruiterDTO` resource, enabling navigation to the single recruiter
     * information.
     *
     * @param resource the `EntityModel` of a `RecruiterDTO` to which the links are to be added
     */
    @Override
    public void addLinks(EntityModel<RecruiterDTO> resource) {
        resource.add(linkTo(methodOn(RecruiterController.class)
                .singleRecruiterInfo(resource.getContent().getId())).withSelfRel());
    }

    /**
     * Adds HATEOAS links to the provided `CollectionModel` of `EntityModel` resources of `RecruiterDTO`.
     *
     * This method adds a self-relational link to the collection of `RecruiterDTO` resources, enabling navigation to the
     * overall collection of recruiter information.
     *
     * @param resources the `CollectionModel` of `EntityModel` resources of `RecruiterDTO` to which the links are to be added
     */
    @Override
    public void addLinks(CollectionModel<EntityModel<RecruiterDTO>> resources) {
        resources.add(linkTo(methodOn(RecruiterController.class).allRecruiterInfo()).withSelfRel());
    }
}
