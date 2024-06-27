package ie.atu.sw;

import static java.lang.System.out;

import java.util.Scanner;

public class SubMenu {
	private Scanner scanner;
	private ConsoleLogger cLogger;
	private boolean keepRunning = true;
	
	private int totalWordsToOutput = 10;
	private int wordsToProcessCount = 10;
	private char searchMode = 'A';
	private boolean returnUnmachted = true;
	
	
	public SubMenu() {
		scanner = new Scanner(System.in);
		cLogger = new ConsoleLogger();
	}
	
	public void init() {
		keepRunning = true;
		while(keepRunning) {
			showOptions();
			
			int choice = 0;
			boolean validInput = false;
			
			while (!validInput) {
				try {
					choice = Integer.parseInt(scanner.next());
					validInput = true;
				} catch (NumberFormatException e) {
					cLogger.log(LogLevel.ERROR, "Invalid input, please enter a valid number.");
					showOptions();	
				}
								
			}
			
			
			switch(choice) {
				case 1 -> setTotalWordsToOutput();
				case 2 -> setWordsToProcessCount();
				case 3 -> setSearchMode();
				case 4 -> setReturnUnmached();
				case 5 -> keepRunning = false;
				default -> cLogger.log(LogLevel.ERROR, "Invalid Selection, choose a number from 1 to 5.");
			}
		}
	}
	
	private void showOptions() {
		out.print(ConsoleColour.RESET);
		out.println(ConsoleColour.WHITE_BOLD);
		out.println("\t(1) Specify the number of results to return (default: 10)");
		out.println("\t(2) Specify the maximum number of words to process at once (default: 10)");
		out.println("\t(3) Choose search mode: whole sentence (A, default) or individual words (B)");
		out.println("\t(4) Ignore unmatched results (yes/no) - (default: yes)\n\t    Selecting \"yes\" is recommended if you chose individual words (option B) above");
		out.println("\t(5) Return to the main menu");
		
		
		out.println();
		out.print(ConsoleColour.YELLOW_BOLD);
		out.print("\tSelect Option [1-5]> ");
		out.print(ConsoleColour.WHITE_BOLD);
	}
	
	
	
	public int getTotalWordsToOutput() {
		return totalWordsToOutput;
	}
	
	private void setTotalWordsToOutput() {
		out.print("\tEnter a number between 1 and 100: ");
		out.print(ConsoleColour.YELLOW_BOLD);
		totalWordsToOutput = Integer.parseInt(scanner.next());
		cLogger.log(LogLevel.INFO, "Number of results is updated successfully: " + totalWordsToOutput);
	}

	public int getWordsToProcessCount() {
		return wordsToProcessCount;
	}
	
	public void setWordsToProcessCount() {
		out.print("\tEnter a number between 1 and 20: ");
		out.print(ConsoleColour.YELLOW_BOLD);
		wordsToProcessCount = Integer.parseInt(scanner.next());
		out.println("here "+wordsToProcessCount);
		cLogger.log(LogLevel.INFO, "Number of words is updated successfully: " + wordsToProcessCount);
	}

	public char getSearchMode() {
		return searchMode;
	}
	
	private void setSearchMode() {
		out.print("\tEnter A or B: ");
		out.print(ConsoleColour.YELLOW_BOLD);
		searchMode = scanner.next().toUpperCase().charAt(0);
		cLogger.log(LogLevel.INFO, "You preference is updated successfully: " + searchMode);
	}

	public boolean getReturnUnmachted() {
		return returnUnmachted;
	}
	
	private void setReturnUnmached() {
		out.print("\tEnter \"yes\" or \"no\": ");
		out.print(ConsoleColour.YELLOW_BOLD);
		out.println(scanner.next());
		returnUnmachted = scanner.next().toLowerCase().trim().equals("yes") ? true : false;
		cLogger.log(LogLevel.INFO, "You preference is updated successfully: " + returnUnmachted);
	}
}
