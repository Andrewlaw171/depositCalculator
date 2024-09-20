package com.example.depositCalculator.services;

import com.example.depositCalculator.exceptions.TermDepositException;
import com.example.depositCalculator.model.TermDeposit;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DepositCalculatorImpl implements DepositCalculator {
    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(DepositCalculatorImpl.class);

    @Override
    public long calculateFinalBalance(TermDeposit termDeposit) {
        validateTermDeposit(termDeposit);

        double compoundsPerYear = 0.0;
        switch(termDeposit.getInterestType()) {
            case MONTHLY -> compoundsPerYear = 12.0;
            case QUARTERLY -> compoundsPerYear = 4.0;
            case ANNUALLY -> compoundsPerYear = 1.0;
            case AT_MATURITY -> compoundsPerYear = 0.0;
        }
        return Math.round(calculateInterest(termDeposit, compoundsPerYear));
    }

    private double calculateInterest(TermDeposit termDeposit, double compoundsPerYear) {
        int principal = termDeposit.getStartingAmount();
        double rate = termDeposit.getInterestRatePerAnnum();
        double timeYears = termDeposit.getInvestmentTermMonths() / 12.0;

        if (compoundsPerYear == 0.0) {
            return principal * (1 + rate * timeYears);
        }

        double result =  principal * Math.pow(1 + rate/compoundsPerYear, (compoundsPerYear*timeYears));
        if (result > Long.MAX_VALUE) {
            throw new ArithmeticException("Interest calculation produced Double Overflow");
        }

        return result;
    }

    private void validateTermDeposit(TermDeposit termDeposit) {
        List<String> errors = new ArrayList<>();
        if (termDeposit.getStartingAmount() <= 0) {
            errors.add("startDeposit must be greater than zero");
        }
        if (termDeposit.getInvestmentTermMonths() <= 0) {
            errors.add("investmentTermMonths must be greater than zero");
        }
        if (termDeposit.getInterestRatePerAnnum() <= 0) {
            errors.add("interestRatePerAnnum must be greater than zero");
        }
        if (termDeposit.getInterestType() == null) {
            errors.add("interestType must not be null");
        }
        if (!errors.isEmpty()) {
            LOGGER.trace("Invalid term deposit received, errors: {}", errors);
            throw new TermDepositException("Invalid term deposit", errors);
        }
    }
}
