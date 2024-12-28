package ie.atu.sw;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class TextSimplifier {
    private CosineDistance cosineDistance = new CosineDistance();
    private QuickSort quickSort = new QuickSort();
    private ConsoleLogger logger = new ConsoleLogger();
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
            logger.info("Processing the input file...");

            Files.lines(Paths.get(inputFilePath)).forEach(line -> {
                pool.execute(() -> {
                    var words = line.split(" ");
                    StringBuilder sb = new StringBuilder();
                    List<double[]> results = new ArrayList<>();

                    for (int i = 0; i < words.length; i++) {

                        if (google1000Map.containsKey(words[i].toLowerCase())) {
                            logger.info(words[i] + " is in google 1000 -> return word as is");
                            sb.append(words[i] + " ");
                            continue;
                        }
                        if (!embeddingsMap.containsKey(words[i].toLowerCase())) {
                            logger.info(words[i] + " -> is not is embeddings -> return word as is");
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
                            quickSort.sort(results);
                            var bestMatch = entries.get((int) results.get(results.size() - 1)[0]).getKey();
                            sb.append(bestMatch + " ");
                        } else {
                            logger.info(words[i] + " -> no match found -> return word as is");
                            sb.append(words[i] + " ");
                        }
                    }

                    textResults.put(index.get(), sb.toString());
                    index.incrementAndGet();
                });
            });

            pool.shutdown();
            pool.awaitTermination(1, TimeUnit.MINUTES);

            new OutputHandler(inputFilePath).generateOutputFile(textResults.values().stream().toList());
        }
    }
}
