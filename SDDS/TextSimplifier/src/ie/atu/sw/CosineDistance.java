package ie.atu.sw;

/*
 * Compute the cosine distance between two vectors
 */

public final class CosineDistance {
	private CosineDistance() {
	}

	// Compute the cosine distance between two vectors
	public static double getDistance(double[] searchTermVector, double[] compareToVector) {
		// compute the search term squared sum only if it's 0.0 to preserve resources if
		// it was already computed once
		double searchTermSquaredSum = computeSquaredSum(searchTermVector);

		// return the computed distance
		return computeDotProduct(searchTermVector, compareToVector)
				/ Math.sqrt(searchTermSquaredSum * computeSquaredSum(compareToVector));
	}

	// Compute the dot product of two vectors
	private static double computeDotProduct(double[] v1, double[] v2) {
		double result = 0.0d;

		for (int i = 0; i < v1.length; i++) {
			result += v1[i] * v2[i];
		}

		return result;
	}

	// Compute the squared sum of a vector
	private static double computeSquaredSum(double[] array) {
		double result = 0.0d;

		for (int i = 0; i < array.length; i++) {
			result += Math.pow(array[i], 2);
		}

		return result;
	}
}
