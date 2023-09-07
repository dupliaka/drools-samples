package org.example.discountchecker;

import org.drools.core.reteoo.ReteDumper;
import org.example.discountchecker.model.Person;
import org.kie.api.KieServices;
import org.kie.api.event.rule.DebugAgendaEventListener;
import org.kie.api.event.rule.DebugRuleRuntimeEventListener;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class DiscountCheckerApp {

    public static void main(String[] args) {

        System.setProperty("drools.metric.logger.enabled", "true");

        // Create a KieServices instance
        KieServices kieServices = KieServices.Factory.get();

        // Load the KieContainer, which contains the rules
        KieContainer kContainer = kieServices.getKieClasspathContainer();

        // Create a KieSession
        KieSession kSession = kContainer.newKieSession();

        // Create a Person object
        Person person = new Person("John", 30);

        System.out.println("======= dumpRete output");
        ReteDumper.dumpRete(kSession);
        System.out.println("=======");

        // Insert the Person object into the KieSession
        kSession.insert(person);

        // Fire the rules
        kSession.fireAllRules();

        // Close the KieSession
        kSession.dispose();

        // Check if the person is eligible for a discount
        if (person.isDiscountEligible()) {
            System.out.println(person.getName() + " is eligible for a discount.");
        } else {
            System.out.println(person.getName() + " is not eligible for a discount.");
        }
    }
}
