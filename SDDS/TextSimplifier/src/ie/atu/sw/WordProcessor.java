package ie.atu.sw;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * An interface that defines word processing operations for text simplification.
 * Implementations of this interface should provide thread-safe mapping
 * capabilities.
 */
public interface WordProcessor {
    /**
     * Processes a single word using provided word embeddings and reference data.
     *
     * @param word          The input word to be processed
     * @param embeddingsMap A concurrent hash map containing word embeddings as
     *                      double arrays
     * @param google1000Map A concurrent hash map containing Google's 1000 most
     *                      common words and their embeddings
     * @param entries       A list of map entries containing reference word
     *                      embeddings
     * @return The processed word or its simplified equivalent
     */
    public String processWord(String word, ConcurrentHashMap<String, double[]> embeddingsMap,
            ConcurrentHashMap<String, double[]> google1000Map, List<Map.Entry<String, double[]>> entries);
}
