package ie.atu.sw;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A concrete implementation of the WordProcessor interface that simplifies
 * words by finding simpler alternatives based on word embeddings and cosine
 * similarity.
 * This processor uses a tolerance threshold to determine acceptable word
 * replacements and maintains a list of common words that should not be
 * simplified.
 */
public class SimpleWordProcessor implements WordProcessor {
    private static double tolerance = 0.7;

    /**
     * {@inheritDoc}
     * <p>
     * This implementation follows these steps:
     * <ol>
     * <li>Cleans the input word by removing punctuation and converting to
     * lowercase</li>
     * <li>Checks if the word is in the common words list (google1000Map)</li>
     * <li>Verifies the word exists in the embeddings dictionary</li>
     * <li>Calculates cosine similarity with potential replacements</li>
     * <li>Returns the most similar common word if found, otherwise returns the
     * original</li>
     * </ol>
     */
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

    /**
     * Sets the similarity tolerance threshold for word replacement.
     * Higher values require closer similarity for word substitution.
     *
     * @param mewTolerance The new tolerance value, must be between 0 and 1
     * @throws IllegalArgumentException if tolerance is not between 0 and 1
     */
    public void setTolerance(double mewTolerance) {
        if (mewTolerance < 0 || mewTolerance > 1.0) {
            throw new IllegalArgumentException("Tolerance must be between 0 and 1");
        }
        tolerance = mewTolerance;
    }
}
