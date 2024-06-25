package ie.atu.sw;

import static java.lang.System.out;

import java.util.Scanner;

public class SubMenu {
	private Scanner scanner;
	private ConsoleLogger cLogger;
	private boolean keepRunning = true;
	
	public SubMenu() {
		scanner = new Scanner(System.in);
		cLogger = new ConsoleLogger();
	}
	
	public void init() {
		while(keepRunning) {
			showOptions();
			
			int choice = Integer.parseInt(scanner.next());
			
			switch(choice) {
				case 1 -> {
					out.println("Enter a number between 1 and 100: ");
					out.println(scanner.next());
					cLogger.log(LogLevel.INFO, "Number of option is updated successfully");
				}
				case 2 -> {
					out.println("Enter a number between 1 and 100: ");
					out.println(scanner.next());
					cLogger.log(LogLevel.INFO, "Number of words is updated successfully");
				}
				case 3 -> {
					out.println("Enter A or B: ");
					out.println(scanner.next());
					cLogger.log(LogLevel.INFO, "You preference is updated successfully");
				}
				case 4 -> {
					out.println("Enter \"yes\" or \"no\": ");
					out.println(scanner.next());
					cLogger.log(LogLevel.INFO, "You preference is updated successfully");
				}
				case 5 -> keepRunning = false;
				default -> cLogger.log(LogLevel.ERROR, "Invalid Selection, choose a number from 1 to 5.");
			}
		}
	}
	
	private void showOptions() {
		out.println();
		out.print(ConsoleColour.RESET);
		
		out.print(ConsoleColour.WHITE_BOLD);
		out.println("\t(1) Specify the number of results to return (default: 10)");
		out.println("\t(2) Specify the maximum number of words to process at once (default: 10)");
		out.println("\t(3) Choose search mode: whole sentence (A, default) or individual words (B)");
		out.println("\t(4) Ignore unmatched results (yes/no) - (default: no) \"yes\" is recommended if you chose individual words (option B) above");
		out.println("\t(5) Return to the main menu");
		
		
		out.println();
		out.print(ConsoleColour.YELLOW_BOLD);
		out.print("Select Option [1-5]> ");
		out.print(ConsoleColour.WHITE_BOLD);
	}
}
