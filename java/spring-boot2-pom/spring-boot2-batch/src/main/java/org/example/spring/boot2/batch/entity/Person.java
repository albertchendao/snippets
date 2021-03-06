package org.example.spring.boot2.batch.entity;

import lombok.Data;

@Data
public class Person {
    private String lastName;
    private String firstName;

    public Person() {
    }

    public Person(String lastName, String firstName) {
        this.lastName = lastName;
        this.firstName = firstName;
    }
}
