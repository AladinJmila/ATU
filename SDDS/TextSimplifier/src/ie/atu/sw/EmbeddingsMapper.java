package ie.atu.sw;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Implementation of Mappator that that handles mapping and processing of text
 * embeddings.
 */
public class EmbeddingsMapper implements Mappator {

    /**
     * {@inheritDoc}
     * 
     * This implementation uses virtual threads for concurrent processing of the
     * input file,
     * with a timeout of 1 minute for the complete operation. The method:
     * <ul>
     * <li>Reads the file line by line</li>
     * <li>Splits each line into a word and its embeddings</li>
     * <li>Processes each line concurrently using virtual threads</li>
     * <li>Stores the results in a thread-safe map</li>
     * </ul>
     */
    @Override
    public ConcurrentHashMap<String, double[]> map(String filePath) throws Exception {
        ConcurrentHashMap<String, double[]> embeddingsMap = new ConcurrentHashMap<>();

        try (var pool = Executors.newVirtualThreadPerTaskExecutor()) {
            Files.lines(Paths.get(filePath)).forEach(line -> {
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

        return embeddingsMap;
    }
}
