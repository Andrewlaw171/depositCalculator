package com.example.depositCalculator.exceptions;

import java.util.List;

public class TermDepositException extends RuntimeException {
    private final String message;
    private final List<String> errors;

    public TermDepositException(String message, List<String> errors) {
        this.message = message;
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
