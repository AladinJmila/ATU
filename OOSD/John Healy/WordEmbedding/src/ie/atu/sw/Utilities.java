package ie.atu.sw;

import java.util.Scanner;

@FunctionalInterface
interface Prompter {
	void prompt();
}

public class Utilities {
	private ConsoleLogger log;
	private Scanner scanner;
	
	Utilities(Scanner scanner) {
		log = new ConsoleLogger();
		this.scanner = scanner;
	}
	
	public int validateNumericInput(Prompter showPrompt, int[] range) {
		int input = 0;
		boolean validInput = false;
		

			while (!validInput) {
				try {
					input = Integer.parseInt(scanner.next());
					
					if (input < range[0] || input > range[1]) {
						log.error(OptionsMenu.TAB , "Input out of range, please enter a valid number.");
						showPrompt.prompt();
					} else {
						validInput = true;	
					}
					
				} catch (NumberFormatException e) {
					log.error(OptionsMenu.TAB , "Invalid input, please enter a valid number.");
					showPrompt.prompt();	
				}
				
			}
			
		return input;
	}
	

}
