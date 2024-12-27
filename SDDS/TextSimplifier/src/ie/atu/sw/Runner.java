package ie.atu.sw;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Runner {

	public static void main(String[] args) throws Exception {
		String embeddingsFile = "./embeddings.txt";
		String google1000File = "./google-1000.txt";
		String inputFile = "./input.txt";
		ConcurrentHashMap<String, double[]> embeddingsMap = new ConcurrentHashMap<>();
		ConcurrentSkipListMap<String, double[]> google1000Map = new ConcurrentSkipListMap<>();
		CosineDistance cosineDistance = new CosineDistance();
		QuickSort quickSort = new QuickSort();
		// private String outputFile = "./out.txt";

		try (var pool = Executors.newVirtualThreadPerTaskExecutor()) {
			Files.lines(Paths.get(embeddingsFile)).forEach(line -> {
				pool.execute(() -> {
					var elements = line.split(",", 2);
					var embeddingsText = elements[1].split(",");
					var embeddings = new double[embeddingsText.length];

					for (int i = 0; i < embeddings.length; i++) {

						embeddings[i] = Double.parseDouble(embeddingsText[i]);
					}

					embeddingsMap.put(elements[0], embeddings);
				});
			});

			pool.shutdown();
			pool.awaitTermination(1, TimeUnit.MINUTES);
		}

		try (var pool = Executors.newVirtualThreadPerTaskExecutor()) {
			Files.lines(Paths.get(google1000File)).forEach(word -> {
				pool.execute(() -> {
					google1000Map.put(word, embeddingsMap.get(word));
				});
			});

			pool.shutdown();
			pool.awaitTermination(1, TimeUnit.MINUTES);
		}

		var entries = google1000Map.entrySet().stream().toList();

		try (var pool = Executors.newVirtualThreadPerTaskExecutor()) {
			Files.lines(Paths.get(inputFile)).forEach(line -> {
				pool.execute(() -> {
					var words = line.split(" ");
					StringBuilder sb = new StringBuilder();
					List<double[]> results = new ArrayList<>();

					for (int i = 0; i < words.length; i++) {
						// Array to hold the word index and the cosine distance
						// double[][] result = new double[FileProcessor.WORDS_COUNT - 1][2];
						if (google1000Map.containsKey(words[i].toLowerCase())) {
							// System.out.println(words[i] + " is in google 1000 -> return word as is");
							sb.append(words[i] + " ");
							continue;
						}
						if (!embeddingsMap.containsKey(words[i].toLowerCase())) {
							System.out.println(words[i] + " -> is not is embeddings -> return word as is");
							sb.append(words[i] + " ");
							continue;
						}

						for (int j = 0; j < google1000Map.size(); j++) {
							double distance = cosineDistance.getDistance(embeddingsMap.get(words[i]),
									entries.get(j).getValue());
							if (distance > 0.7) {
								results.add(new double[] { (double) j, distance });
							}
						}

						if (results.size() > 0) {
							System.out.println(words[i]);
							System.out.println(results.size());
							quickSort.sort(results);
							var bestMatch = entries.get((int) results.get(results.size() - 1)[0]).getKey();
							System.out.println(bestMatch);
							System.out.println(Arrays.toString(results.get(results.size() - 1)));
							sb.append(bestMatch + " ");
						} else {
							sb.append(words[i] + " ");
						}
					}

					System.out.println(sb.toString());
				});
				// System.out.println(line);
			});
		}

		// System.out.println(embeddingsMap.size());
		// System.out.println(Arrays.toString(embeddingsMap.get("this")));
		// System.out.println(google1000Map.size());
		// System.out.println(Arrays.toString(google1000Map.get("this")));

		// // You should put the following code into a menu or Menu class
		// System.out.println(ConsoleColour.WHITE);
		// System.out.println("************************************************************");
		// System.out.println("* ATU - Dept. of Computer Science & Applied Physics *");
		// System.out.println("* *");
		// System.out.println("* Virtual Threaded Text Simplifier *");
		// System.out.println("* *");
		// System.out.println("************************************************************");
		// System.out.println("(1) Specify Embeddings File");
		// System.out.println("(2) Specify Google 1000 File");
		// System.out.println("(3) Specify an Output File (default: ./out.txt)");
		// System.out.println("(4) Execute, Analyse and Report");
		// System.out.println("(5) Optional Extras...");
		// System.out.println("(?) Quit");

		// // Output a menu of options and solicit text from the user
		// System.out.print(ConsoleColour.BLACK_BOLD_BRIGHT);
		// System.out.print("Select Option [1-4]>");
		// System.out.println();

		// // You may want to include a progress meter in you assignment!
		// System.out.print(ConsoleColour.YELLOW); // Change the colour of the console
		// text
		// int size = 100; // The size of the meter. 100 equates to 100%
		// for (int i = 0; i < size; i++) { // The loop equates to a sequence of
		// processing steps
		// printProgress(i + 1, size); // After each (some) steps, update the progress
		// meter
		// Thread.sleep(10); // Slows things down so the animation is visible
		// }
	}

	/*
	 * Terminal Progress Meter
	 * -----------------------
	 * You might find the progress meter below useful. The progress effect
	 * works best if you call this method from inside a loop and do not call
	 * System.out.println(....) until the progress meter is finished.
	 * 
	 * Please note the following carefully:
	 * 
	 * 1) The progress meter will NOT work in the Eclipse console, but will
	 * work on Windows (DOS), Mac and Linux terminals.
	 * 
	 * 2) The meter works by using the line feed character "\r" to return to
	 * the start of the current line and writes out the updated progress
	 * over the existing information. If you output any text between
	 * calling this method, i.e. System.out.println(....), then the next
	 * call to the progress meter will output the status to the next line.
	 * 
	 * 3) If the variable size is greater than the terminal width, a new line
	 * escape character "\n" will be automatically added and the meter won't
	 * work properly.
	 * 
	 * 
	 */
	public static void printProgress(int index, int total) {
		if (index > total)
			return; // Out of range
		int size = 50; // Must be less than console width
		char done = '█'; // Change to whatever you like.
		char todo = '░'; // Change to whatever you like.

		// Compute basic metrics for the meter
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

		// Once the meter reaches its max, move to a new line.
		if (done == total)
			System.out.println("\n");
	}
}