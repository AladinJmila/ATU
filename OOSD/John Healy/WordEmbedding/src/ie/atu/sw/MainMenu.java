package ie.atu.sw;

import static java.lang.System.out;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class MainMenu {
	private String inputFile = "./static/word-embeddings.txt";
	private Scanner scanner;
	private Searcher searcher;
	private Plotter plotter;
	private ConsoleLogger log;
	private SubMenu subMenu;
	private boolean keepRunning = true;
	private boolean isFirstRun = true;
	private boolean isAlreadyInvoked;
	private String[] searchTerms;
	private int wordsToProcessCount;
	
	public static String outputFile = "out.txt";
	
	public MainMenu() {
		scanner = new Scanner(System.in);
		searcher = new Searcher();
		plotter = new Plotter();
		log = new ConsoleLogger();
		subMenu = new SubMenu();
		wordsToProcessCount = subMenu.getTotalWordsToOutput();
	}
	
	public void init() throws IOException {
		while(keepRunning) {
			showOptions();
			
			int choice = Integer.parseInt(scanner.next());
			
			switch(choice) {
				case 1 	-> {
					out.println("Enter input file path");
					inputFile = scanner.next();
					log.info("Input file path succesfully added");
					}
				case 2 	-> {
					out.println("Enter output file path");
					outputFile = scanner.next();
					log.info("Output file path succesfully added");
					}
				case 3 	-> handleSearchInput();
				case 4 	-> {
					out.println();
					out.println("   | Configure Options Menu: ");
					out.print(ConsoleColour.WHITE); 
					subMenu.init();
					}
				case 5 	-> {out.println(""); out.print(ConsoleColour.WHITE); out.println("5");}
				case 6 	-> keepRunning = false;
				default -> log.error("Invalid Selection, choose a number from 1 to 6.");
					
			}
		}
	}
	
	private void handleSearchInput() throws IOException {
		
		log.cyanBoldTitle("Enter the search term or a phrase of 10 words maximum: ");

		getUserInput();
		if (!isAlreadyInvoked) {
			String[][] result = searcher.search(searchTerms, inputFile, wordsToProcessCount);
			plotter.plot(result);
			log.info("Results file will launch automatically");
			isAlreadyInvoked = true;
		}
	}
	
	private void getUserInput() throws IOException {
		isAlreadyInvoked = false;
		String input = "";
		int counter = 0;
		
		while(scanner.hasNextLine()) {
			input = scanner.nextLine();
			counter++;
			if (counter != 0 && input.length() != 0) {
				break;
			}
		}
		
		// Convert the search text to lowercase and split into search terms
		searchTerms = input.toLowerCase().split(" ");
		
		while (searchTerms.length > wordsToProcessCount) {
//			cLogger.log(subMenu.Tab, input);
			log.warn(SubMenu.TAB, "Your sentence is longer than the maximum allowed number of words!\n" +
					"             Only " + wordsToProcessCount + " will be processed.");
			out.println(SubMenu.TAB + "---------------------------------------------------------------");
			out.println(SubMenu.TAB + "| 1 | Continue anyway");
			out.println(SubMenu.TAB + "| 2 | Enter a new sentence");
			out.println(SubMenu.TAB + "| 3 | Increace the number of words to process");
			out.println(SubMenu.TAB + "---------------------------------------------------------------");
			log.cyanBoldTitle("Select Option [1-3]> ");
			
			int choice = Integer.parseInt(scanner.next());
			
			switch (choice) {
				case 1 -> {}
				case 2 -> handleSearchInput();
				case 3 -> { 
					subMenu.setWordsToProcessCount(); 
					wordsToProcessCount = subMenu.getWordsToProcessCount();
					out.println("Updated: " + wordsToProcessCount);
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
		} 

		log.cyanBoldTitle("| Main Menu:");
		out.println("---------------------------------------------------");
		out.println("| 1 | Specify Embedding File");
		out.println("| 2 | Specify an Output File (default: ./out.txt)");
		out.println("| 3 | Enter a word or a sentence");
		out.println("| 4 | Configure Options");
		out.println("| 5 | Optional Extras...");
		out.println("| 6 | Quit");
		out.println("---------------------------------------------------");
		log.cyanBoldTitle("Select Option [1-6]> ");
		
		isFirstRun = false;
	}
}
