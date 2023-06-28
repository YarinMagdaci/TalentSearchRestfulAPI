package com.yarin.springproject.projectspring.randomuser;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The `RandomUser` class represents a random user, containing an email address and a name.
 *
 * This class is annotated with `@Data` to automatically generate getter and setter methods, `@JsonPropertyOrder`
 * to specify the order of properties when serialized to JSON, and `@AllArgsConstructor` and `@NoArgsConstructor`
 * to generate constructors with and without arguments, respectively.
 */
@Data
@JsonPropertyOrder({"email", "name"})
@AllArgsConstructor
@NoArgsConstructor
public class RandomUser {
    private String email;
    private Name name;
    /**
     * Returns the string representation of the `RandomUser` object.
     *
     * @return a string representation of the `RandomUser` object
     */
    @Override
    public String toString() {
        return "RandomUser{" +
                "email='" + email + '\'' +
                ", name=" + name +
                '}';
    }
}
