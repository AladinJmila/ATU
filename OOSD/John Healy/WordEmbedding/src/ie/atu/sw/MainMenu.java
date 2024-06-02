package ie.atu.sw;

import static java.lang.System.out;

import java.util.Scanner;

public class MainMenu {
	private Scanner s;
	private boolean keepRunning = true;
	private boolean isFirstRun = true;
	
	public MainMenu() {
		s = new Scanner(System.in);
	}
	
	public void init() {
		while(keepRunning) {
			showOptions();
			
			int choice = Integer.parseInt(s.next());
			
			switch(choice) {
				case 1 	-> {out.println(""); out.print(ConsoleColour.WHITE); out.println("1");}
				case 2 	-> {out.println(""); out.print(ConsoleColour.WHITE); out.println("2");}
				case 3 	-> {out.println(""); out.print(ConsoleColour.WHITE); out.println("3");}
				case 4 	-> {out.println(""); out.print(ConsoleColour.WHITE); out.println("4");}
				case 5 	-> {out.println(""); out.print(ConsoleColour.WHITE); out.println("5");}
				case 6 	-> keepRunning = false;
				default -> {
					out.println(""); 
					out.print(ConsoleColour.RED_BOLD); 
					out.println("[ERROR] Invalid Selection"); 
					out.print(ConsoleColour.RESET);
					}
			}
		}
	}
	
	private void showOptions() {
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
			out.println();
		} else {
			out.print(ConsoleColour.YELLOW_BOLD);
			out.println("************************************************************");
		}
		
		out.print(ConsoleColour.WHITE_BOLD);
		out.println("(1) Specify Embedding File");
		out.println("(2) Specify an Output File (default: ./out.txt)");
		out.println("(3) Enter a Word or Text");
		out.println("(4) Configure Options");
		out.println("(5) Optional Extras...");
		out.println("(6) Quit");
		
		out.println();
		out.print(ConsoleColour.YELLOW_BOLD);
		out.print("Select Option [1-6]>");
		out.print(ConsoleColour.CYAN_BOLD);
		out.println();
		isFirstRun = false;
	}
}
