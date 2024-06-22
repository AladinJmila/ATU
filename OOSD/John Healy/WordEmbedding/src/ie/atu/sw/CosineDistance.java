package ie.atu.sw;

public class CosineDistance {
	private double searchTermSquaredSum;
	
	// Compute the cosine distance between two vectors
	public double getDistance(double[] searchTermVector, double[] compareToVector) {
		// compute the search term squared sum only if it's 0.0 to preserve resources if it was already computed once
		if (searchTermSquaredSum == 0.0d) {
			searchTermSquaredSum = computeSquaredSum(searchTermVector);
		}
		
		// return the computed distance
		return computeDotProduct(searchTermVector, compareToVector) / Math.sqrt(searchTermSquaredSum * computeSquaredSum(compareToVector));
	}
	
	// Compute the dot product of two vectors
	private double computeDotProduct(double[] v1, double[] v2) {
		double result = 0.0d;
		
		// Iterate through the elements of the vectors and compute the dot product
		for (int i = 0; i < v1.length; i++) {
			result += v1[i] * v2[i];
		}
		
		// Return the dot product result
		return result;
	}
	
	
	// Compute the squared sum of a vector
	private double computeSquaredSum(double[] array) {
		double result = 0.0d;
		
		// Iterate through the element of the array and compute the squared sum
		for (int i = 0; i < array.length; i++) {
			result += Math.pow(array[i], 2);
		}
		
		// Return the squared sum result
		return result;
	}
}
