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
