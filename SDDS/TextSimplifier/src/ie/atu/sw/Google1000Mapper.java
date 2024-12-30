package ie.atu.sw;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Implementation of ExtendedMappator that processes Google's 1000 most common
 * words using virtual threads for concurrent processing.
 */
public class Google1000Mapper implements ExtendedMappator {
    /**
     * {@inheritDoc}
     * 
     * This implementation uses virtual threads for concurrent processing of the
     * input file,
     * with a timeout of 1 minute for the complete operation.
     */
    @Override
    public ConcurrentHashMap<String, double[]> map(String filePath, ConcurrentHashMap<String, double[]> map)
            throws Exception {
        ConcurrentHashMap<String, double[]> google1000Map = new ConcurrentHashMap<>();

        try (var pool = Executors.newVirtualThreadPerTaskExecutor()) {
            Files.lines(Paths.get(filePath)).forEach(word -> {
                pool.execute(() -> {
                    google1000Map.put(word, map.get(word));
                });
            });

            pool.shutdown();
            pool.awaitTermination(1, TimeUnit.MINUTES);
        }

        return google1000Map;
    }
}
