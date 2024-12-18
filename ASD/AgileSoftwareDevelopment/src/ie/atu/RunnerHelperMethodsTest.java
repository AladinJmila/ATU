package ie.atu.dip;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RunnerHelperMethodsTest {
	private Runner runner;
	private Scanner scanner;

	@BeforeEach
	void setUp() {
		runner = new Runner();
	}
	

	// Edge cases have already been tested in BankingApp & InputValidator
	@Test
	void testGetCustomerNameInput() {
		// Simulate user input: valid input
		String input = "John Smith\n";
		scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

		String result = runner.getCustomerNameInput(scanner);
		assertEquals("John Smith", result);
	}
	
	// Edge cases have already been tested in BankingApp & InputValidator
	@Test
	void testGetAmountInput() {
		// Simulate user input: valid input
		String input = "1000\n";
		scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

		double result = runner.getAmountInput(scanner, "Please try again");
		assertEquals(1000.0, result);
	}
}
