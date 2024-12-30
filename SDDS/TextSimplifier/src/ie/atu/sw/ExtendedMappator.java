package ie.atu.sw;

import java.util.concurrent.ConcurrentHashMap;

/**
 * An interface that defines mapping operations for processing data into vector
 * representations.
 * Implementations of this interface should provide thread-safe mapping
 * capabilities.
 */
public interface ExtendedMappator {

    /**
     * Maps data from a file into a concurrent hash map containing vector
     * representations.
     *
     * @param filePath the path to the file to be processed
     * @param map      the concurrent hash map to retreive values from
     * @return a ConcurrentHashMap containing the mapped data with string keys and
     *         double array values
     * @throws Exception if an error occurs during the mapping process
     */
    public ConcurrentHashMap<String, double[]> map(String filePath, ConcurrentHashMap<String, double[]> map)
            throws Exception;
}
