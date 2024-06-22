package ie.atu.sw;

import static java.lang.System.out;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

public class Plotter {
	public void plot(String[][] resultPhrases) throws IOException {
		String[] formats = dynamicPlotTemplate(resultPhrases);
		generateOutputFile(resultPhrases, formats);
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
}
