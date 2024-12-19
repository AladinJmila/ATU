
package ie.atu.dip;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Edge cases have already been tested in BankingApp & InputValidator
 * This is purely to increase test coverage, so I'm only testing the happy path
 */

public class RunnerHelperMethodsTest {
	private Runner runner;
	private Scanner scanner;

	@BeforeAll
	static void RunnerHelperMethodsTestsStarted() {
		System.out.println("Runner helper methods tests started.");
	}

	@Test
	void testGetCustomerNameInput() {
		// Simulate user input: valid input
		String input = "John Smith\n";
		scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
		
		runner = new Runner();

		String result = runner.getCustomerNameInput(scanner);
		assertEquals("John Smith", result);
	}
	
	@Test
	void testGetAmountInput() {
		// Simulate user input: valid input
		String input = "1000\n";
		scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

		runner = new Runner();
		
		double result = runner.getAmountInput(scanner, "Please try again");
		assertEquals(1000.0, result);
	}
	
	@AfterEach
	void reset() {
		runner = null;
		scanner = null;
	}

	@AfterAll
	static void RunnerHelperMethodsTestsEnded() {
		System.out.println("Runner helper methods tests ended.");
	}
}