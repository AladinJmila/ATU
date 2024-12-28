package ie.atu.sw;

import java.util.Scanner;

/*
 * OptionsMenu class handles the configuration options for the application.
 */

public class OptionsMenu {
	private MenuHandler menuHandler;
	private IntegersValidator intValidator;
	private DoublesValidator doubleValidator;
	private boolean keepRunning = true;
	private String tab = ConsoleLogger.TAB;
	private double toleranceLevel = 0.7;

	public OptionsMenu(Scanner scanner) {
		menuHandler = new MenuHandler();
		intValidator = new IntegersValidator(scanner);
	}

	// Initializes and runs the options menu loop.
	public void init() {
		keepRunning = true;
		while (keepRunning) {
			menuHandler.showOptionsMenu();
			// Define the valid range for menu choices
			Integer[] range = { 1, 5 };
			// Validate and get user input for menu choice
			int choice = intValidator.validate(() -> menuHandler.showOptionsMenu(), range, tab);

			// Handle the user's menu choice
			switch (choice) {
				case 1 -> setToleranceLevel();
				case 2 -> keepRunning = false;
				default -> ConsoleLogger.error(
						tab, "Invalid Selection, choose a number from " + range[0] + " to " + range[1] + ".");
			}
		}
	}

	public double getToleranceLevel() {
		return toleranceLevel;
	}

	private void setToleranceLevel() {
		// Define the valid range for the number of tolerance level
		Double[] range = { 0.0, 1.0 };
		String prompt = tab + "Enter a number between " + range[0] + " and " + range[1] + ": ";
		ConsoleLogger.cyanBoldTitle(prompt);
		// Validate and set the tolerance level
		toleranceLevel = doubleValidator.validate(() -> ConsoleLogger.cyanBoldTitle(prompt), range, tab);

		ConsoleLogger.info(tab, "Number of results is updated successfully: " + toleranceLevel);
	}
}
