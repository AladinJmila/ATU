package ie.atu.sw;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SimpleWordProcessor implements WordProcessor {
    private final QuickSort quickSort = new QuickSort();

    @Override
    public String processWord(String word, ConcurrentHashMap<String, double[]> embeddingsMap,
            ConcurrentHashMap<String, double[]> google1000Map, List<Map.Entry<String, double[]>> entries) {

        List<double[]> results = new ArrayList<>();

        if (google1000Map.containsKey(word.toLowerCase())) {
            ConsoleLogger.info("Word '" + word + "' found in common words list - keeping original");

            return word;
        }

        if (!embeddingsMap.containsKey(word.toLowerCase())) {
            ConsoleLogger.info("Word '" + word + "' not found in embeddings dictionary - keeping original");
            return word;
        }

        for (int j = 0; j < google1000Map.size(); j++) {
            double distance = CosineDistance.getDistance(embeddingsMap.get(word),
                    entries.get(j).getValue());
            if (distance > 0.7) {
                results.add(new double[] { (double) j, distance });
            }
        }

        if (results.size() > 0) {
            quickSort.sort(results);
            var bestMatch = entries.get((int) results.get(results.size() - 1)[0]).getKey();
            ConsoleLogger.info("Found simpler alternative for '" + word + "': '" + bestMatch + "'");
            return bestMatch;
        }

        ConsoleLogger.info("No suitable alternative found for '" + word + "' - keeping original");
        return word;
    }
}
