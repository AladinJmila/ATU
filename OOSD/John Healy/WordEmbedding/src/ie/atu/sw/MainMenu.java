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
	private ConsoleLogger cLogger;
	private SubMenu subMenu;
	private boolean keepRunning = true;
	private boolean isFirstRun = true;
	private String[] searchTerms;
	private int wordsToProcessCount;
	
	public static String outputFile = "out.txt";
	
	public MainMenu() {
		scanner = new Scanner(System.in);
		searcher = new Searcher();
		plotter = new Plotter();
		cLogger = new ConsoleLogger();
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
					cLogger.log(LogLevel.INFO, "Input file path succesfully added");
					}
				case 2 	-> {
					out.println("Enter output file path");
					outputFile = scanner.next();
					cLogger.log(LogLevel.INFO, "Output file path succesfully added");
					}
				case 3 	-> {
					out.print(ConsoleColour.YELLOW_BOLD);
					out.println("Enter the search term or a phrase of 10 words maximum: ");
					out.print(ConsoleColour.WHITE_BOLD);
					boolean proceed = getUserInput();
					if (proceed) {
						String[][] result = searcher.search(searchTerms, inputFile, wordsToProcessCount);
						plotter.plot(result);
						cLogger.log(LogLevel.INFO, "Results file will launch automatically");
						}
					}
				case 4 	-> {
					out.println();
					out.println("\tYou entered the Options Configuration menu");
					out.print(ConsoleColour.WHITE); 
					subMenu.init();
					}
				case 5 	-> {out.println(""); out.print(ConsoleColour.WHITE); out.println("5");}
				case 6 	-> keepRunning = false;
				default -> cLogger.log(LogLevel.ERROR, "Invalid Selection, choose a number from 1 to 6.");
					
			}
		}
	}
	
	private boolean getUserInput() {
		String input = "";
		int counter = 0;
		boolean proceed = false;
		
		
		while(scanner.hasNextLine()) {
			input = scanner.nextLine();
			counter++;
			if (counter != 0 && input.length() != 0) {
				break;
			}
		}
		
		// Convert the search text to lowercase and split into search terms
		searchTerms = input.toLowerCase().split(" ");
		
		if (searchTerms.length > wordsToProcessCount) {
			out.println("Your sentence is longer than the maximum allowed number of words");
			out.println("Only " + wordsToProcessCount + " will be processed");
			out.println("(1) Continue anyway");
			out.println("(2) Enter a new sentence");
			out.println("(3) Increace the number of words to process");
			
			int choice = Integer.parseInt(scanner.next());
			
			switch (choice) {
				case 1 -> proceed = true;
				case 2 -> proceed = false;
				case 3 -> { 
					out.println(wordsToProcessCount);
					subMenu.setWordsToProcessCount(); 
					wordsToProcessCount = subMenu.getWordsToProcessCount();
					out.println("Updated: " + wordsToProcessCount);
					proceed = true; 
					}
			}
			
		}
		
		return proceed;
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
		out.println("(3) Enter a word or a sentence");
		out.println("(4) Configure Options");
		out.println("(5) Optional Extras...");
		out.println("(6) Quit");
		
		out.println();
		out.print(ConsoleColour.YELLOW_BOLD);
		out.print("Select Option [1-6]> ");
		out.print(ConsoleColour.WHITE_BOLD);
		isFirstRun = false;
	}
}
