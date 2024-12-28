package ie.atu.sw;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public interface WordProcessor {
    public String processWord(String word, ConcurrentHashMap<String, double[]> embeddingsMap,
            ConcurrentHashMap<String, double[]> google1000Map, List<Map.Entry<String, double[]>> entries);
}
