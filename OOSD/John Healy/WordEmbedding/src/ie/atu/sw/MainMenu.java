package ie.atu.sw;

import static java.lang.System.out;
import java.io.IOException;
import java.util.Scanner;

/*
 * MainMenu class handles the main menu operations of the application.
 */

public class MainMenu {
	private String inputFile = "./static/word-embeddings.txt";
	private Scanner scanner;
	private Searcher searcher;
	private MenuHandler menuHandler;
	private Plotter plotter;
	private ConsoleLogger log;
	private OptionsMenu optionsMenu;
	private InputValidator validator;
	private boolean keepRunning = true;
	private boolean isAlreadyInvoked;
	private String[] searchTerms;
	private int wordsToProcessCount;
	private String tab = ConsoleLogger.TAB;
	
	public static String outputFile = "out.txt";
	
	// Constructor initializes the MainMenu with default settings.
	public MainMenu() {
		scanner = new Scanner(System.in);
		searcher = new Searcher();
		menuHandler = new MenuHandler();
		plotter = new Plotter();
		log = new ConsoleLogger();
		optionsMenu = new OptionsMenu(scanner);
		validator = new InputValidator(scanner);
		wordsToProcessCount = optionsMenu.getWordsToProcessCount();
	}
	
	// Initializes and runs the main menu loop.
	public void init() throws IOException {
		while(keepRunning) {
			menuHandler.showMainMenu();
			
			// Define the valid range for menu choices
			int [] range = {1, 5};
			// Validate and get user input for menu choice
			int choice = validator.validateNumericInput(
					() -> menuHandler.showMainMenu(), range);
			
			// Handle the user's menu choice
			switch(choice) {
				case 1 	-> setInputFilePath();
				case 2 	-> setOutputFilePath();
				case 3 	-> handleSearchInput();
				case 4 	-> configureOptions();
				case 5 	-> keepRunning = false;
				default -> log.error("Invalid Selection, choose a number from 1 to 5.");
					
			}
		}
	}
	
	// Prompts the user to set the input file path.
	private void setInputFilePath() {
		out.println("Enter input file path");
		inputFile = scanner.next();
		log.info("Input file path succesfully added");
	}
	
	// Prompts the user to set the output file path.
	private void setOutputFilePath() {
		out.println("Enter output file path");
		outputFile = scanner.next();
		log.info("Output file path succesfully added");
	}
	
	// Handles the user's search input and performs the search operation.
	private void handleSearchInput() throws IOException {
		log.cyanBoldTitle("Enter the search term or a phrase of 10 words maximum: ", true);

		// Get search terms from the user
		getUserInput();
		
		if (!isAlreadyInvoked) {
			// Perform the search and plot the results
			String[][] result = searcher.search(searchTerms, inputFile, optionsMenu.getTotalWordsToOutput(), 
												optionsMenu.getSearchMode(), optionsMenu.getReturnUnmachted());
			plotter.plot(result, optionsMenu.getSearchMode(), optionsMenu.getTotalWordsToOutput());
			log.info("Results file will launch automatically");
			isAlreadyInvoked = true;
		}
	}
	
	// Displays and handles the options configuration menu.
	private void configureOptions() {
		out.println();
		out.println("   | Configure Options Menu: ");
		out.print(ConsoleColour.WHITE);
		optionsMenu.init();
	}

	// Reads user input for search terms and handles input validation
	private void getUserInput() throws IOException {
		isAlreadyInvoked = false;
		String input = "";
		int counter = 0;
		
		while(scanner.hasNextLine()) {
			input = scanner.nextLine();
			counter++;
			if (counter != 0 && input.length() != 0) {
				break;
			}
		}
		
		// Convert the search text to lowercase and split into search terms
		searchTerms = input.toLowerCase().split(" ");
		
		// Validate the number of search terms
		while (searchTerms.length > wordsToProcessCount) {
			menuHandler.showMaxWordsOptions(wordsToProcessCount);
			
			// Define the valid range for choices in the max words options menu
			int [] range = {1, 3};
			// Validate and get user input for the choice
			int choice = validator.validateNumericInput(
					() -> menuHandler.showMaxWordsOptions(wordsToProcessCount), range, tab);
			
			// Handle the user's choice
			switch (choice) {
				case 1 -> {}
				case 2 -> handleSearchInput();
				case 3 -> updateWordsCount();
			}
			
		}
		
	}
	
	// Updates the number of words to process based on user input.
	private void updateWordsCount() {
		optionsMenu.setWordsToProcessCount(); 
		wordsToProcessCount = optionsMenu.getWordsToProcessCount();
	}
	
}
