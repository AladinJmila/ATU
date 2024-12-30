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
    public void simplifyText() throws Exception {
        var entries = google1000Map.entrySet().stream().toList();
        var textResults = new ConcurrentSkipListMap<Integer, String>();

        try (var pool = Executors.newVirtualThreadPerTaskExecutor()) {
            ConsoleLogger.info("Processing the input file...");

            var lines = Files.readAllLines(Paths.get(inputFilePath));
            var futures = new ArrayList<Future<?>>();

            // Process lines with pre-assigned indices
            for (int lineIndex = 0; lineIndex < lines.size(); lineIndex++) {
                final int currentIndex = lineIndex; // Capture for lambda
                final String line = lines.get(lineIndex);

                futures.add(pool.submit(() -> {
                    var words = line.split(" ");
                    StringBuilder sb = new StringBuilder();

                    for (int i = 0; i < words.length; i++) {
                        var processedWord = processor.processWord(words[i], embeddingsMap, google1000Map, entries);
                        sb.append(processedWord).append(" ");
                    }

                    String processedLine = sb.toString().trim();
                    textResults.put(currentIndex, processedLine);
                }));
            }

            // Wait for all futures to complete
            for (Future<?> future : futures) {
                future.get();
            }

            pool.shutdown();
            if (!pool.awaitTermination(1, TimeUnit.MINUTES))
                pool.shutdownNow();

            new OutputHandler(inputFilePath).generateFile(textResults.values().stream().toList());
        }
    }
}
