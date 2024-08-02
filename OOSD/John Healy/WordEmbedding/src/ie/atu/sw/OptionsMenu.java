package ie.atu.sw;

import java.util.Scanner;

/*
 * OptionsMenu class handles the configuration options for the application.
 */

public class OptionsMenu {
	private MenuHandler menuHandler;
	private ConsoleLogger log;
	private Utilities utilities;
	private boolean keepRunning = true;
	private String tab = ConsoleLogger.TAB;
	
	private int totalWordsToOutput = 10;
	private int wordsToProcessCount = 10;
	private String searchMode = "whole sentence";
	private boolean returnUnmachted = true;
	
	
	public OptionsMenu(Scanner scanner) {
		menuHandler = new MenuHandler();
		utilities = new Utilities(scanner);
		log = new ConsoleLogger();
	}
	
	// Initializes and runs the options menu loop.
	public void init() {
		keepRunning = true;
		while(keepRunning) {
			menuHandler.showOptionsMenu();
			// Define the valid range for menu choices
			int [] range = {1, 5};
			// Validate and get user input for menu choice
			int choice = utilities.validateNumericInput(() -> menuHandler.showOptionsMenu(), range, tab);

			// Handle the user's menu choice
			switch(choice) {
				case 1 -> setTotalWordsToOutput();
				case 2 -> setWordsToProcessCount();
				case 3 -> setSearchMode();
				case 4 -> setReturnUnmached();
				case 5 -> keepRunning = false;
				default -> log.error(tab, "Invalid Selection, choose a number from 1 to 5.");
			}
		}
	}
	

	public int getTotalWordsToOutput() {
		return totalWordsToOutput;
	}
	
	private void setTotalWordsToOutput() {
		// Define the valid range for the number of words to output
		int [] range = {1, 100};
		String prompt = tab + "Enter a number between "+ range[0] + " and " + range[1] + ": ";
		log.cyanBoldTitle(prompt);
		// Validate and set the total words to output
		totalWordsToOutput = utilities.validateNumericInput(() -> log.cyanBoldTitle(prompt), range, tab);
		log.info(tab , "Number of results is updated successfully: " + totalWordsToOutput);
	}

	public int getWordsToProcessCount() {
		return wordsToProcessCount;
	}
	
	public void setWordsToProcessCount() {
		// Define the valid range for the number of words to process
		int [] range = {1, 20};
		String prompt = tab + "Enter a number between "+ range[0] + " and " + range[1] + ": ";
		log.cyanBoldTitle(prompt);
		// Validate and set the words to process count
		wordsToProcessCount = utilities.validateNumericInput(() -> log.cyanBoldTitle(prompt), range, tab);
		log.info(tab , "Number of words is updated successfully: " + wordsToProcessCount);
	}

	public String getSearchMode() {
		return searchMode;
	}
	
	private void setSearchMode() {
		// Define the valid options for search mode
		String[] options = {"A", "B"};
		String prompt = tab + "Enter " + options[0] + " or " + options[1] + ": ";
		log.cyanBoldTitle(prompt);
		// Validate and set the search mode
		char input = utilities.validateOptionInput(
				() -> log.cyanBoldTitle(prompt), options, tab).charAt(0);
		searchMode = input == 'A' ? "whole sentence" : "individual words";
		log.info(tab , "You preference is updated successfully: " + input);
	}

	public boolean getReturnUnmachted() {
		return returnUnmachted;
	}
	
	private void setReturnUnmached() {
		// Define the valid options for returning unmatched words
		String[] options = {"yes", "no"};
		String prompt = tab + "Enter \"" + options[0] + "\" or \"" + options[1] + "\": ";
		log.cyanBoldTitle(prompt);
		// Validate and set the return unmatched flag
		String input = utilities.validateOptionInput(
				() -> log.cyanBoldTitle(prompt), options, tab);
		returnUnmachted = input.equals("YES") ? true : false;
		log.info(tab ,"You preference is updated successfully: " + input);
	}
}
