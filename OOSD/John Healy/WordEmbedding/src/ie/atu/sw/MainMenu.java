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
	
	
	
	private void doSearch(String searchText) throws IOException {
		FileProcessor fp = new FileProcessor(inputFile);
		String[] words = fp.getWordsArray();
		double[][] embeddings = fp.getEmbeddingsArray();
		
		
//		long startTime = System.currentTimeMillis();
		
		String[] searchTerms = searchText.toLowerCase().split(" ");
		int[] searchTermIndexs = new int[searchTerms.length];
		String[][][] allSearchResults =  new String[searchTerms.length][totalWordsToOutput][2];
		
		
		for (int i = 0; i < searchTerms.length; i++) {
			for (int j = 0; j < words.length; j++) {
				if (words[j].equals(searchTerms[i])) {
					searchTermIndexs[i] = j;
					break;
				}

			}
			
//			out.println(Arrays.toString(searchTerms));
//			out.println(Arrays.toString(searchTermIndexs));

			Searcher s = new Searcher();
			double[][] result = new double[FileProcessor.WORDS_COUNT - 1][2];

			for (int j = 0; j < words.length - 1; j++) {
				if (j == searchTermIndexs[i]) {
//					out.println(searchTermIndexs[i]);
					continue; 
				}
				result[j][0] = (double) j;
				result[j][1] = s.cosineDistance(embeddings[searchTermIndexs[i]], embeddings[j]);
			}

			new QuickSort().sort(result);
//			System.out.println("It took this long: " + (System.currentTimeMillis() - startTime));
			allSearchResults[i] = generateSearchResults(result, words, totalWordsToOutput);
		}
//		generateOutputFile(allSearchResults);
		generateResultPhrases(searchTerms, allSearchResults);
	}
	
	private String[][] generateSearchResults(double[][] searchResult, String[] words, int totalWordsToOutput){
		String[][] result = new String[totalWordsToOutput][2];
		int index = 0;
		
		for (int i = FileProcessor.WORDS_COUNT - 2; i > FileProcessor.WORDS_COUNT - 2 - totalWordsToOutput; i--) {
			double score = searchResult[i][1] * 100;
			int wordIndex = (int) searchResult[i][0];
			String word = words[wordIndex];
			result[index++] = new String[]{word, String.format("%.1f", score)};
		}
		
		return result;
	}
	
	private String[][] generateResultPhrases(String[] searchTerms, String[][][] searchResults) throws IOException {
		String[] noMatchResults = new String[]{"another", "an", "one", "the", "same", "is", 
											"whose", "comes", "with", "on", "this", "as", "s",
											"for", "first", "it", "which", "of", "turned", "but", 
											"i", "you"};
		
		String[][] resultPhrases = new String[searchResults[0].length][2];
		
		for (int i = 0; i < searchTerms.length; i++) {
			
			for (int j = 0; j < searchResults[i].length; j++) {
				StringBuilder sb = new StringBuilder();
				if (Arrays.asList(noMatchResults).contains(searchTerms[i])
						|| Arrays.asList(noMatchResults).contains(searchResults[i][j][0])) {
					if (resultPhrases[j][0] == null) {
						resultPhrases[j][0] = sb.append(searchTerms[i]).toString().trim();
					} else {
						resultPhrases[j][0] = sb.append(resultPhrases[j][0]).append(" " + searchTerms[i]).toString().trim();
					}
					resultPhrases[j][1] = "0.0";
				} else {
					if (resultPhrases[j][0] == null) {
						resultPhrases[j][0] = sb.append(searchResults[i][j][0]).toString().trim();
						out.println(searchResults[i][j][1]);
						resultPhrases[j][1] = searchResults[i][j][1];

					} else {
						resultPhrases[j][0] = sb.append(resultPhrases[j][0]).append(" " + searchResults[i][j][0]).toString().trim();
						float scoreAverage = 0.0f;
						if (resultPhrases[j][1].equals("0.0")) {
							scoreAverage = Float.parseFloat(searchResults[i][j][1]);
						} else {
							scoreAverage = (Float.parseFloat(resultPhrases[j][1]) + Float.parseFloat(searchResults[i][j][1])) / 2;
						}
						resultPhrases[j][1] = String.format("%.1f", scoreAverage);
					}
				}
			}
		}
		out.println(Arrays.toString(resultPhrases[0]));
		String[] formats = dynamicPlotTemplate(resultPhrases);
		generateOutputFile(resultPhrases, formats);
		return resultPhrases;
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
