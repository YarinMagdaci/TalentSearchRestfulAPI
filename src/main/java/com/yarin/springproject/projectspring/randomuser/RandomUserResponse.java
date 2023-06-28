package com.yarin.springproject.projectspring.randomuser;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * The `RandomUserResponse` class represents a response containing a list of random users.

 * This class is annotated with `@Data` to automatically generate getter and setter methods, `@JsonPropertyOrder`
 * to specify the order of properties when serialized to JSON, and `@AllArgsConstructor` and `@NoArgsConstructor`
 * to generate constructors with and without arguments, respectively.
 */

@Data
@JsonPropertyOrder({"results"})
@AllArgsConstructor
@NoArgsConstructor
public class RandomUserResponse {
    private List<RandomUser> results;
}