package ie.atu.sw;

import java.util.concurrent.ConcurrentHashMap;

public interface SimpleMapper {
    public ConcurrentHashMap<String, double[]> map(String filePath) throws Exception;
}
