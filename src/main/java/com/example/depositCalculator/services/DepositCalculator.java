package com.example.depositCalculator.services;

import com.example.depositCalculator.model.TermDeposit;

public interface DepositCalculator {

    long calculateFinalBalance(TermDeposit termDeposit);
}
