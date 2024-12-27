package ie.atu.sw;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;

public class TextSimplifier {
    CosineDistance cosineDistance = new CosineDistance();
    QuickSort quickSort = new QuickSort();
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

        try (var pool = Executors.newVirtualThreadPerTaskExecutor()) {
            Files.lines(Paths.get(inputFilePath)).forEach(line -> {
                pool.execute(() -> {
                    var words = line.split(" ");
                    StringBuilder sb = new StringBuilder();
                    List<double[]> results = new ArrayList<>();

                    for (int i = 0; i < words.length; i++) {
                        // Array to hold the word index and the cosine distance

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
            });
        }
    }
}
