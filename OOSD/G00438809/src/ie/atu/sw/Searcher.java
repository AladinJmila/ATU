package ie.atu.sw;

import java.io.IOException;
import java.util.Arrays;

public class Searcher {
	private int totalWordsToOutput;
	private String[] searchTerms;
	private String[][][] searchResults;
	private ConsoleLogger log = new ConsoleLogger(); 
	// List of words to be ignored in the search results
	private String[] noMatchResults = {"another", "an", "one", "the", "same", "is", 
			"whose", "comes", "with", "on", "this", "as", "s", "for", "first", "it", 
			"which", "of", "turned", "but", "i", "you"};
	
	// Perform the search functionality using all the helper methods defined below
	public String[][] search(String[] searchTerms, String inputFile, int totalWordsToOutput, 
							String searchMode, boolean returnUnmatched) throws IOException {
		this.totalWordsToOutput = totalWordsToOutput;
		this.searchTerms = searchTerms;
		
		FileProcessor fp = new FileProcessor(inputFile);
		String[] words = fp.getWordsArray(); // Load the words array from the file
		double[][] embeddings = fp.getEmbeddingsArray(); // Load the embeddings matrix from the file
		int[] searchTermIndices = new int[this.searchTerms.length];
		this.searchResults = new String[this.searchTerms.length][totalWordsToOutput][2];
		
		log.info("Searching...");
		for (int i = 0; i < this.searchTerms.length; i++) {
			// Find the index of the current search term in the words array
			for (int j = 0; j < words.length; j++) {
				if (words[j].equals(this.searchTerms[i])) {
					// Update the searchTermIndices array with the current index
					searchTermIndices[i] = j;
					break; // Leave early when the word is found
				}

			}
			
			CosineDistance s = new CosineDistance();
			// Array to hold the word index and the cosine distance
			double[][] result = new double[FileProcessor.WORDS_COUNT - 1][2];

			// Compute the cosine distance between the current word and the search term
			for (int j = 0; j < words.length - 1; j++) {
				if (j == searchTermIndices[i]) {
					continue; // Skip if the search term and the word are the same
				}

				// Save the word index and the cosine distance
				result[j][0] = (double) j;
				result[j][1] = s.getDistance(embeddings[searchTermIndices[i]], embeddings[j]);
			}

			// Sort the search result using QuickSort
			new QuickSort().sort(result);
			// Generate search results from the sorted results
			this.searchResults[i] = generateSearchResults(result, words);
		}

		generateGroupedResults(this.searchTerms, this.searchResults);

		if (!returnUnmatched) {
			filterUnmached(this.searchTerms, this.searchResults);
		}

		if (searchMode.equals("whole sentence")) {
			// Generate and return the final result phrases
			return generateResultPhrases(this.searchTerms, this.searchResults);
		} else {
			return generateGroupedResults(this.searchTerms, this.searchResults);
		}
	}

	// Generate search results from the result array
	private String[][] generateSearchResults(double[][] searchResult, String[] words) {
		String[][] result = new String[totalWordsToOutput][2];
		int index = 0;

		// Iterate through the search results to generate the final result array
		for (int i = FileProcessor.WORDS_COUNT - 2; i > FileProcessor.WORDS_COUNT - 2 - totalWordsToOutput; i--) {
			// Calculate the score as a percentage
			double score = searchResult[i][1] * 100;
			// Get the word index from the search result
			int wordIndex = (int) searchResult[i][0];
			// Get the word corresponding to the word index
			String word = words[wordIndex];
			// Save the word and its score to the result array
			result[index++] = new String[] { word, String.format("%.1f", score) };
		}

		// Return the final search results
		return result;
	}

