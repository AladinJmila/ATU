package ie.atu.dip;

import java.util.Scanner;

@FunctionalInterface
interface Prompter {
	void prompt();
}

public abstract class InputValidator {
	
	// Validates numeric input from the user within a specific rage
	public static int validateNumericInput(Scanner scanner, Prompter showPrompt, int[] range) {
		int input = 0;
		boolean validInput = false;

		while (!validInput) {
			try {
				input = Integer.parseInt(scanner.nextLine());

				// Check if the input is within the specified range
				if (input < range[0] || input > range[1]) {
					System.out.println("Input out of range. Please enter a valid value");
					showPrompt.prompt();
				} else {
					validInput = true;
				}
			} catch (NumberFormatException e) {
				System.out.println("Invalid input. Please enter a valid value");
				showPrompt.prompt();
			}
		}

		return input;
	}
	
	// Validates numeric input from the user within a specific rage
		public static double validateNumericInput(Scanner scanner, Prompter showPrompt, double[] range) {
			double input = 0.0d;
			boolean validInput = false;

			while (!validInput) {
				try {
					input = Double.parseDouble(scanner.nextLine());

					// Check if the input is within the specified range
					if (input < range[0] || input > range[1]) {
						System.out.println("Input out of range. Please enter a valid value");
						showPrompt.prompt();
					} else {
						validInput = true;
					}
				} catch (NumberFormatException e) {
					System.out.println("Invalid input. Please enter a valid value");
					showPrompt.prompt();
				}
			}

			return input;
		}

	// Validate name input from the user
	public static String validateNameInput(Scanner scanner, Prompter showPrompt) {
		boolean validInput = false;
		String input = "";

		while (!validInput) {
			input = scanner.nextLine();

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
