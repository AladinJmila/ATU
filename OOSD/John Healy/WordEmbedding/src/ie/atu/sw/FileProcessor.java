package ie.atu.sw;

import java.io.*;

public class FileProcessor {
	String file = "./static/word-embeddings.txt";
	
	public void loadFile() {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			String line = null;
			int counter = 0;
			
			while ((line = br.readLine()) != null) {
				if (counter++ > 10) break;
				
				System.out.println(line);
			}
			
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	
	
}
