package ie.atu.sw;

import static java.lang.System.out;

import java.util.Scanner;

public class SubMenu {
	private Scanner scanner;
	private ConsoleLogger log;
	private boolean keepRunning = true;
	
	private int totalWordsToOutput = 10;
	private int wordsToProcessCount = 10;
	private char searchMode = 'A';
	private boolean returnUnmachted = true;
	public static final String TAB = "   ";
	
	
	public SubMenu() {
		scanner = new Scanner(System.in);
		log = new ConsoleLogger();
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
					log.error(TAB , "Invalid input, please enter a valid number.");
					showOptions();	
				}
								
			}
			
			
			switch(choice) {
				case 1 -> setTotalWordsToOutput();
				case 2 -> setWordsToProcessCount();
				case 3 -> setSearchMode();
				case 4 -> setReturnUnmached();
				case 5 -> keepRunning = false;
				default -> log.error(TAB, "Invalid Selection, choose a number from 1 to 5.");
			}
		}
	}
	
	private void showOptions() {
		log.cyanBoldTitle(TAB + " Configure Options Menu: ", true);
		out.println(TAB + "-----------------------------------------------------------------------------------------");
		out.println(TAB + "| 1 | Specify the number of results to return (default: 10)");
		out.println(TAB + "| 2 | Specify the maximum number of words to process at once (default: 10)");
		out.println(TAB + "| 3 | Choose search mode: whole sentence (A, default) or individual words (B)");
		out.println(TAB + "| 4 | Ignore unmatched results (yes/no) - (default: yes)");
		out.println(TAB + "|   | -->  Selecting \"yes\" is recommended if you chose individual words (option B) above");
		out.println(TAB + "| 5 | Return to the Main Menu");
		out.println(TAB + "-----------------------------------------------------------------------------------------");
		log.cyanBoldTitle(TAB + "Select Option [1-5]> ");
	}
	
	
	
	public int getTotalWordsToOutput() {
		return totalWordsToOutput;
	}
	
	private void setTotalWordsToOutput() {
		log.cyanBoldTitle(TAB + "Enter a number between 1 and 100: ");
		totalWordsToOutput = Integer.parseInt(scanner.next());
		log.info(TAB, "Number of results is updated successfully: " + totalWordsToOutput);
	}

	public int getWordsToProcessCount() {
		return wordsToProcessCount;
	}
	
	public void setWordsToProcessCount() {
		log.cyanBoldTitle(TAB + "Enter a number between 1 and 20: ");
		wordsToProcessCount = Integer.parseInt(scanner.next());
		log.info(TAB, "Number of words is updated successfully: " + wordsToProcessCount);
	}

	public char getSearchMode() {
		return searchMode;
	}
	
	private void setSearchMode() {
		log.cyanBoldTitle(TAB + "Enter A or B: ");
		searchMode = scanner.next().toUpperCase().charAt(0);
		log.info(TAB, "You preference is updated successfully: " + searchMode);
	}

	public boolean getReturnUnmachted() {
		return returnUnmachted;
	}
	
	private void setReturnUnmached() {
		log.cyanBoldTitle(TAB + "Enter \"yes\" or \"no\": ");
		out.println(scanner.next());
		returnUnmachted = scanner.next().toLowerCase().trim().equals("yes") ? true : false;
		log.info(TAB,"You preference is updated successfully: " + returnUnmachted);
	}
}
