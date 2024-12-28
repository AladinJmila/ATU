package ie.atu.sw;

import java.util.concurrent.ConcurrentHashMap;

public interface ExtendedMappator {
    public ConcurrentHashMap<String, double[]> map(String filePath, ConcurrentHashMap<String, double[]> map)
            throws Exception;
}