	private void filterUnmached(String[] searchTerms, String[][][] searchResults) {
		int counter = 0;
		boolean matched = false;
		String[] tempMatchedTerms = new String[searchTerms.length];
		String[][][] tempMatchedSearchResults = new String[searchResults[0].length][searchResults[0][0].length][2];

		// Iterate through each search term to check if it matches with search results
		for (int i = 0; i < searchTerms.length; i++) {
			matched = false;
			for (int j = 0; j < searchResults[i].length; j++) {
				// Check if the result is not in the noMatchResults list
				if (!Arrays.asList(noMatchResults).contains(searchResults[i][j][0])) {
					tempMatchedTerms[counter] = searchTerms[i];
					tempMatchedSearchResults[counter] = searchResults[i];
					matched = true;
				}
			}
			if (matched)
				counter++;
		}

		String[] matchedTerms = new String[counter];
		String[][][] matchedSearchResults = new String[counter][searchResults[0][0].length][2];

		// Copy matched terms and results to the final arrays
		for (int i = 0; i < counter; i++) {
			matchedTerms[i] = tempMatchedTerms[i];
			matchedSearchResults[i] = tempMatchedSearchResults[i];
		}

		this.searchTerms = matchedTerms;
		this.searchResults = matchedSearchResults;
	}
	
	// Generate a 2D array that groups search terms and search results together
	private String[][] generateGroupedResults(String[] searchTerms, String[][][] searchResults) {
		String[][] result = new String[searchTerms.length + (searchResults.length * searchResults[0].length)][2];
		int index = 0;

		// Iterate through search terms and append them to the result array
		for (int i = 0; i < searchTerms.length; i++) {
			result[index][0] = searchTerms[i];
			result[index][1] = "input";
			index++;

			// Append corresponding search results for each term
			for (int j = 0; j < searchResults[i].length; j++) {
				result[index++] = searchResults[i][j];
			}
		}

		return result;
	}

	// Generate result phrases from the search results
	private String[][] generateResultPhrases(String[] searchTerms, String[][][] searchResults) throws IOException {
		String[][] resultPhrases = new String[searchResults[0].length + 1][2];
		StringBuilder builder = new StringBuilder();
		
		for (int i = 0; i < searchTerms.length; i++) {
			builder.append(searchTerms[i] + " ");
		}
		
		resultPhrases[0][0] = builder.toString().trim();	
		resultPhrases[0][1] = "input";
		
		// Iterate through the search terms and search results to generate the result phrases
		for (int i = 0; i < searchTerms.length; i++) {
			for (int j = 0; j < searchResults[i].length; j++) {
				StringBuilder sb = new StringBuilder();
				
				int index = j + 1;
				// Check if the search term or the search result word is in the no-match list
				if (Arrays.asList(noMatchResults).contains(searchTerms[i])
						|| Arrays.asList(noMatchResults).contains(searchResults[i][j][0])) {
					// Append the search term to the result phrase
					if (resultPhrases[index][0] == null) {
						resultPhrases[index][0] = sb.append(searchTerms[i]).toString().trim();
					} else {
						resultPhrases[index][0] = sb
													.append(resultPhrases[index][0])
													.append(" " + searchTerms[i])
													.toString()
													.trim();
					}
					
					// Set the score to 0.0 for no-match results
					if (resultPhrases[index][1] == null) {
						resultPhrases[index][1] = "0.0";
					}
				} else {
					// Append the search result word to the result phrase
					if (resultPhrases[index][0] == null) {
						resultPhrases[index][0] = sb.append(searchResults[i][j][0]).toString().trim();
						resultPhrases[index][1] = searchResults[i][j][1];

					} else {
						resultPhrases[index][0] = sb
													.append(resultPhrases[index][0])
													.append(" " + searchResults[i][j][0])
													.toString()
													.trim();
						
						// Calculate the average score for the combined results
						float scoreAverage = 0.0f;
						if (resultPhrases[index][1].equals("0.0")) {
							scoreAverage = Float.parseFloat(searchResults[i][j][1]);
						} else {
							scoreAverage = (Float.parseFloat(resultPhrases[index][1]) + Float.parseFloat(searchResults[i][j][1])) / 2;
						}
						resultPhrases[index][1] = String.format("%.1f", scoreAverage);
					}
				}
			}
		}

		return resultPhrases;
	}
}
