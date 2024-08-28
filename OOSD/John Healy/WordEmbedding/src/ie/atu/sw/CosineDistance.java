package ie.atu.sw;

/*
 * Compute the cosine distance between two vectors
 */

public class CosineDistance {
	private double searchTermSquaredSum;

	// Compute the cosine distance between two vectors
	public double getDistance(double[] searchTermVector, double[] compareToVector) {
		// compute the search term squared sum only if it's 0.0 to preserve resources if
		// it was already computed once
		if (searchTermSquaredSum == 0.0d) {
			searchTermSquaredSum = computeSquaredSum(searchTermVector);
		}

		// return the computed distance
		return computeDotProduct(searchTermVector, compareToVector)
				/ Math.sqrt(searchTermSquaredSum * computeSquaredSum(compareToVector));
	}

	// Compute the dot product of two vectors
	private double computeDotProduct(double[] v1, double[] v2) {
		double result = 0.0d;

		for (int i = 0; i < v1.length; i++) {
			result += v1[i] * v2[i];
		}

		return result;
	}

	// Compute the squared sum of a vector
	private double computeSquaredSum(double[] array) {
		double result = 0.0d;

		for (int i = 0; i < array.length; i++) {
			result += Math.pow(array[i], 2);
		}

		return result;
	}
}
