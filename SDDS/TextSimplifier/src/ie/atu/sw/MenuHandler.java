package ie.atu.sw;

import static java.lang.System.out;

/*
 * MenuHandler class handles displaying the various menus used in the application.
 */

public class MenuHandler {
	String tab = ConsoleLogger.TAB;
	private boolean isFirstRun = true;

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
			out.print("*             ");
			out.print(ConsoleColour.CYAN_UNDERLINED);
			out.print("Virtual Threaded Text Simplifier");
			out.print(ConsoleColour.RESET);
			out.print(ConsoleColour.YELLOW_BOLD);
			out.println("             *");
			out.println("*                                                          *");
			out.println("************************************************************");
		}

		ConsoleLogger.cyanBoldTitle(" Main Menu:", true);
		out.println("---------------------------------------------------");
		out.println("| 1 | Specify Embeddings File");
		out.println("| 2 | Specify Google 1000 File");
		out.println("| 3 | Specify an Output File (default: ./out.txt)");
		out.println("| 4 | Execute, Analyse and Report");
		out.println("| 5 | Configure Options");
		out.println("| 6 | Quit");
		out.println("---------------------------------------------------");
		ConsoleLogger.cyanBoldTitle("Select Option [1-5]> ");

		isFirstRun = false;
	}

	public void showOptionsMenu() {
		ConsoleLogger.cyanBoldTitle(tab + " Configure Options Menu: ", true);
		out.println(tab + "-----------------------------------------------------------------------------------------");
		out.println(tab + "| 1 | Specify the tolerance level");
		out.println(tab + "| 2 | Return to the Main Menu");
		out.println(tab + "-----------------------------------------------------------------------------------------");
		ConsoleLogger.cyanBoldTitle(tab + "Select Option [1-5]> ");
	}

}
