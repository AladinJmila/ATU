package ie.atu.sw;

import static java.lang.System.out;
import java.util.Scanner;

/**
 * MainMenu class provides the main menu interface for the Text Simplifier
 * application.
 * This class handles user interactions and menu operations.
 */
public class MainMenu implements MenuHandlator {
	private String embeddingsFilePath;
	private String google1000FilePath;
	private String inputFilePath;
	private Scanner scanner;
	private MainMenuRenderer menuRenderer = new MainMenuRenderer();;
	private OptionsMenu optionsMenu;
	private IntegerValidater integersValidator;
	private boolean keepRunning = true;
	public static String outputFile = "out.txt";

	// Constructor initializes the MainMenu with default settings.
	public MainMenu() {
		scanner = new Scanner(System.in);
		optionsMenu = new OptionsMenu(scanner);
		integersValidator = new IntegerValidater(scanner);
	}

	/**
	 * Displays the main menu options to the user and handles user input.
	 */
	@Override
	public void handleMenu() throws Exception {
		while (keepRunning) {
			menuRenderer.renderMenu();

			// Define the valid range for menu choices
			Integer[] range = { 1, 6 };
			// Validate and get user input for menu choice
			int choice = integersValidator.validate(() -> menuRenderer.renderMenu(), range);

			// Handle the user's menu choice
			switch (choice) {
				case 1 -> setEmbeddingsFilePath();
				case 2 -> setGoogle1000FilePath();
				case 3 -> setOutputFilePath();
				case 4 -> handleTextSimplification();
				case 5 -> configureOptions();
				case 6 -> keepRunning = false;
				default -> ConsoleLogger.error(
						"Invalid Selection, choose a number from " + range[0] + " to " + range[1] + ".");

			}
		}
	}

	/**
	 * Sets the file path for the embeddings file based on user input.
	 */
	private void setEmbeddingsFilePath() {
		embeddingsFilePath = FilePathLoader.loadPath(scanner, "Enter embeddings file path");
	}

	/**
	 * Sets the file path for the Google 1000 common words file based on user input.
	 */
	private void setGoogle1000FilePath() {
		google1000FilePath = FilePathLoader.loadPath(scanner, "Enter Google 1000 file path");
	}

	/**
	 * Gets the input file path from the user. If necessary files (embeddings or
	 * Google 1000) are missing, prompts the user to provide them first.
	 * 
	 * @return String The validated input file path
	 */
	private String getInputFilePath() {
		if (embeddingsFilePath == null) {
			ConsoleLogger.error("Missing Embedding file.");
			setEmbeddingsFilePath();
		}

		if (google1000FilePath == null) {
			ConsoleLogger.error("Missing Google 1000 file.");
			setGoogle1000FilePath();
		}

		return FilePathLoader.loadPath(scanner, "Enter input file path");
	}

	/**
	 * Prompts the user to set the output file path and updates the OutputHandler
	 * with the new path.
	 */
	private void setOutputFilePath() {
		ConsoleLogger.cyanBoldTitle("Enter output file path: ", true);
		outputFile = scanner.next();
		if (outputFile != null) {
			OutputHandler.setOutputPath(outputFile);
		}
		ConsoleLogger.info("Output file path succesfully added");
	}

	/**
	 * Handles the text simplification process by:
	 * 1. Getting the input file path
	 * 2. Loading and mapping the embeddings
	 * 3. Processing the Google 1000 common words
	 * 4. Performing text simplification
	 * 
	 * @throws Exception If there are errors during file processing or text
	 *                   simplification
	 */
	private void handleTextSimplification() throws Exception {
		inputFilePath = getInputFilePath();

		var embeddingsMap = new EmbeddingsMapper().map(embeddingsFilePath);
		var google1000Map = new Google1000Mapper().map(google1000FilePath, embeddingsMap);

		new TextSimplifier(inputFilePath, embeddingsMap, google1000Map).simplifyText();
	}

	/**
	 * Displays the options configuration menu
	 */
	private void configureOptions() {
		out.println();
		out.println("   | Configure Options Menu: ");
		out.print(ConsoleColour.WHITE);
		optionsMenu.handleMenu();
	}
}
