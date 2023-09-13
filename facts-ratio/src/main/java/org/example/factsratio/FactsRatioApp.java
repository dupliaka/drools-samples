package org.example.factsratio;

import org.apache.commons.codec.Resources;
import org.drools.core.reteoo.ReteDumper;
import org.kie.api.KieBase;
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



import java.io.IOException;
import java.io.InputStream;


public class FactsRatioApp {
    private static final int nrOfFacts = 100000;

    public static void main(String[] args) throws IOException {
        // Create a KieServices instance
        KieServices kieServices = KieServices.Factory.get();

        // Load the KieContainer, which contains the rules
        KieContainer kContainer = kieServices.getKieClasspathContainer();

        KieBase base = kContainer.getKieBase();

        timeFactsInsertionAndRulesFiring(base);
    }

    private static void timeFactsInsertionAndRulesFiring(KieBase base) {
        // Create a KieSession
        KieSession kSession = base.newKieSession();

        for (int i = 0; i < nrOfFacts; i++) {
            kSession.insert("someString" + i);
        }

        // Fire the rules
        kSession.fireAllRules();

        // Close the KieSession
        kSession.dispose();
    }

    private static void createKbaseProgrammatically() {
        KieServices kieServices = KieServices.Factory.get();
        KieModuleModel kieModuleModel = kieServices.newKieModuleModel();

        KieBaseModel kieBaseModel1 = kieModuleModel.newKieBaseModel("KBase1 ")
                .setDefault(true)
                .setEqualsBehavior(EqualityBehaviorOption.EQUALITY)
                .setEventProcessingMode(EventProcessingOption.STREAM)
                .addPackage("org.example.factsratio.model");

        KieSessionModel ksessionModel1 = kieBaseModel1.newKieSessionModel("KSession1")
                .setDefault(true)
                .setType(KieSessionModel.KieSessionType.STATEFUL)
                .setClockType(ClockTypeOption.get("realtime"));

        KieFileSystem kfs = kieServices.newKieFileSystem();

        InputStream is = FactsRatioApp.class.getClassLoader().getResourceAsStream("org/example/factsratio/factsRatio.drl");

        kfs.write("src/main/resources/KBase1/ruleSet1.drl"
                , kieServices.getResources().newInputStreamResource(is));

        kfs.writeKModuleXML(kieModuleModel.toXML());
    }

}
