package ie.atu.sw;

import java.util.Arrays;

public class Runner {

	public static void main(String[] args) throws Exception {
//		MainMenu mm = new MainMenu();
//		mm.init();
		
		String file = "./static/word-embeddings.txt";
		FileProcessor fp = new FileProcessor(file);
		String[] words = fp.getWordsArray();
		double[][] embeddings = fp.getEmbeddingsArray();
//		
//		for (int i = 0; i < words.length; i++) {
//			System.out.println(words[i]);
//			System.out.println(Arrays.toString(embeddings[i]));
//		}
		
		System.out.println(Arrays.toString(words));
		System.out.println(words.length);
		
		String searchTerm = "horse";
		int searchTermIndex = 0;
		
		String searchTerm2 = "monkey";
		int searchTermIndex2 = 0;
		
		for (int i = 0; i < words.length; i++) {
			if (words[i].equals(searchTerm.toLowerCase())) {
				searchTermIndex = i;
//				break;
			}
			
			if (words[i].equals(searchTerm2.toLowerCase())) {
				searchTermIndex2 = i;
//				break;
			}
		}
		
		
		
//		double[] v1 = {1.0, 2.0, 3.0};
//		double[] v2 = {4.0, 5.0, 6.0};
		
		Searcher s = new Searcher();
		
//		System.out.println(s.cosineDistance(embeddings[searchTermIndex], embeddings[searchTermIndex2]));
		
		for (int i = 0; i < words.length; i++) {
			if (i == searchTermIndex) continue;
			double result = s.cosineDistance(embeddings[searchTermIndex], embeddings[i]);
//			if (result >= 0.68d && result <= 1.0d ) {
				if (result >= 0.68d ) {
				System.out.println(words[i]);
				System.out.println(result);
			}
			
		}
		
		
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