package ie.atu.sw;

import java.io.FileWriter;

import static java.lang.System.out;

import java.io.PrintWriter;

public class Runner {
	
	private static void launchFile(String filePath) {
		String os = System.getProperty("os.name").toLowerCase();
		Runtime runtime = Runtime.getRuntime();
		
		try {
			if(os.contains("win")) {
				runtime.exec(new String[]{"cmd.exe", "/c", "start", filePath});
			} else if (os.contains("mac")) {
				runtime.exec(new String[] {"open", filePath});
			} else if (os.contains("nix") || os.contains("nux")) {
				runtime.exec(new String[] {"xdg-open", filePath});
			} else {
				System.out.print(ConsoleColour.YELLOW_BOLD);
				System.out.println("[WARNING] Unsupported operating system: " + os);
				System.out.println("Please open the file manually.");
				System.out.print(ConsoleColour.RESET);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public static void main(String[] args) throws Exception {
//		MainMenu mm = new MainMenu();
//		mm.init();
		
		String file = "./static/word-embeddings.txt";
		FileProcessor fp = new FileProcessor(file);
		String[] words = fp.getWordsArray();
		double[][] embeddings = fp.getEmbeddingsArray();
		
		long startTime = System.currentTimeMillis();
		String searchTerm = "prince";
		int searchTermIndex = 0;
		
		for (int i = 0; i < words.length; i++) {
			if (words[i].equals(searchTerm.toLowerCase())) {
				searchTermIndex = i;
				break;
			}
			
		}
		
		Searcher s = new Searcher();
		double[][] result = new double[FileProcessor.WORDS_COUNT - 1][2];
		
		for (int i = 0; i < words.length - 1; i++) {
			if (i == searchTermIndex) continue;
			result[i][0]  = (double) i;
			result[i][1]  = s.cosineDistance(embeddings[searchTermIndex], embeddings[i]);
		}
		
		QuickSort qs = new QuickSort();
		qs.sort(result);
		
		FileWriter out = new FileWriter("out.txt");
		PrintWriter print = new PrintWriter(out);
		
		print.printf("|    Result    |  Score(%%)  |%n");
		print.printf("---------------+-------------%n");
		for (int i = FileProcessor.WORDS_COUNT - 2; i > FileProcessor.WORDS_COUNT - 12; i--) {
			System.out.println(result[i][1]);
			double score = result[i][1] * 100;
			int wordIndex = (int) result[i][0];
			String word = words[wordIndex];
			System.out.println(words[wordIndex]);
			print.printf("| %-12s |    %-8.1f|%n", word, score);
			if (i != FileProcessor.WORDS_COUNT - 11)
			print.printf("---------------+-------------%n");
		}
		print.printf("-----------------------------%n");
		
		print.close();
		
//		Runner.launchFile("out.txt");
		
		System.out.println("It took this long: " + (System.currentTimeMillis() - startTime));
		
//		//You may want to include a progress meter in you assignment!
//		System.out.print(ConsoleColour.CYAN);	//Change the colour of the console text
//		int size = 100;							//The size of the meter. 100 equates to 100%
//		for (int i =0 ; i < size ; i++) {		//The loop equates to a sequence of processing steps
//			printProgress(i + 1, size); 		//After each (some) steps, update the progress meter
//			Thread.sleep(10);					//Slows things down so the animation is visible 
//		}
		
	}
	

	
	/*
	 *  Terminal Progress Meter
	 *  -----------------------
	 *  You might find the progress meter below useful. The progress effect 
	 *  works best if you call this method from inside a loop and do not call
	 *  System.out.println(....) until the progress meter is finished.
	 *  
	 *  Please note the following carefully:
	 *  
	 *  1) The progress meter will NOT work in the Eclipse console, but will
	 *     work on Windows (DOS), Mac and Linux terminals.
	 *     
	 *  2) The meter works by using the line feed character "\r" to return to
	 *     the start of the current line and writes out the updated progress
	 *     over the existing information. If you output any text between 
	 *     calling this method, i.e. System.out.println(....), then the next
	 *     call to the progress meter will output the status to the next line.
	 *      
	 *  3) If the variable size is greater than the terminal width, a new line
	 *     escape character "\n" will be automatically added and the meter won't
	 *     work properly.  
	 *  
	 * 
	 */
	public static void printProgress(int index, int total) {
		if (index > total) return;	//Out of range
        int size = 50; 				//Must be less than console width
	    char done = '█';			//Change to whatever you like.
	    char todo = '░';			//Change to whatever you like.
	    
	    //Compute basic metrics for the meter
        int complete = (100 * index) / total;
        int completeLen = size * complete / 100;
        
        /*
         * A StringBuilder should be used for string concatenation inside a 
         * loop. However, as the number of loop iterations is small, using
         * the "+" operator may be more efficient as the instructions can
         * be optimized by the compiler. Either way, the performance overhead
         * will be marginal.  
         */
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < size; i++) {
        	sb.append((i < completeLen) ? done : todo);
        }
        
        /*
         * The line feed escape character "\r" returns the cursor to the 
         * start of the current line. Calling print(...) overwrites the
         * existing line and creates the illusion of an animation.
         */
        System.out.print("\r" + sb + "] " + complete + "%");
        
        //Once the meter reaches its max, move to a new line.
        if (done == total) System.out.println("\n");
    }
}