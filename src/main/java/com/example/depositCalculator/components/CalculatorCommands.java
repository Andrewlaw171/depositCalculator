package com.example.depositCalculator.components;

import com.example.depositCalculator.exceptions.TermDepositException;
import com.example.depositCalculator.model.InterestType;
import com.example.depositCalculator.model.TermDeposit;
import com.example.depositCalculator.services.DepositCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.shell.command.CommandExecution;
import org.springframework.shell.command.CommandHandlingResult;
import org.springframework.shell.command.CommandParser;
import org.springframework.shell.command.annotation.ExceptionResolver;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@ShellComponent
public class CalculatorCommands {
    private final DepositCalculator depositCalculator;

    public CalculatorCommands(@Autowired DepositCalculator depositCalculator) {
        this.depositCalculator = depositCalculator;
    }

    @ShellMethod(key = "calculate-term-deposit", value = "Calculates the final balance of a term deposit")
    public String calculateTermDeposit(
            @ShellOption(help = "Starting deposit dollar amount") int startDeposit,
            @ShellOption(help = "Decimal interest rate per annum") double interestRatePerAnnum,
            @ShellOption(help = "Investment term duration in months") int investmentTermMonths,
            @ShellOption(help = "Interest payment interval, options: [MONTHLY, QUARTERLY, ANNUALLY, AT_MATURITY]") InterestType interestType
        ) {
        TermDeposit termDeposit = new TermDeposit.Builder()
                .startDeposit(startDeposit)
                .interestRatePerAnnum(interestRatePerAnnum)
                .investmentTermMonths(investmentTermMonths)
                .interestType(interestType)
                .build();

        long finalBalance = depositCalculator.calculateFinalBalance(termDeposit);

        return String.format("Final balance is %s interest paid %s", formatBalanceString(finalBalance), interestType);
    }

    private String formatBalanceString(long balance) {
        return NumberFormat.getCurrencyInstance(Locale.US).format(balance);
    }

    @ExceptionResolver({ TermDepositException.class })
    CommandHandlingResult termDepositExceptionHandler(TermDepositException e) {
        return CommandHandlingResult.of(String.format("%s, errors: %s\n", e.getMessage(), e.getErrors()));
    }

    @ExceptionResolver({ CommandExecution.CommandParserExceptionsException.class })
    CommandHandlingResult commandParserExceptionHandler(CommandExecution.CommandParserExceptionsException e) {
        List<String> errors = new ArrayList<>();
        for (CommandParser.CommandParserException pe : e.getParserExceptions()) {
                errors.add(pe.getMessage());
        }
        return CommandHandlingResult.of(String.format("Error parsing command arguments, errors: %s\n", errors));
    }
    @ExceptionResolver({ ArithmeticException.class })
    CommandHandlingResult arithmeticExceptionHandler(ArithmeticException e) {
        return CommandHandlingResult.of(
                String.format("Calculator Overflow, final balance exceeds %s, please use smaller values\n",
                        formatBalanceString(Long.MAX_VALUE))
        );
    }
}
