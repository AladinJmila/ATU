package ie.atu.sw;

import java.util.Scanner;

/**
 * This class handles the configuration options for the application.
 */
public class OptionsMenu implements MenuHandlator {
	private OptionsMenuRenderer menuRenderer = new OptionsMenuRenderer();
	private IntegerValidater intValidator;
	private DoubleValidater doubleValidator;
	private YesNoValidater yesNoValidator;
	private boolean keepRunning = true;
	private String tab = ConsoleLogger.TAB;

	public OptionsMenu(Scanner scanner) {
		intValidator = new IntegerValidater(scanner);
		doubleValidator = new DoubleValidater(scanner);
		yesNoValidator = new YesNoValidater(scanner);
	}

	/**
	 * Initializes and runs the options menu loop.
	 * Displays menu options and handles user input until exit is selected.
	 */
	@Override
	public void handleMenu() {
		keepRunning = true;
		while (keepRunning) {
			menuRenderer.renderMenu();
			// Define the valid range for menu choices
			Integer[] range = { 1, 5 };
			// Validate and get user input for menu choice
			int choice = intValidator.validate(() -> menuRenderer.renderMenu(), range, tab);

			// Handle the user's menu choice
			switch (choice) {
				case 1 -> setToleranceLevel();
				case 2 -> setLaunchFile();
				case 3 -> keepRunning = false;
				default -> ConsoleLogger.error(
						tab, "Invalid Selection, choose a number from " + range[0] + " to " + range[1] + ".");
			}
		}
	}

	/**
	 * Sets the tolerance level for word processing.
	 * Prompts user for a value between 0.0 and 1.0 and updates the
	 * SimpleWordProcessor with the new tolerance level.
	 */
	private void setToleranceLevel() {
		// Define the valid range for the number of tolerance level
		Double[] range = { 0.0, 1.0 };
		String prompt = tab + "Enter a number between " + range[0] + " and " + range[1] + ": ";
		ConsoleLogger.cyanBoldTitle(prompt);
		// Validate and set the tolerance level
		double toleranceLevel = doubleValidator.validate(() -> ConsoleLogger.cyanBoldTitle(prompt), range, tab);
		new SimpleWordProcessor().setTolerance(toleranceLevel);

		ConsoleLogger.info(tab, "Number of results is updated successfully: " + toleranceLevel);
	}

	/**
	 * Configures whether to automatically launch the output file after processing.
	 * Prompts user for yes/no input and updates the OutputHandler accordingly.
	 */
	private void setLaunchFile() {
		String[] validOptions = { "yes", "no", "y", "n" };
		String prompt = tab + "Do you want to launch the file automatically when processing completes? (yes/no): ";
		ConsoleLogger.cyanBoldTitle(prompt);

		// Validate and set the launch file option
		boolean launchFileOption = yesNoValidator.validate(() -> ConsoleLogger.cyanBoldTitle(prompt), validOptions,
				tab);
		OutputHandler.setLauchFile(launchFileOption);
	}
}
