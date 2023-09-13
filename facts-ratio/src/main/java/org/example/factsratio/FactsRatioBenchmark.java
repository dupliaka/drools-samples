package org.example.factsratio;

import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieSession;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.SingleShotTime)
@State(Scope.Thread)
@Warmup(iterations = 30)
@Measurement(iterations = 20)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class FactsRatioBenchmark {

    private static final int nrOfFacts = 100000;
    private KieBase kieBase;

    public static void main(String[] args) throws IOException {
        org.openjdk.jmh.Main.main(args);
    }

    @Setup(Level.Iteration)
    public void setup() {
        KieServices kieServices = KieServices.Factory.get();
        kieBase = kieServices.newKieClasspathContainer().getKieBase();
    }

    @Benchmark
    public Object fireAllRules() {

        KieSession kSession = kieBase.newKieSession();

        for (int i = 0; i < nrOfFacts; i++) {
            kSession.insert("someString" + i);
        }

        kSession.fireAllRules();
        kSession.dispose();

        return kSession;
    }

}
