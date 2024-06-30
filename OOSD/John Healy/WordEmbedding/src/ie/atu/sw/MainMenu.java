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
	private OptionsMenu optionsMenu;
	private Utilities utilites;
	private boolean keepRunning = true;
	private boolean isFirstRun = true;
	private boolean isAlreadyInvoked;
	private String[] searchTerms;
	private int wordsToProcessCount;
	private String tab = ConsoleLogger.TAB;
	
	public static String outputFile = "out.txt";
	
	public MainMenu() {
		scanner = new Scanner(System.in);
		searcher = new Searcher();
		plotter = new Plotter();
		log = new ConsoleLogger();
		optionsMenu = new OptionsMenu(scanner);
		utilites = new Utilities(scanner);
		wordsToProcessCount = optionsMenu.getTotalWordsToOutput();
	}
	
	public void init() throws IOException {
		while(keepRunning) {
			showOptions();
			
			int [] range = {1, 5};
			int choice = utilites.validateNumericInput(() -> showOptions(), range);
			
			switch(choice) {
				case 1 	-> setInputFilePath();
				case 2 	-> setOutputFilePath();
				case 3 	-> handleSearchInput();
				case 4 	-> configureOptions();
				case 5 	-> keepRunning = false;
				default -> log.error("Invalid Selection, choose a number from 1 to 5.");
					
			}
		}
	}
	
	private void setInputFilePath() {
		out.println("Enter input file path");
		inputFile = scanner.next();
		log.info("Input file path succesfully added");
	}
	
	private void setOutputFilePath() {
		out.println("Enter output file path");
		outputFile = scanner.next();
		log.info("Output file path succesfully added");
	}
	
	
	private void handleSearchInput() throws IOException {
		log.cyanBoldTitle("Enter the search term or a phrase of 10 words maximum: ", true);

		getUserInput();
		
		if (!isAlreadyInvoked) {
			String[][] result = searcher.search(searchTerms, inputFile, wordsToProcessCount);
			plotter.plot(result);
			log.info("Results file will launch automatically");
			isAlreadyInvoked = true;
		}
	}
	
	private void configureOptions() {
		out.println();
		out.println("   | Configure Options Menu: ");
		out.print(ConsoleColour.WHITE);
		optionsMenu.init();
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
			showMaxWordsOptions();
			
			int [] range = {1, 3};
			int choice = utilites.validateNumericInput(() -> showMaxWordsOptions(), range, tab);
			
			switch (choice) {
				case 1 -> {}
				case 2 -> handleSearchInput();
				case 3 -> updateWordsCount();
			}
			
		}
		
	}
	
	private void updateWordsCount() {
		optionsMenu.setWordsToProcessCount(); 
		wordsToProcessCount = optionsMenu.getWordsToProcessCount();
	}
	
	private void showMaxWordsOptions() {
		log.warn(tab , "Your sentence is longer than the maximum allowed number of words!\n" +
				"             Only " + wordsToProcessCount + " will be processed.");
		out.println(tab + "---------------------------------------------------------------");
		out.println(tab + "| 1 | Continue anyway");
		out.println(tab + "| 2 | Enter a new sentence");
		out.println(tab + "| 3 | Increace the number of words to process");
		out.println(tab + "---------------------------------------------------------------");
		log.cyanBoldTitle("Select Option [1-3]> ");
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
}
