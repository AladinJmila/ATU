package ie.atu.sw;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class TextSimplifier {
    private WordProcessor processor = new SimpleWordProcessor();
    private ConcurrentHashMap<String, double[]> embeddingsMap;
    private ConcurrentHashMap<String, double[]> google1000Map;
    private String inputFilePath = "";

    TextSimplifier(String inputFilePath, ConcurrentHashMap<String, double[]> embeddingsMap,
            ConcurrentHashMap<String, double[]> google1000Map) {
        this.inputFilePath = inputFilePath;
        this.embeddingsMap = embeddingsMap;
        this.google1000Map = google1000Map;
    }

    public void simplifyText() throws Exception {
        var entries = google1000Map.entrySet().stream().toList();
        var textResults = new ConcurrentSkipListMap<Integer, String>();
        AtomicInteger index = new AtomicInteger(0);

        try (var pool = Executors.newVirtualThreadPerTaskExecutor()) {
            ConsoleLogger.info("Processing the input file...");

            Files.lines(Paths.get(inputFilePath)).forEach(line -> {
                pool.execute(() -> {
                    var words = line.split(" ");
                    StringBuilder sb = new StringBuilder();

                    for (int i = 0; i < words.length; i++) {
                        var processedWord = processor.processWord(words[i], embeddingsMap, google1000Map, entries);
                        sb.append(processedWord + " ");
                    }

                    textResults.put(index.get(), sb.toString());
                    index.incrementAndGet();
                });
            });

            pool.shutdown();
            pool.awaitTermination(1, TimeUnit.MINUTES);

            new OutputHandler(inputFilePath).generateFile(textResults.values().stream().toList().reversed());
        }
    }
}
