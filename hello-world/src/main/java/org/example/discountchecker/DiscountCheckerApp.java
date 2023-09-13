package org.example.discountchecker;

import org.drools.core.reteoo.ReteDumper;
import org.example.discountchecker.model.Person;
import org.kie.api.KieServices;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.model.KieBaseModel;
import org.kie.api.builder.model.KieModuleModel;
import org.kie.api.builder.model.KieSessionModel;
import org.kie.api.conf.EqualityBehaviorOption;
import org.kie.api.conf.EventProcessingOption;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.conf.ClockTypeOption;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

public class DiscountCheckerApp {

    static final List<Person> personList = List.of(
            new Person("Elon Mask", LocalDate.of(1971, Month.JULY, 28)),
            new Person("Jeff Bezos", LocalDate.of(1964, Month.JANUARY, 12)),
            new Person("Mark Zuckerberg", LocalDate.of(1984, Month.MAY, 14)),
            new Person("Tim Cook", LocalDate.of(1960, Month.NOVEMBER, 1)),
            new Person("Sundar Pichai", LocalDate.of(1972, Month.JULY, 12)),
            new Person("Satya Nadella", LocalDate.of(1967, Month.AUGUST, 19)),
            new Person("Larry Page", LocalDate.of(1973, Month.MARCH, 26)),
            new Person("Sergey Brin", LocalDate.of(1973, Month.AUGUST, 21)),
            new Person("Jack Dorsey", LocalDate.of(1976, Month.NOVEMBER, 19)),
            new Person("Reed Hastings", LocalDate.of(1960, Month.OCTOBER, 8)));

    public static void main(String[] args) {
        DiscountCheckerApp.createKbaseProgrammatically();
    }

    public static void runDroolsDiscountCheck() {

        // Create a KieServices instance
        KieServices kieServices = KieServices.Factory.get();

        // Load the KieContainer, which contains the rules
        KieContainer kContainer = kieServices.getKieClasspathContainer();

        // Create a KieSession
        KieSession kSession = kContainer.newKieSession();

//        //Log rule events
//        KieRuntimeLogger logger =
//                KieServices.Factory.get().getLoggers().newConsoleLogger(kSession);

        //Show created Rete network
        ReteDumper.dumpRete(kSession);

        // Insert the Person objects into the KieSession
        personList.forEach(kSession::insert);

        // Fire the rules
        kSession.fireAllRules();

        System.out.println("=======Eligibility list=========");
        // Output the results
        personList.forEach(p -> {
            if (p.isDiscountEligible()) {
                System.out.println(p.getName() + " is eligible for the discount");
            } else {
                System.out.println(p.getName() + " is not eligible for the discount");
            }
        });

        // Close the KieSession
        kSession.dispose();
//        logger.close();
    }

    private static void createKbaseProgrammatically(){
        KieServices kieServices = KieServices.Factory.get();

        //kmodel file
        KieModuleModel kieModuleModel = kieServices.newKieModuleModel();

        KieBaseModel kieBaseModel1 = kieModuleModel.newKieBaseModel( "KBase1 ")
                .setDefault( true )
                .setEqualsBehavior( EqualityBehaviorOption.EQUALITY )
                .setEventProcessingMode( EventProcessingOption.STREAM )
                .addPackage("org.example.discountchecker.model");

        KieSessionModel ksessionModel1 = kieBaseModel1.newKieSessionModel( "KSession1" )
                .setDefault( true )
                .setType( KieSessionModel.KieSessionType.STATEFUL )
                .setClockType( ClockTypeOption.get("realtime") );

        //virtual file system
        KieFileSystem kfs = kieServices.newKieFileSystem();

//        InputStream is = DiscountCheckerApp.class.getClassLoader()
//                .getResourceAsStream("org/example/discountchecker/discountRules.drl");
//
//        kfs.write( "src/main/resources/KBase1/ruleSet1.drl"
//                , kieServices.getResources().newInputStreamResource( is ) );

        kfs.writeKModuleXML(kieModuleModel.toXML());

        kieServices.newKieBuilder( kfs ).buildAll();
        KieContainer kieContainer = kieServices.newKieContainer(kieServices.getRepository().getDefaultReleaseId());

        // Create a KieSession
        KieSession kSession = kieContainer.newKieSession("KSession1");

        //Show created Rete network
        ReteDumper.dumpRete(kSession);

        // Insert the Person objects into the KieSession
        personList.forEach(kSession::insert);

        // Fire the rules
        kSession.fireAllRules();

        System.out.println("=======Eligibility list=========");
        // Output the results
        personList.forEach(p -> {
            if (p.isDiscountEligible()) {
                System.out.println(p.getName() + " is eligible for the discount");
            } else {
                System.out.println(p.getName() + " is not eligible for the discount");
            }
        });

        // Close the KieSession
        kSession.dispose();
    }

}
