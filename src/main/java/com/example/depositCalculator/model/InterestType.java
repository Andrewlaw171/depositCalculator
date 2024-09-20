package com.example.depositCalculator.model;

public enum InterestType {
    MONTHLY("monthly"),

    QUARTERLY("quarterly"),

    ANNUALLY("annually"),

    AT_MATURITY("at maturity");

    private final String name;

    InterestType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
