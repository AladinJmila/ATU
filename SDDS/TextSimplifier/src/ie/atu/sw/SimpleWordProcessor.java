package ie.atu.sw;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SimpleWordProcessor implements WordProcessor {
    private static double tolerance = 0.7;

    @Override
    public String processWord(String word, ConcurrentHashMap<String, double[]> embeddingsMap,
            ConcurrentHashMap<String, double[]> google1000Map, List<Map.Entry<String, double[]>> entries) {

        List<double[]> results = new ArrayList<>();

        String cleanWord = word.replaceAll("^\\p{Punct}+|\\p{Punct}+$", "").toLowerCase().trim();

        if (cleanWord.isEmpty())
            return word;

        if (google1000Map.containsKey(cleanWord)) {
            ConsoleLogger.info("Word '" + cleanWord + "' found in common words list - keeping original");

            return cleanWord;
        }

        if (!embeddingsMap.containsKey(cleanWord)) {
            ConsoleLogger.info("Word '" + cleanWord + "' not found in embeddings dictionary - keeping original");
            return cleanWord;
        }

        for (int j = 0; j < google1000Map.size(); j++) {
            double distance = CosineDistance.getDistance(embeddingsMap.get(
                    cleanWord),
                    entries.get(j).getValue());
            if (distance > tolerance) {
                results.add(new double[] { (double) j, distance });
            }
        }

        if (results.size() > 0) {
            QuickSort.sort(results);
            var bestMatch = entries.get((int) results.get(results.size() - 1)[0]).getKey();
            ConsoleLogger.info("Found simpler alternative for '" + cleanWord + "': '" + bestMatch + "'");
            return bestMatch;
        }

        ConsoleLogger.info("No suitable alternative found for '" + cleanWord + "' - keeping original");
        return cleanWord;
    }

    public void setTolerance(double mewTolerance) {
        if (mewTolerance < 0 || mewTolerance > 1.0) {
            throw new IllegalArgumentException("Tolerance must be between 0 and 1");
        }
        tolerance = mewTolerance;
    }
}
