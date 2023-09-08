package org.example.discountchecker.model;

import java.time.LocalDate;
import java.time.Period;

public class Person {
    private final String name;
    private final LocalDate birthDate;
    private boolean discountEligible;

    // Constructor, getters, and setters

    public Person(String name, LocalDate birthDate) {
        this.name = name;
        this.birthDate = birthDate;
    }

    public String getName() {
        return name;
    }

    // Method to check if the person is eligible for a discount
    public boolean isDiscountEligible() {
        return discountEligible;
    }

    public void setDiscountEligible(boolean discountEligible) {
        this.discountEligible = discountEligible;
    }

    public int getAge() {
        return Period.between(this.birthDate, LocalDate.now()).getYears();
    }

    // Other methods and properties
}
