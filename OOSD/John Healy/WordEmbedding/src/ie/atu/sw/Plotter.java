package ie.atu.sw;

import static java.lang.System.out;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

public class Plotter {
	public void plot(String[][] resultPhrases, String searchMode, int totalWordsToOutput) throws IOException {
		String[] formats = dynamicPlotTemplate(resultPhrases);
		generateOutputFile(linesToPlot(resultPhrases, formats, totalWordsToOutput));
	}
	
	private String[] dynamicPlotTemplate(String[][] data) {
		// Initialize the length to 7 to cover short words
		int longestResultLength = 7;
		String[] formats = new String[6];
		
		for (String [] entry : data) {
			if (entry[0].length() > longestResultLength) {
				longestResultLength = entry[0].length();
			}
		}
		
		formats[0] = "Input: %-" + longestResultLength  + "s          "; // %n%n
		formats[1] = "| Result%-" + (longestResultLength - 6) + "s |  Score(%%)  |"; // %n
		formats[2] = "| %-" + longestResultLength + "s |    %-5s   |"; // %n
		formats[3] = "|-%-" + longestResultLength + "s-+----%-5s---|"; // %n
		formats[4] = "--%-" + longestResultLength + "s------%-5s----"; // %n
		formats[5] = "-".repeat(longestResultLength);
		return formats;
	}
	
	private String[] linesToPlot(String[][] searchResults, String[] formats, int totalWordsToOutput) {
		String[] result = new String[totalWordsToOutput * 2 + 3];
		int index = 0;
		
		for (int i = 0; i < searchResults.length; i++) {
			StringBuilder builder = new StringBuilder();
			if (searchResults[i][1].equals("input")) {
				index = 0;
				if (result[index] == null) result[index] = "";
				result[index] = builder.append(result[index]).append(String.format(formats[0], searchResults[i][0])).append("    ").toString();
				out.println(result[index]);
				builder = new StringBuilder();
				index++;
				if (result[index] == null) result[index] = "";
				result[index] = builder.append(result[index]).append(String.format(formats[1], "")).append("    ").toString();
				out.println(result[index]);
			}  else {
				builder = new StringBuilder();
				if (result[index] == null) result[index] = "";
				result[index] = builder.append(result[index]).append(String.format(formats[3], formats[5], "-----")).append("    ").toString();
				out.println(result[index]);
				index++;
				builder = new StringBuilder();
				if (result[index] == null) result[index] = "";
				result[index] = builder.append(result[index]).append(String.format(formats[2], searchResults[i][0], searchResults[i][1])).append("    ").toString();
				out.println(result[index]);
			}	
			index++;
			
			if (index == totalWordsToOutput * 2 + 2) {
				builder = new StringBuilder();
				if (result[index ] == null) result[index] = "";
				result[index] = builder.append(result[index]).append(String.format(formats[4], formats[5], "-----")).append("    ").toString();
				out.println(result[index]);
			}
			
		}
		
		System.out.println(Arrays.toString(result));
		return result;
	}
	
	private void generateOutputFile(String[] linesToPlot) throws IOException {
		FileWriter out = new FileWriter("out.txt");
		PrintWriter print = new PrintWriter(out);
		
		for (int i = 0; i < linesToPlot.length; i++) {
			if (i == 0) {
				print.printf("%s%n%n", linesToPlot[i]);
			} else {
				print.printf("%s%n", linesToPlot[i]);
			}
		}
		
		print.println();

		print.close();

		Runner.launchFile("out.txt");
	}
}
