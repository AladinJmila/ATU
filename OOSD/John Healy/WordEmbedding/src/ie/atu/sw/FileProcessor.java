package ie.atu.sw;

import java.io.*;

/*
 * Processes an embeddings text file to extract the words and relevant embeddings
 * into two separate arrays
 */

public class FileProcessor {
	// Constants defining the number of words and features in the embeddings file
	public static final int WORDS_COUNT = 59_602;
	public static final int FEATURES_COUNT = 50;
	// Flag indicating if the arrays have been created
	private boolean createdArrays;
	
	// File path to the embeddings file
	private String file;
	// Array to store the words from the embeddings file
	private String[] words = new String[WORDS_COUNT];
	// 2D array to store the embeddings
	private double[][] embeddings = new double[WORDS_COUNT][FEATURES_COUNT];
	// Instance of ConsoleLogger to log messages
	private ConsoleLogger log;
	
	// Constructor initializing the file path and ConsoleLogger instance
	FileProcessor(String filePath) {
		file = filePath;
		log = new ConsoleLogger();
	}
	
	// Generate arrays from the embeddings file
	private void generateArrays() {
		try {
			// Initialize BufferedReader to read the file
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			String line = null;
			int index = 0;
			
			// Log the start of file processing
			log.info("Processing the input file...");
			
			// Read each line from the file
			while ((line = br.readLine()) != null) {
				// Split the line by commas to separate words and embeddings
				String[] items = line.split(",");
				for (int i = 0; i < items.length; i++) {
					if (i == 0) {
						// The first item is the word
						words[index] = items[i];
					} else {
						// The rest of the items are the embedding features
						embeddings[index][i - 1] = Double.parseDouble(items[i]);
					}
					
				}

				// Stop reading if the maximum word count is reached
				if (index++ >= WORDS_COUNT - 1) break;
			}
			
			// Mark arrays as created
			createdArrays = true;
			br.close();
		} catch (Exception e) {
			// Print any exceptions that occur during file processing
			System.out.println(e);
		}
	}
	
	// Get the words array, generate it if not already created
	public String[] getWordsArray() {
		if (!createdArrays) generateArrays();
		
		return words;
	}
	
	// Get the embeddings array, generate it if not already created
	public double[][] getEmbeddingsArray() {
		if (!createdArrays) generateArrays();
		
		return embeddings;
	}
	
}
