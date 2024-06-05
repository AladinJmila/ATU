package ie.atu.sw;

import static java.lang.System.out;

import java.io.*;

public class FileProcessor {
	public static final int WORDS_COUNT = 59_602;
	public static final int FEATURES_COUNT = 50;
	private boolean createdArrays;
	
	private String file;
	private String[] words = new String[WORDS_COUNT];
	private double[][] embeddings = new double[WORDS_COUNT][FEATURES_COUNT];
	private ConsoleLogger cLogger;
	
	FileProcessor(String filePath) {
		file = filePath;
		cLogger = new ConsoleLogger();
	}
	
	private void generateArrays() {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			String line = null;
			int index = 0;
			
			cLogger.log(LogLevel.INFO, "Processing the input file");
			
			while ((line = br.readLine()) != null) {
				String[] items = line.split(",");
				for (int i = 0; i < items.length; i++) {
					if (i == 0) {
						words[index] = items[i];
					} else {
						embeddings[index][i - 1] = Double.parseDouble(items[i]);
					}
					
				}

				if (index++ >= WORDS_COUNT - 1) break;
			}
			
			createdArrays = true;
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public String[] getWordsArray() {
		if (!createdArrays) generateArrays();
		
		return words;
	}
	
	public double[][] getEmbeddingsArray() {
		if (!createdArrays) generateArrays();
		
		return embeddings;
	}
	
}
