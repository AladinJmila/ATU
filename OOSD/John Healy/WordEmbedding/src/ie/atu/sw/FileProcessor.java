package ie.atu.sw;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

public class FileProcessor {
	private static final int WORDS_COUNT = 10;
	private static final int FEATURES_COUNT = 50;
	
	private String file = "./static/word-embeddings.txt";
	private String[] words = new String[WORDS_COUNT];
	private double[][] embeddings = new double[WORDS_COUNT][FEATURES_COUNT];
	private String[][] embeddingsTest = new String[WORDS_COUNT][FEATURES_COUNT];
	
	public void processFile() {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			String line = null;
			int index = 0;
			
			while ((line = br.readLine()) != null) {
				String[] items = line.split(",");
				for (int i = 0; i < items.length; i++) {
					if (i == 0) {
						words[index] = items[i];
					} else {
						embeddings[index][i - 1] = Double.parseDouble(items[i]);
					}
					
				}
				
				if (index++ >= 9) break;
			}
			
			for (int i = 0; i < WORDS_COUNT; i++) {
				System.out.println(words[i]);
				System.out.println(Arrays.toString(embeddings[i]));
			}
			

		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	
	
}
