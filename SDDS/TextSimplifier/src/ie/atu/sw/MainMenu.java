package ie.atu.sw;

import static java.lang.System.out;
import java.util.Scanner;

/*
 * MainMenu class handles the main menu operations of the application.
 */

public class MainMenu implements MenuHandlator {
	private String embeddingsFilePath = "./embeddings.txt";
	private String google1000FilePath = "./google-1000.txt";
	private String inputFilePath = "./input.txt";
	private Scanner scanner;
	private MainMenuRenderer menuRenderer = new MainMenuRenderer();;
	private OptionsMenu optionsMenu;
	private IntegerValidator integersValidator;
	private boolean keepRunning = true;
	public static String outputFile = "out.txt";

	// Constructor initializes the MainMenu with default settings.
	public MainMenu() {
		scanner = new Scanner(System.in);
		optionsMenu = new OptionsMenu(scanner);
		integersValidator = new IntegerValidator(scanner);
	}

	// Initializes and runs the main menu loop.
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

	private void setEmbeddingsFilePath() {
		embeddingsFilePath = FilePathLoader.loadPath(scanner, "Enter embeddings file path");
	}

	private void setGoogle1000FilePath() {
		google1000FilePath = FilePathLoader.loadPath(scanner, "Enter Google 1000 file path");
	}

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

	// Prompts the user to set the output file path.
	private void setOutputFilePath() {
		ConsoleLogger.cyanBoldTitle("Enter output file path: ", true);
		outputFile = scanner.next();
		ConsoleLogger.info("Output file path succesfully added");
	}

	// Handles the user's search input and performs the search operation.
	private void handleTextSimplification() throws Exception {
		inputFilePath = getInputFilePath();

		var embeddingsMap = new EmbeddingsMapper().map(embeddingsFilePath);
		var google1000Map = new Google1000Mapper().map(google1000FilePath, embeddingsMap);

		new TextSimplifier(inputFilePath, embeddingsMap, google1000Map).simplifyText();
	}

	// Displays and handles the options configuration menu.
	private void configureOptions() {
		out.println();
		out.println("   | Configure Options Menu: ");
		out.print(ConsoleColour.WHITE);
		optionsMenu.handleMenu();
	}
}
