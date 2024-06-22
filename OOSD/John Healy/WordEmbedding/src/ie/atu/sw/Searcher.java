package ie.atu.sw;

import static java.lang.System.out;

import java.io.IOException;
import java.util.Arrays;

public class Searcher {
	public String[][] search(String searchText, String inputFile, int totalWordsToOutput) throws IOException {
		FileProcessor fp = new FileProcessor(inputFile);
		String[] words = fp.getWordsArray();
		double[][] embeddings = fp.getEmbeddingsArray();
		
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
			
			CosineDistance s = new CosineDistance();
			double[][] result = new double[FileProcessor.WORDS_COUNT - 1][2];

			for (int j = 0; j < words.length - 1; j++) {
				if (j == searchTermIndexs[i]) {
					continue; 
				}
				result[j][0] = (double) j;
				result[j][1] = s.getDistance(embeddings[searchTermIndexs[i]], embeddings[j]);
			}

			new QuickSort().sort(result);
			allSearchResults[i] = generateSearchResults(result, words, totalWordsToOutput);
		}
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

		return resultPhrases;
	}
}
