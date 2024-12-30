package ie.atu.sw;

/**
 * A utility class that provides methods to compute the cosine distance between
 * vectors.
 */
public final class CosineDistance {
	private CosineDistance() {
	}

	/**
	 * Computes the cosine distance between two vectors.
	 * 
	 * @param searchTermVector the first vector, typically representing the search
	 *                         term
	 * @param compareToVector  the second vector, typically representing the vector
	 *                         to compare against the search term
	 * @return the cosine distance between the two vectors, ranging from -1 to 1
	 */
	public static double getDistance(double[] searchTermVector, double[] compareToVector) {
		double searchTermSquaredSum = computeSquaredSum(searchTermVector);

		// return the computed distance
		return computeDotProduct(searchTermVector, compareToVector)
				/ Math.sqrt(searchTermSquaredSum * computeSquaredSum(compareToVector));
	}

	/**
	 * Computes the dot product (scalar product) of two vectors.
	 * 
	 * @param v1 the first vector
	 * @param v2 the second vector
	 * @return the dot product of the two vectors
	 */
	private static double computeDotProduct(double[] v1, double[] v2) {
		double result = 0.0d;

		for (int i = 0; i < v1.length; i++) {
			result += v1[i] * v2[i];
		}

		return result;
	}

	/**
	 * Computes the sum of squares of all elements in a vector.
	 * 
	 * @param array the input vector
	 * @return the sum of squares of all elements in the vector
	 */
	private static double computeSquaredSum(double[] array) {
		double result = 0.0d;

		for (int i = 0; i < array.length; i++) {
			result += Math.pow(array[i], 2);
		}

		return result;
	}
}
