package com.example.depositCalculator.model;

public class TermDeposit {
    private final int startingAmount;

    private final double interestRatePerAnnum;

    private final int investmentTermMonths;

    private final InterestType interestType;

    private TermDeposit(int startDeposit, double interestRatePerAnnum, int investmentTermMonths, InterestType interestType) {
        this.startingAmount = startDeposit;
        this.interestRatePerAnnum = interestRatePerAnnum;
        this.investmentTermMonths = investmentTermMonths;
        this.interestType = interestType;
    }

    public int getStartingAmount() {
        return startingAmount;
    }

    public double getInterestRatePerAnnum() {
        return interestRatePerAnnum;
    }

    public int getInvestmentTermMonths() {
        return investmentTermMonths;
    }

    public InterestType getInterestType() {
        return interestType;
    }

    public static final class Builder {
        private int startDeposit;
        private double interestRatePerAnnum;
        private int investmentTermMonths;
        private InterestType interestType;

        public Builder() {
        }

        public Builder startDeposit(int startDeposit) {
            this.startDeposit = startDeposit;
            return this;
        }

        public Builder interestRatePerAnnum(double interestRatePerAnnum) {
            this.interestRatePerAnnum = interestRatePerAnnum;
            return this;
        }

        public Builder investmentTermMonths(int investmentTermMonths) {
            this.investmentTermMonths = investmentTermMonths;
            return this;
        }

        public Builder interestType(InterestType interestType) {
            this.interestType = interestType;
            return this;
        }

        public TermDeposit build() {
            return new TermDeposit(startDeposit, interestRatePerAnnum, investmentTermMonths, interestType);
        }
    }
}
