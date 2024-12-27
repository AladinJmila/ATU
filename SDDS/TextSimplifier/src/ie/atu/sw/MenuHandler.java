package ie.atu.sw;

import static java.lang.System.out;

/*
 * MenuHandler class handles displaying the various menus used in the application.
 */

public class MenuHandler {
	ConsoleLogger log;
	String tab = ConsoleLogger.TAB;
	private boolean isFirstRun = true;

	MenuHandler() {
		log = new ConsoleLogger();
	}

	public void showMainMenu() {
		out.println();
		out.print(ConsoleColour.RESET);
		if (isFirstRun) {
			out.print(ConsoleColour.YELLOW_BOLD);
			out.println("************************************************************");
			out.print("*     ");
			out.print(ConsoleColour.CYAN_BOLD);
			out.print("ATU - Dept. of Computer Science & Applied Physics");
			out.print(ConsoleColour.YELLOW_BOLD);
			out.println("    *");
			out.println("*                                                          *");
			out.print("*          ");
			out.print(ConsoleColour.CYAN_UNDERLINED);
			out.print("Virtual Threaded Text Simplifier");
			out.print(ConsoleColour.RESET);
			out.print(ConsoleColour.YELLOW_BOLD);
			out.println("          *");
			out.println("*                                                          *");
			out.println("************************************************************");
		}

		log.cyanBoldTitle(" Main Menu:", true);
		out.println("---------------------------------------------------");
		out.println("| 1 | Specify Embeddings File");
		out.println("| 2 | Specify Google 1000 File");
		out.println("| 3 | Specify an Output File (default: ./out.txt)");
		out.println("| 3 | Execute, Analyse and Report");
		out.println("| 4 | Configure Options");
		out.println("| 5 | Quit");
		out.println("---------------------------------------------------");
		log.cyanBoldTitle("Select Option [1-5]> ");

		isFirstRun = false;
	}

	public void showOptionsMenu() {
		log.cyanBoldTitle(tab + " Configure Options Menu: ", true);
		out.println(tab + "-----------------------------------------------------------------------------------------");
		out.println(tab + "| 1 | Specify the number of results to return (default: 10)");
		out.println(tab + "| 2 | Specify the maximum number of words to process at once (default: 10)");
		out.println(tab + "| 3 | Choose search mode: whole sentence (A, default) or individual words (B)");
		out.println(tab + "| 4 | Return unmatched results (yes/no) - (default: yes)");
		out.println(tab + "|   | -->  Selecting \"no\" is recommended if you chose individual words (option B) above");
		out.println(tab + "| 5 | Return to the Main Menu");
		out.println(tab + "-----------------------------------------------------------------------------------------");
		log.cyanBoldTitle(tab + "Select Option [1-5]> ");
	}

}
