package ie.atu.sw;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class EmbeddingsMapper implements Mappator {
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
