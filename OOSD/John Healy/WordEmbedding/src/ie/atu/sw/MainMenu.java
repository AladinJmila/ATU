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
	private Scanner scanner;
	private Searcher searcher;
	private ConsoleLogger cLogger;
	private boolean keepRunning = true;
	private boolean isFirstRun = true;
	
	public static String outputFile = "out.txt";
	
	public MainMenu() {
		scanner = new Scanner(System.in);
		searcher = new Searcher();
		cLogger = new ConsoleLogger();
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
					String [][] result = searcher.search(getUserInput(), inputFile, totalWordsToOutput);
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
		
		while(scanner.hasNextLine()) {
			input = scanner.nextLine();
			counter++;
			if (counter != 0 && input.length() != 0) {
				break;
			}
		}
		return input;
	}
	
	
	private String[] dynamicPlotTemplate(String[][] data) {
		// Initialize the length to 7 to cover short words
		int longestResultLength = 7;
		String[] formats = new String[5];
		
		for (String [] entry : data) {
			if (entry[0].length() > longestResultLength) {
				longestResultLength = entry[0].length();
			}
		}
		
		formats[0] = "| Result%-" + (longestResultLength - 6) + "s |  Score(%%)  |%n";
		formats[1] = "| %-" + longestResultLength + "s |    %-5s   |%n";
		formats[2] = "|-%-" + longestResultLength + "s-+----%-5s---|%n";
		formats[3] = "--%-" + longestResultLength + "s------%-5s----%n";
		formats[4] = "-".repeat(longestResultLength);
		out.println(Arrays.toString(formats));
		return formats;
	}
	
	private void generateOutputFile(String[][] searchResults, String[] formats) throws IOException {
		FileWriter out = new FileWriter("out.txt");
		PrintWriter print = new PrintWriter(out);
		
		print.printf(formats[0], "");
		print.printf(formats[2], formats[4], "-----");
		for (int i = 0; i < searchResults.length; i++) {
				print.printf(formats[1], searchResults[i][0], searchResults[i][1]);
				if (i != searchResults.length - 1) {
					print.printf(formats[2], formats[4], "-----");
				} else {		
					print.printf(formats[3], formats[4], "-----");
				}
		}
		print.println();

		print.close();

		Runner.launchFile("out.txt");
	}
	
	private void generateOutputFile(String[][][] searchResults) throws IOException {
		FileWriter out = new FileWriter("out.txt");
		PrintWriter print = new PrintWriter(out);
		
		for (int i = 0; i < searchResults.length; i++) {
			print.printf("|    Result    |  Score(%%)  |%n");
			print.printf("---------------+-------------%n");
			for (int j = 0; j < searchResults[i].length; j++) {
				print.printf("| %-12s |    %-5s   |%n", searchResults[i][j][0], searchResults[i][j][1]);
				System.out.println(Arrays.toString(searchResults[i][j]));
				if (j != searchResults[i].length - 1) {
					print.printf("---------------+-------------%n");
				}
			}
			print.printf("-----------------------------%n");
			print.println();
		}

		print.close();

		Runner.launchFile("out.txt");
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
