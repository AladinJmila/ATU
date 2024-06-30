package ie.atu.sw;

import java.util.Scanner;

@FunctionalInterface
interface Prompter {
	void prompt();
}

public class Utilities {
	private static ConsoleLogger log = new ConsoleLogger();
	
	public static int validateNumericInput(Scanner scanner, Prompter showPrompt) {
		int input = 0;
		boolean validInput = false;
		
		while (!validInput) {
			try {
				input = Integer.parseInt(scanner.next());
				validInput = true;
			} catch (NumberFormatException e) {
				log.error(OptionsMenu.TAB , "Invalid input, please enter a valid number.");
				showPrompt.prompt();	
			}
							
		}
		
		return input;
	}
}
