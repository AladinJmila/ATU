package ie.atu.sw;

import static java.lang.System.out;

import java.util.Scanner;

public class OptionsMenu {
	private Scanner scanner;
	private MenuHandler menuHandler;
	private ConsoleLogger log;
	private Utilities utilites;
	private boolean keepRunning = true;
	private String tab = ConsoleLogger.TAB;
	
	private int totalWordsToOutput = 10;
	private int wordsToProcessCount = 10;
	private char searchMode = 'A';
	private boolean returnUnmachted = true;
	
	
	public OptionsMenu(Scanner scanner) {
		menuHandler = new MenuHandler();
		this.scanner = scanner;
		utilites = new Utilities(scanner);
		log = new ConsoleLogger();
	}
	
	public void init() {
		keepRunning = true;
		while(keepRunning) {
			menuHandler.showOptionsMenu();
			int [] range = {1, 5};
			int choice = utilites.validateNumericInput(() -> menuHandler.showOptionsMenu(), range, tab);

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
		int [] range = {1, 100};
		String prompt = "Enter a number between "+ range[0] + " and " + range[1] + ": ";
		log.cyanBoldTitle(tab + prompt);
		totalWordsToOutput = utilites.validateNumericInput(() -> log.cyanBoldTitle(tab + prompt), range, tab);
		log.info(tab , "Number of results is updated successfully: " + totalWordsToOutput);
	}

	public int getWordsToProcessCount() {
		return wordsToProcessCount;
	}
	
	public void setWordsToProcessCount() {
		int [] range = {1, 20};
		String prompt = "Enter a number between "+ range[0] + " and " + range[1] + ": ";
		log.cyanBoldTitle(tab + prompt);
		wordsToProcessCount = utilites.validateNumericInput(() -> log.cyanBoldTitle(tab + prompt), range, tab);
		log.info(tab , "Number of words is updated successfully: " + wordsToProcessCount);
	}

	public char getSearchMode() {
		return searchMode;
	}
	
	private void setSearchMode() {
		log.cyanBoldTitle(tab + "Enter A or B: ");
		searchMode = scanner.next().toUpperCase().charAt(0);
		log.info(tab , "You preference is updated successfully: " + searchMode);
	}

	public boolean getReturnUnmachted() {
		return returnUnmachted;
	}
	
	private void setReturnUnmached() {
		log.cyanBoldTitle(tab + "Enter \"yes\" or \"no\": ");
		out.println(scanner.next());
		returnUnmachted = scanner.next().toLowerCase().trim().equals("yes") ? true : false;
		log.info(tab ,"You preference is updated successfully: " + returnUnmachted);
	}
}
