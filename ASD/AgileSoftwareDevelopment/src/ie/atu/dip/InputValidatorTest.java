package ie.atu.dip;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class InputValidatorTest {
	private Scanner scanner;
	
	@BeforeAll
	static void inputValidatorTestsStarted() {
		System.out.println("Input validator tests started.");
	}
	
	@Test 
	void testValidateNumericInputWithValidInputInteger() {
		// Simulate user input: valid input
		String input = "5\n";
		scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
		
		// Define the valid range
		int [] range = {1, 10};
		
		int result = InputValidator.validateNumericInput(scanner, () -> {}, range);
		
		assertEquals(5, result);
	}
	
	@Test
	public void testValidateNumericInputwithInvalidInputThenValidInputInteger() {
		// Simulate user input: invalid input followed by valid input
		String input = "abc\n5\n";
		scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

		// Define the valid range
		int[] range = { 1, 10 };

		int result = InputValidator.validateNumericInput(scanner, () -> {
		}, range);
		
		assertEquals(5, result);
	}

	@Test
    public void testValidateNumericInputWithOutOfRangeInputThenValidInputInteger() {
        // Simulate user input: out-of-range input followed by valid input
        String input = "15\n5\n";
        scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        // Define the valid range
        int[] range = { 1, 10 };

        int result = InputValidator.validateNumericInput(scanner, () -> {}, range);
        assertEquals(5.0, result);
    }

	@Test
	void testValidateNumericInputWithValidInputDouble() {
		// Simulate user input: valid input
		String input = "5\n";
		scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

		 // Define the valid range
		double[] range = { 1.0, 10.0 };

		double result = InputValidator.validateNumericInput(scanner, () -> {
		}, range);
		
		assertEquals(5.0, result, 0.1);
	}

	@Test
	public void testValidateNumericInputwithInvalidInputThenValidInputDouble() {
		// Simulate user input: invalid input followed by valid input
		String input = "abc\n5\n";
		scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

		 // Define the valid range
		double[] range = { 1.0, 10.0 };

		double result = InputValidator.validateNumericInput(scanner, () -> {
		}, range);
		assertEquals(5.0, result, 0.1);
	}
	
	@Test
    public void testValidateNumericInputWithOutOfRangeInputThenValidInputDouble() {
        // Simulate user input: out-of-range input followed by valid input
        String input = "15\n5\n";
        scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        // Define the valid range
        double[] range = {1.0, 10.0};

        double result = InputValidator.validateNumericInput(scanner, () -> {}, range);
        assertEquals(5.0, result, 0.1);
    }
	
	
	@Test
    public void testValidateNameInputWithValidInput() {
		// Simulate user input: valid input
        String input = "John Smith\n";
        scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));


        String result = InputValidator.validateNameInput(scanner, () -> {});
        assertEquals("John Smith", result);
    }
  

	@Test
    public void testValidateNameInputWithInvalidInputThenValidInput() {
		// Simulate user input: invalid input followed by valid input
        String input = "$5fad@\nJohn Smith\n";
        scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));


        String result = InputValidator.validateNameInput(scanner, () -> {});
        assertEquals("John Smith", result);
    }
   
	@AfterEach
	void reset() {
		scanner = null;
	}

	@AfterAll
	static void inputValidatorTestsEnded() {
		System.out.println("Input validator tests ended.");
	}

}
