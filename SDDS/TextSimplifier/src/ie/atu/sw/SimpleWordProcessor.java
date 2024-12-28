package ie.atu.sw;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SimpleWordProcessor implements WordProcessor {
    private final CosineDistance cosineDistance = new CosineDistance();
    private final QuickSort quickSort = new QuickSort();
    private final ConsoleLogger logger = new ConsoleLogger();

    @Override
    public String processWord(String word, ConcurrentHashMap<String, double[]> embeddingsMap,
            ConcurrentHashMap<String, double[]> google1000Map, List<Map.Entry<String, double[]>> entries) {

        List<double[]> results = new ArrayList<>();

        if (google1000Map.containsKey(word.toLowerCase())) {
            logger.info(word + " is in google 1000 -> return word as is");
            return word;
        }

        if (!embeddingsMap.containsKey(word.toLowerCase())) {
            logger.info(word + " -> is not is embeddings -> return word as is");
            return word;
        }

        for (int j = 0; j < google1000Map.size(); j++) {
            double distance = cosineDistance.getDistance(embeddingsMap.get(word),
                    entries.get(j).getValue());
            if (distance > 0.7) {
                results.add(new double[] { (double) j, distance });
            }
        }

        if (results.size() > 0) {
            quickSort.sort(results);
            var bestMatch = entries.get((int) results.get(results.size() - 1)[0]).getKey();
            logger.info("Returning best match: " + bestMatch);
            return bestMatch;
        }

        logger.info(word + " -> no match found -> return word as is");
        return word;
    }
}
