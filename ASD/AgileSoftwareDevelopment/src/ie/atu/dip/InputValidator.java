package ie.atu.dip;

import java.util.Scanner;

@FunctionalInterface
interface Prompter {
	void prompt();
}

public class InputValidator {
	private Scanner scanner;

	InputValidator(Scanner scanner) {
		this.scanner = scanner;
	}

	// Validates numeric input from the user within a specific rage
	public int validateNumericInput(Prompter showPrompt, int[] range) {
		int input = 0;
		boolean validInput = false;

		while (!validInput) {
			try {
				input = Integer.parseInt(scanner.next());

				// Check if the input is within the specified range
				if (input < range[0] || input > range[1]) {
					System.out.println("Input out of range. Please enter a valid number");
					showPrompt.prompt();
				} else {
					validInput = true;
				}
			} catch (NumberFormatException e) {
				System.out.println("Invalid input. Please enter a valid number");
				showPrompt.prompt();
			}
		}

		return input;
	}

	// Validate name input from the user
	public String validateNameInput(Prompter showPrompt, String input) {
		boolean validInput = false;

		while (!validInput) {
			input = scanner.next();

			// Check if input is valid (letters, spaces, hyphens, and apostrophes)
			if (input.matches("[a-zA-Z\\s'-]+")) {
				validInput = true;
			} else {
				System.out.println(
						"Invalid input. Please enter a valid name (letters, spaces, hyphens, and apostrophes only).");
				showPrompt.prompt();
			}
		}

		return input;
	}
}
