package ie.atu.sw;

import static java.lang.System.out;

import java.io.IOException;
import java.util.Arrays;

public class Searcher {
	// Perform the search functionality using all the helper methods defined below
	public String[][] search(String searchText, String inputFile, int totalWordsToOutput) throws IOException {
		// Create an instance of the FileProcessor class to handle loading word embeddings from an input file
		FileProcessor fp = new FileProcessor(inputFile);
		// Load the words array from the file
		String[] words = fp.getWordsArray();
		// Load the embeddings matrix from the file
		double[][] embeddings = fp.getEmbeddingsArray();
		
		// Convert the search text to lowercase and split into search terms
		String[] searchTerms = searchText.toLowerCase().split(" ");
		// Array to hold the indices of the search terms in the words array
		int[] searchTermIndices = new int[searchTerms.length];
		// Array to hold all search results for search terms
		String[][][] allSearchResults =  new String[searchTerms.length][totalWordsToOutput][2];
		
		// Iterate through each search term 
		for (int i = 0; i < searchTerms.length; i++) {
			// Find the index of the current search term in the words array
			for (int j = 0; j < words.length; j++) {
				if (words[j].equals(searchTerms[i])) {
					// Update the searchTermIndices array with the current index
					searchTermIndices[i] = j;
					break; // Leave early when the word is found
				}

			}
			
			// Instantiate the CosineDistance class
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
			allSearchResults[i] = generateSearchResults(result, words, totalWordsToOutput);
		}
		// Generate and return the final result phrases
		return generateResultPhrases(searchTerms, allSearchResults);
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
					
					if (resultPhrases[j][1] == null) {
						resultPhrases[j][1] = "0.0";
					}
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

		return resultPhrases;
	}
}
