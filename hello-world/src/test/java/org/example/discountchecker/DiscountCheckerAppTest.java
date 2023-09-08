package org.example.discountchecker;

import org.example.discountchecker.model.Person;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DiscountCheckerAppTest {

    @Test
    void runDroolsDiscountCheck() {
        DiscountCheckerApp.runDroolsDiscountCheck();
        List<String> eligiglePersonList = DiscountCheckerApp.personList.stream()
                .filter(Person::isDiscountEligible)
                .map(Person::getName)
                .collect(Collectors.toList());
        List<String> personOver60 = List.of("Tim Cook", "Reed Hastings");

        assertTrue(eligiglePersonList.containsAll(personOver60));

        // Check that the sizes of the lists are equal
        assertEquals(eligiglePersonList.size(), personOver60.size());
    }
}