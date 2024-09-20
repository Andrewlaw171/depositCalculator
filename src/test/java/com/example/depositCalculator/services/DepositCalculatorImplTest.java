package com.example.depositCalculator.services;

import com.example.depositCalculator.exceptions.TermDepositException;
import com.example.depositCalculator.model.InterestType;
import com.example.depositCalculator.model.TermDeposit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DepositCalculatorImplTest {
    private final DepositCalculatorImpl depositCalculator = new DepositCalculatorImpl();

    @Test
    public void itShouldCalculateCorrectBalanceFromSampleInputt() {
        TermDeposit termDeposit = new TermDeposit.Builder()
                .startDeposit(10000)
                .investmentTermMonths(36)
                .interestRatePerAnnum(0.011)
                .interestType(InterestType.AT_MATURITY)
                .build();
        Assertions.assertEquals(10330, depositCalculator.calculateFinalBalance(termDeposit));
    }

    @Test
    public void itShouldCalculateCorrectMonthlyCompoundInterest() {
        TermDeposit termDeposit = new TermDeposit.Builder()
                .startDeposit(1500000)
                .investmentTermMonths(36)
                .interestRatePerAnnum(0.011)
                .interestType(InterestType.MONTHLY)
                .build();
        Assertions.assertEquals(1550302, depositCalculator.calculateFinalBalance(termDeposit));
    }

    @Test
    public void itShouldCalculateCorrectQuarterlyCompoundInterest() {
        TermDeposit termDeposit = new TermDeposit.Builder()
                .startDeposit(1500000)
                .investmentTermMonths(36)
                .interestRatePerAnnum(0.011)
                .interestType(InterestType.QUARTERLY)
                .build();
        Assertions.assertEquals(1550256, depositCalculator.calculateFinalBalance(termDeposit));
    }

    @Test
    public void itShouldCalculateCorrectAnnuallyCompoundInterest() {
        TermDeposit termDeposit = new TermDeposit.Builder()
                .startDeposit(1500000)
                .investmentTermMonths(36)
                .interestRatePerAnnum(0.011)
                .interestType(InterestType.ANNUALLY)
                .build();
        Assertions.assertEquals(1550046, depositCalculator.calculateFinalBalance(termDeposit));
    }

    @Test
    public void itShouldCalculateCorrectSimpleInterest() {
        TermDeposit termDeposit = new TermDeposit.Builder()
                .startDeposit(1500000)
                .investmentTermMonths(36)
                .interestRatePerAnnum(0.011)
                .interestType(InterestType.AT_MATURITY)
                .build();
        Assertions.assertEquals(1549500, depositCalculator.calculateFinalBalance(termDeposit));
    }

    @Test
    public void itShouldHandleLessThanAYearMonthlyInterest() {
        TermDeposit termDeposit = new TermDeposit.Builder()
                .startDeposit(10000)
                .investmentTermMonths(8)
                .interestRatePerAnnum(0.064)
                .interestType(InterestType.MONTHLY)
                .build();
        Assertions.assertEquals(10435, depositCalculator.calculateFinalBalance(termDeposit));
    }

    @Test
    public void itShouldHandleLessThanAYearQuarterlyInterest() {
        TermDeposit termDeposit = new TermDeposit.Builder()
                .startDeposit(10000)
                .investmentTermMonths(8)
                .interestRatePerAnnum(0.064)
                .interestType(InterestType.QUARTERLY)
                .build();
        Assertions.assertEquals(10432, depositCalculator.calculateFinalBalance(termDeposit));
    }

    @Test
    public void itShouldHandleLessThanAYearSimpleInterest() {
        TermDeposit termDeposit = new TermDeposit.Builder()
                .startDeposit(10000)
                .investmentTermMonths(8)
                .interestRatePerAnnum(0.064)
                .interestType(InterestType.AT_MATURITY)
                .build();
        Assertions.assertEquals(10427, depositCalculator.calculateFinalBalance(termDeposit));
    }

    @Test
    public void itShouldThrowExceptionForCalculationOverflow() {
        TermDeposit termDeposit = new TermDeposit.Builder()
                .startDeposit(1000000000)
                .investmentTermMonths(9999999)
                .interestRatePerAnnum(9999)
                .interestType(InterestType.MONTHLY)
                .build();
        ArithmeticException exception = Assertions.assertThrows(ArithmeticException.class,
                () -> depositCalculator.calculateFinalBalance(termDeposit));
        Assertions.assertEquals(exception.getMessage(), "Interest calculation produced Double Overflow");
    }

    @Test
    public void itShouldThrowExceptionWithCorrectErrorsForInvalidTermDeposit() {
        TermDeposit termDeposit = new TermDeposit.Builder()
                .startDeposit(-10000)
                .investmentTermMonths(-8)
                .interestRatePerAnnum(-0.064)
                .interestType(null)
                .build();
        TermDepositException exception = Assertions.assertThrows(TermDepositException.class,
                () -> depositCalculator.calculateFinalBalance(termDeposit));
        Assertions.assertEquals(exception.getMessage(), "Invalid term deposit");
        Assertions.assertTrue(exception.getErrors().contains("startDeposit must be greater than zero"));
        Assertions.assertTrue(exception.getErrors().contains("investmentTermMonths must be greater than zero"));
        Assertions.assertTrue(exception.getErrors().contains("interestRatePerAnnum must be greater than zero"));
        Assertions.assertTrue(exception.getErrors().contains("interestType must not be null"));
    }

}
