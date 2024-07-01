package ie.atu.sw;

import static java.lang.System.out;

public class MenuHandler {
	ConsoleLogger log;
	String tab = ConsoleLogger.TAB;
	
	MenuHandler() {
		log = new ConsoleLogger();
	}

	public void showMainMenu(boolean isFirstRun) {
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
			out.print("Similarity Search with Word Embeddings");
			out.print(ConsoleColour.RESET);
			out.print(ConsoleColour.YELLOW_BOLD);
			out.println("          *");
			out.println("*                                                          *");
			out.println("************************************************************");
		} 

		log.cyanBoldTitle(" Main Menu:", true);
		out.println("---------------------------------------------------");
		out.println("| 1 | Specify Embedding File");
		out.println("| 2 | Specify an Output File (default: ./out.txt)");
		out.println("| 3 | Enter a word or a sentence");
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
		out.println(tab + "| 4 | Ignore unmatched results (yes/no) - (default: yes)");
		out.println(tab + "|   | -->  Selecting \"yes\" is recommended if you chose individual words (option B) above");
		out.println(tab + "| 5 | Return to the Main Menu");
		out.println(tab + "-----------------------------------------------------------------------------------------");
		log.cyanBoldTitle(tab+ "Select Option [1-5]> ");
	}
	
	public void showMaxWordsOptions(int wordsToProcessCount) {
		log.warn(tab , "Your sentence is longer than the maximum allowed number of words!\n" +
				"             Only " + wordsToProcessCount + " will be processed.");
		out.println(tab + "---------------------------------------------------------------");
		out.println(tab + "| 1 | Continue anyway");
		out.println(tab + "| 2 | Enter a new sentence");
		out.println(tab + "| 3 | Increace the number of words to process");
		out.println(tab + "---------------------------------------------------------------");
		log.cyanBoldTitle("Select Option [1-3]> ");
	}
	
}
