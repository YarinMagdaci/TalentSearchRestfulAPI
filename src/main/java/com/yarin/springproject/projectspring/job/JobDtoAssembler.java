package com.yarin.springproject.projectspring.job;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.SimpleRepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * A component that assembles links for the JobDTO resource.
 * Implements the SimpleRepresentationModelAssembler interface to build links based on a Spring web controller and a LinkRelationProvider.
 */
@Component
public class JobDtoAssembler implements SimpleRepresentationModelAssembler<JobDTO> {

    /**
     * Adds links to the EntityModel representing a single JobDTO.
     *
     * @param resource The EntityModel of JobDTO.
     */
    @Override
    public void addLinks(EntityModel<JobDTO> resource) {
        resource.add(linkTo(methodOn(JobController.class)
                .singleJobInfo(resource.getContent().getId())).withSelfRel());
    }

    /**
     * Adds links to the CollectionModel representing a collection of JobDTOs.
     *
     * @param resources The CollectionModel of EntityModel of JobDTO.
     */
    @Override
    public void addLinks(CollectionModel<EntityModel<JobDTO>> resources) {
        resources.add(linkTo(methodOn(JobController.class).allJobsInfo()).withSelfRel());
    }
}