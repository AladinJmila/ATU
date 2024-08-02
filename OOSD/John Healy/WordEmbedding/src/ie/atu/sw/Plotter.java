package ie.atu.sw;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/*
 * Plotter class handles the generation and output of search results.
 */

public class Plotter {
	// Plots the search results and generates the output file.
	public void plot(String[][] resultPhrases, String searchMode, int totalWordsToOutput) throws IOException {
		// Generate formats for plotting based on the result data
		String[] formats = dynamicPlotTemplate(resultPhrases);
		// Generate the output file with the formatted lines
		generateOutputFile(linesToPlot(resultPhrases, formats, totalWordsToOutput));
	}
	
	// Creates a dynamic plot template based on the longest result length.
	private String[] dynamicPlotTemplate(String[][] data) {
		// Initialize the length to 7 to cover short words
		int longestResultLength = 7;
		String[] formats = new String[6];
		
		// Determine the longest entry in the result data
		for (String [] entry : data) {
			if (entry[0].length() > longestResultLength) {
				longestResultLength = entry[0].length();
			}
		}
		
		// Define the format strings based on the longest result length
		formats[0] = "Input: %-" + longestResultLength  + "s          "; // %n%n
		formats[1] = "| Result%-" + (longestResultLength - 6) + "s |  Score(%%)  |"; // %n
		formats[2] = "| %-" + longestResultLength + "s |    %-5s   |"; // %n
		formats[3] = "|-%-" + longestResultLength + "s-+----%-5s---|"; // %n
		formats[4] = "--%-" + longestResultLength + "s------%-5s----"; // %n
		formats[5] = "-".repeat(longestResultLength);
		return formats;
	}
	
	// Converts the search results into formatted lines for plotting.
	private String[] linesToPlot(String[][] searchResults, String[] formats, int totalWordsToOutput) {
		// Array to store the formatted result lines
		String[] result = new String[totalWordsToOutput * 2 + 3];
		int index = 0;
		
		// Format each search result into the corresponding line
		for (int i = 0; i < searchResults.length; i++) {
			StringBuilder builder = new StringBuilder();
			if (searchResults[i][1].equals("input")) {
				index = 0;
				if (result[index] == null) result[index] = "";
				result[index] = builder.append(result[index]).append(String.format(formats[0], searchResults[i][0])).append("    ").toString();
				builder = new StringBuilder();
				index++;
				if (result[index] == null) result[index] = "";
				result[index] = builder.append(result[index]).append(String.format(formats[1], "")).append("    ").toString();
			}  else {
				builder = new StringBuilder();
				if (result[index] == null) result[index] = "";
				result[index] = builder.append(result[index]).append(String.format(formats[3], formats[5], "-----")).append("    ").toString();
				index++;
				builder = new StringBuilder();
				if (result[index] == null) result[index] = "";
				result[index] = builder.append(result[index]).append(String.format(formats[2], searchResults[i][0], searchResults[i][1])).append("    ").toString();
			}	
			index++;
			
			// Add a separator line if it's the last entry
			if (index == totalWordsToOutput * 2 + 2) {
				builder = new StringBuilder();
				if (result[index ] == null) result[index] = "";
				result[index] = builder.append(result[index]).append(String.format(formats[4], formats[5], "-----")).append("    ").toString();
			}
			
		}
		
		return result;
	}
	
	// Generates the output file with the formatted lines.
	private void generateOutputFile(String[] linesToPlot) throws IOException {
		FileWriter out = new FileWriter("out.txt");
		PrintWriter print = new PrintWriter(out);
		
		// Write each line to the output file
		for (int i = 0; i < linesToPlot.length; i++) {
			if (i == 0) {
				print.printf("%s%n%n", linesToPlot[i]);
			} else {
				print.printf("%s%n", linesToPlot[i]);
			}
		}
		
		print.println();

		print.close();

		// Launch the output file
		Runner.launchFile("out.txt");
	}
}
