package ie.atu.sw;

import static java.lang.System.out;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class MainMenu {
	private String inputFile = "./static/word-embeddings.txt";
	private int totalWordsToOutput = 10;
	private Scanner s;
	private ConsoleLogger cLogger;
	private boolean keepRunning = true;
	private boolean isFirstRun = true;
	
	public static String outputFile = "out.txt";
	
	public MainMenu() {
		s = new Scanner(System.in);
		cLogger = new ConsoleLogger();
	}
	
	public void init() throws IOException {
		while(keepRunning) {
			showOptions();
			
			int choice = Integer.parseInt(s.next());
			
			switch(choice) {
				case 1 	-> {
					out.println("Enter input file path");
					inputFile = s.next();
					cLogger.log(LogLevel.INFO, "Input file path succesfully added");
					}
				case 2 	-> {
					out.println("Enter output file path");
					outputFile = s.next();
					cLogger.log(LogLevel.INFO, "Output file path succesfully added");
					}
				case 3 	-> {
					out.print(ConsoleColour.YELLOW_BOLD);
					out.println("Enter the search term or a phrase of 10 words maximum: ");
					out.print(ConsoleColour.WHITE_BOLD);
					doSearch(getUserInput());
					cLogger.log(LogLevel.INFO, "Results file will launch automatically");
					}
				case 4 	-> {out.println(""); out.print(ConsoleColour.WHITE); out.println("4");}
				case 5 	-> {out.println(""); out.print(ConsoleColour.WHITE); out.println("5");}
				case 6 	-> keepRunning = false;
				default -> cLogger.log(LogLevel.ERROR, "Invalid Selection, choose a number from 1 to 6.");
					
			}
		}
	}
	
	private String getUserInput() {
		String input = "";
		int counter = 0;
		
		while(s.hasNextLine()) {
			input = s.nextLine();
			counter++;
			if (counter != 0 && input.length() != 0) {
				break;
			}
		}
		return input;
	}
	
	
	
	private void doSearch(String searchTerm) throws IOException {
		FileProcessor fp = new FileProcessor(inputFile);
		String[] words = fp.getWordsArray();
		double[][] embeddings = fp.getEmbeddingsArray();
		
		long startTime = System.currentTimeMillis();
		int searchTermIndex = 0;
		
		for (int i = 0; i < words.length; i++) {
			if (words[i].equals(searchTerm.toLowerCase())) {
				searchTermIndex = i;
				break;
			}
			
		}
		
		Searcher s = new Searcher();
		double[][] result = new double[FileProcessor.WORDS_COUNT - 1][2];
		
		for (int i = 0; i < words.length - 1; i++) {
			if (i == searchTermIndex) continue;
			result[i][0]  = (double) i;
			result[i][1]  = s.cosineDistance(embeddings[searchTermIndex], embeddings[i]);
		}
		
		new QuickSort().sort(result);
		
		generateOutput(result, words, totalWordsToOutput);
		
	}
	
	private void generateOutput(double[][] searchResult, String[] words, int totalWordsToOutput) {

		FileWriter out = new FileWriter(outputFile);
		PrintWriter print = new PrintWriter(out);
		
		print.printf("|    Result    |  Score(%%)  |%n");
		print.printf("---------------+-------------%n");
		for (int i = FileProcessor.WORDS_COUNT - 2; i > FileProcessor.WORDS_COUNT - 2 - totalWordsToOutput; i--) {
//			System.out.println(result[i][1]);
			double score = searchResult[i][1] * 100;
			int wordIndex = (int) searchResult[i][0];
			String word = words[wordIndex];
//			System.out.println(words[wordIndex]);
			print.printf("| %-12s |    %-8.1f|%n", word, score);
			if (i != FileProcessor.WORDS_COUNT - 11)
			print.printf("---------------+-------------%n");
		}
		print.printf("-----------------------------%n");
		
		print.close();
		
		Runner.launchFile(outputFile);
		
		System.out.println("It took this long: " + (System.currentTimeMillis() - startTime));
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
		out.println("(5) Configure Options");
		out.println("(6) Optional Extras...");
		out.println("(7) Quit");
		
		out.println();
		out.print(ConsoleColour.YELLOW_BOLD);
		out.print("Select Option [1-6]> ");
		out.print(ConsoleColour.WHITE_BOLD);
		isFirstRun = false;
	}
}
