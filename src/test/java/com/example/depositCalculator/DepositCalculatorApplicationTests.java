package com.example.depositCalculator;

import com.example.depositCalculator.components.CalculatorCommands;
import com.example.depositCalculator.services.DepositCalculatorImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(properties = "spring.shell.interactive.enabled=false")
class DepositCalculatorApplicationTests {
	@Autowired
	private DepositCalculatorImpl depositCalculator;

	@Autowired
	private CalculatorCommands calculatorCommands;

	@Test
	public void contextLoads() {
		assertThat(depositCalculator).isNotNull();
		assertThat(calculatorCommands).isNotNull();
	}
}
