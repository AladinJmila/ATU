package ie.gmit.dip;

import java.util.List;

public class Runner {
	private static final String DICTIONARY_FILE = "./dictionary.txt";
	
	public static void main(String[] args) throws Throwable {
		DictionaryParser dp = new DictionaryParser();
		
		long start = System.currentTimeMillis();
		
		List<String> list = dp.getWords(DICTIONARY_FILE);
		
		System.out.println("Time (ms): " + (System.currentTimeMillis() - start));
		System.out.println("Time Complexity = O(n) where n = " + list.size());
		

		
		boolean hasWord = list.contains("variable");
		System.out.println(hasWord);
		
		int index = list.indexOf("variable");
		System.out.println(index);
		
	}
}
