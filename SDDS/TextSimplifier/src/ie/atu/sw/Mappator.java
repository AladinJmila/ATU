package ie.atu.sw;

import java.util.concurrent.ConcurrentHashMap;

public interface Mappator {
    public ConcurrentHashMap<String, double[]> map(String filePath) throws Exception;
}
