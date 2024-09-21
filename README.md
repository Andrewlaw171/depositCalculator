# Term Deposit Calculator

## Getting Started
### Requirements
- Java 17

### Running the application locally
After checking out the project you can run the service locally
using the intellij `DepositCalculatorApplication` run configuration, alternatively you can run the application
with `./mvnw spring-boot:run`

### Usage
This application is a CLI tool for calculating the final balance of a term deposit.

To see the help menu enter `help calculate-term-deposit`

Example usage:
```shell
calculate-term-deposit --startDeposit 10000 --investmentTermMonths 36 --interestRatePerAnnum 0.011 --interestType AT_MATURITY
Final balance is $10,330.00 interest paid at maturity
```

Unit tests can be run through intellij -> right-click `tests/java` -> click `Run 'Tests in 'Java''`

### Assumptions
Only allow positive numbers as inputs
Maximum calculable final balance is the maximum Long number
Final balance is rounded to the nearest dollar
No other limits on input besides above

### Design decisions
Java was chosen as the programming language for this task as it is a statically typed object-oriented language, possess
a solid standard library and is familiar to most engineers. Static typing helps us detect errors earlier
at compile-time with a small trade-off in verbosity.

Spring Shell was used in this project to abstract away the complexity of building a fully featured CLI application from scratch.
Using this framework the application can be written much faster and with simpler code with the trade-off of larger
deployment file due to dependencies, learning curve for unfamiliar developers and reduced fined-grained control that might be necessary 
in a complex project.

The interface implementation pattern is used to decouple interfaces from implementations
and improve extensibility by allowing new implementations to be created without modifying existing code.

Time constraints meant that I could not create a test suite with full coverage of the codebase. Comprehensive
unit tests were written firstly covering the core logic of the application. If given more time I would write 
tests for the CalulatorCommands component and integration tests to test the application end-to-end.