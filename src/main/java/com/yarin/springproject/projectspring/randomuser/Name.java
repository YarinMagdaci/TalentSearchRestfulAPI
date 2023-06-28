package com.yarin.springproject.projectspring.randomuser;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The `Name` class represents a person's name, consisting of a title, first name, and last name.

 * This class is annotated with `@Data` to automatically generate getter and setter methods, `@JsonPropertyOrder`
 * to specify the order of properties when serialized to JSON, and `@AllArgsConstructor` and `@NoArgsConstructor`
 * to generate constructors with and without arguments, respectively.
 */

@Data
@JsonPropertyOrder({"title", "first", "last"})
@AllArgsConstructor
@NoArgsConstructor
public class Name {
    private String title;
    private String first;
    private String last;
    /**
     * Returns the string representation of the `Name` object.
     *
     * @return a string representation of the `Name` object
     */
    @Override
    public String toString() {
        return "Name{" +
                "title='" + title + '\'' +
                ", first='" + first + '\'' +
                ", last='" + last + '\'' +
                '}';
    }
}
