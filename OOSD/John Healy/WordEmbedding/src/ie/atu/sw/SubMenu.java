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
	private boolean ignoreUnmachted = true;
	
	
	public SubMenu() {
		scanner = new Scanner(System.in);
		cLogger = new ConsoleLogger();
	}
	
	public void init() {
		keepRunning = true;
		while(keepRunning) {
			showOptions();
			
			int choice = Integer.parseInt(scanner.next());
			
			switch(choice) {
				case 1 -> {
					out.print("\tEnter a number between 1 and 100: ");
					out.print(ConsoleColour.YELLOW_BOLD);
					totalWordsToOutput = Integer.parseInt(scanner.next());
					cLogger.log(LogLevel.INFO, "Number of option is updated successfully");
				}
				case 2 -> {
					out.print("\tEnter a number between 1 and 20: ");
					out.print(ConsoleColour.YELLOW_BOLD);
					wordsToProcessCount = Integer.parseInt(scanner.next());
					cLogger.log(LogLevel.INFO, "Number of words is updated successfully");
				}
				case 3 -> {
					out.print("\tEnter A or B: ");
					out.print(ConsoleColour.YELLOW_BOLD);
					searchMode = scanner.next().toUpperCase().charAt(0);
					cLogger.log(LogLevel.INFO, "You preference is updated successfully");
				}
				case 4 -> {
					out.print("\tEnter \"yes\" or \"no\": ");
					out.print(ConsoleColour.YELLOW_BOLD);
					out.println(scanner.next());
					ignoreUnmachted = scanner.next().toLowerCase().trim() == "yes" ? true : false;
					cLogger.log(LogLevel.INFO, "You preference is updated successfully");
				}
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
		out.println("\t(4) Ignore unmatched results (yes/no) - (default: no)\n\t    Selecting \"yes\" is recommended if you chose individual words (option B) above");
		out.println("\t(5) Return to the main menu");
		
		
		out.println();
		out.print(ConsoleColour.YELLOW_BOLD);
		out.print("\tSelect Option [1-5]> ");
		out.print(ConsoleColour.WHITE_BOLD);
	}
	
	public int getTotalWordsToOutput() {
		return totalWordsToOutput;
	}

	public int getWordsToProcessCount() {
		return wordsToProcessCount;
	}

	public char getSearchMode() {
		return searchMode;
	}

	public boolean getIgnoreUnmachted() {
		return ignoreUnmachted;
	}
}
