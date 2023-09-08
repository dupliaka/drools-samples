# Drools Rule Engine Example

This is a simple Java application that demonstrates how to use the Drools rule engine to determine if a person is
eligible for a discount based on their age.

## Project Structure

The project structure looks like this:

```
.
├── pom.xml
└── src
    └── main
        ├── java
        │   └── org
        │       └── example
        │           └── discountchecker
        │               ├── DiscountCheckerApp.java
        │               └── model
        │                   └── Person.java
        └── resources
            ├── logback.xml
            ├── META-INF
            │   └── kmodule.xml
            └── org
                └── example
                    └── discountchecker
                        └── discountRules.drl

```

- `src/main/java/org/example/dicountchecker`: Contains the Java source code.
    - `DiscountChecker.java`: The main Java class for running the Drools rules.
    - `Person.java`: A Java class representing a person with age and discount eligibility.
- `src/main/resources/`: Contains the Drools rule file and config.
    - `discountRules.drl`: The Drools rule file that defines the discount eligibility rule.
    - `kmodule.xml` : The drools KBase and KSession configuration files

## Running the Example

To run this example:

1. Ensure you have Java and Maven installed on your system.

2. Clone this repository or download the source code.

3. Navigate to the project's root directory.

4. If you are using Maven, build the project:

`mvn clean package`

5. Run the application:

` java -jar target/discountchecker.jar`

6. Check the output to see if the person is eligible for a discount based on their age.
