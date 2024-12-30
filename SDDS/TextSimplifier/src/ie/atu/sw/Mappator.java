package ie.atu.sw;

import java.util.concurrent.ConcurrentHashMap;

/**
 * An interface that defines mapping operations for processing data into vector
 * representations.
 * Implementations of this interface should provide thread-safe mapping
 * capabilities.
 */
public interface Mappator {
    /**
     * Maps the contents of a file to a concurrent hash map containing string keys
     * and double array values.
     *
     * @param filePath the path to the file to be processed
     * @return a ConcurrentHashMap containing the mapped data with String keys and
     *         double[] values
     * @throws Exception if an error occurs during the file processing or mapping
     *                   operation
     */
    public ConcurrentHashMap<String, double[]> map(String filePath) throws Exception;
}
