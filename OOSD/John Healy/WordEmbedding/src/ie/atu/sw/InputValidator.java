package ie.atu.sw;

import java.util.Arrays;
import java.util.Scanner;

@FunctionalInterface
interface Prompter {
	void prompt();
}

/*
 * Utilities class provides methods for validating user inputs.
 */

public class InputValidator {
	private ConsoleLogger log;
	private Scanner scanner;

	InputValidator(Scanner scanner) {
		log = new ConsoleLogger();
		this.scanner = scanner;
	}

	// Validates numeric input from the user within a specified range with a tab.
	public int validateNumericInput(Prompter showPrompt, int[] range, String tab) {
		int input = 0;
		boolean validInput = false;

		// Loop until a valid input is provided
		while (!validInput) {
			try {
				input = Integer.parseInt(scanner.next());

				// Check if the input is within the specified range
				if (input < range[0] || input > range[1]) {
					log.error(tab, "Input out of range, please enter a valid number.");
					showPrompt.prompt();
				} else {
					validInput = true;
				}

			} catch (NumberFormatException e) {
				log.error(tab, "Invalid input, please enter a valid number.");
				showPrompt.prompt();
			}

		}

		return input;
	}

	// Validates numeric input from the user within a specified range without a tab.
	public int validateNumericInput(Prompter showPrompt, int[] range) {
		return validateNumericInput(showPrompt, range, "");
	}

	// Validates option input from the user against a set of valid options
	public String validateOptionInput(Prompter showPrompt, String[] options, String tab) {
		String input = "";
		boolean validInput = false;

		// Convert all options to uppercase for case-insensitive comparison
		for (int i = 0; i < options.length; i++) {
			options[i] = options[i].toUpperCase();
		}

		// Loop until a valid input is provided
		while (!validInput) {
			input = scanner.next().trim().toUpperCase();

			// Check if the input is one of the valid options
			if (Arrays.asList(options).contains(input)) {
				validInput = true;
			} else {
				log.error(tab, "Invalid input, please enter a valid option.");
				showPrompt.prompt();
			}

		}

		return input;
	}
}
