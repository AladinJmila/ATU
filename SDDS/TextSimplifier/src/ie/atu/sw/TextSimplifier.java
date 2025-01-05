package ie.atu.sw;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * A class that simplifies text by processing words using word embeddings and
 * Google's 1000 most common words.
 * This class handles concurrent text processing using virtual threads for
 * improved performance.
 */
public class TextSimplifier {
    private WordProcessor processor = new SimpleWordProcessor();
    private ConcurrentHashMap<String, double[]> embeddingsMap;
    private ConcurrentHashMap<String, double[]> google1000Map;
    private String inputFilePath = "";

    /**
     * Constructs a new TextSimplifier with specified input file and word embedding
     * maps.
     *
     * @param inputFilePath the path to the input file to be simplified
     * @param embeddingsMap the map containing word embeddings for semantic analysis
     * @param google1000Map the map containing Google's 1000 most common words
     */
    TextSimplifier(String inputFilePath, ConcurrentHashMap<String, double[]> embeddingsMap,
            ConcurrentHashMap<String, double[]> google1000Map) {
        this.inputFilePath = inputFilePath;
        this.embeddingsMap = embeddingsMap;
        this.google1000Map = google1000Map;
    }

    /**
     * Simplifies the text from the input file using concurrent processing.
     * The method reads the input file line by line, processes each word in the
     * lines concurrently using virtual threads, and generates a simplified version
     * of the text. The simplified text is then written to an output file.
     *
     * @throws Exception if there are errors reading the input file or processing
     *                   the text
     */
    // O(n^3): has 2 level nested iterative operations with the deepest containing
    // O(n) operation: n for each line > n for each word > n for processWord method
    // O(c + n + (n * n * n) + n) -> O(c + 2n + n^3) -> O(n^3)
    public void simplifyText() throws Exception {
        var entries = google1000Map.entrySet().stream().toList(); // O(c) it's fixed to 1000
        var textResults = new ConcurrentSkipListMap<Integer, String>();

        try (var pool = Executors.newVirtualThreadPerTaskExecutor()) {
            ConsoleLogger.info("Processing the input file...");

            var lines = Files.readAllLines(Paths.get(inputFilePath)); // O(n)
            var futures = new ArrayList<Future<?>>();

            // Process lines with pre-assigned indices
            for (int lineIndex = 0; lineIndex < lines.size(); lineIndex++) { // O(n)
                final int currentIndex = lineIndex; // Capture for lambda
                final String line = lines.get(lineIndex);

                futures.add(pool.submit(() -> { // O(n)
                    var words = line.split(" "); // O(n)
                    StringBuilder sb = new StringBuilder();

                    for (int i = 0; i < words.length; i++) { // O(n)
                        var processedWord = processor.processWord(words[i], embeddingsMap, google1000Map, entries); // O(n)
                        sb.append(processedWord).append(" ");
                    }

                    String processedLine = sb.toString().trim(); // O(n)
                    textResults.put(currentIndex, processedLine);
                }));
            }

            // Wait for all futures to complete
            for (Future<?> future : futures) { // O(n)
                future.get();
            }

            pool.shutdown();
            if (!pool.awaitTermination(1, TimeUnit.MINUTES))
                pool.shutdownNow();

            new OutputHandler(inputFilePath).generateFile(textResults.values().stream().toList()); // O(n)
        }
    }
}
