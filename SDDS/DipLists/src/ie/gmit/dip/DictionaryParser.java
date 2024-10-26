package ie.gmit.dip;

import java.util.*;
import java.io.*;

public class DictionaryParser {
	
	public List<String> getWords(String file) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(file))));
		String word = null;
		
		List<String> words = new LinkedList<String>();
		
		while ((word = br.readLine()) != null) {
			words.add(word);
		}
		return words;
	}
}
