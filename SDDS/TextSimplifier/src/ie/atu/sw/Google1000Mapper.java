package ie.atu.sw;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Google1000Mapper implements ExtendedMappator {
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
