package org.example.discountchecker.model;

public class Person {
    private String name;
    private int age;
    private boolean discountEligible;

    // Constructor, getters, and setters

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
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
        return age;
    }

    // Other methods and properties
}
