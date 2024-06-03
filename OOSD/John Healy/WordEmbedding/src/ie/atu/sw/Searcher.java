package ie.atu.sw;

public class Searcher {
	private double searchTermSquaredSum;
	public double cosineDistance(double[] searchTermVector, double[] compareToVector) {
		if (searchTermSquaredSum == 0.0d) {
			searchTermSquaredSum = computeSquaredSum(searchTermVector);
		}
		
		return computeDotProduct(searchTermVector, compareToVector) / computeDotProductSquared(searchTermSquaredSum, compareToVector);
	}
	
	private double computeDotProduct(double[] v1, double[] v2) {
		double result = 0.0d;
		
		for (int i = 0; i < v1.length; i++) {
			result += v1[i] * v2[i];
		}
		
		return result;
	}
	
	private double computeDotProductSquared(double searchTermSquaredSum, double[] compareToVector) {
		double result = 0.0d;
		
		for (int i = 0; i < compareToVector.length; i++) {
			result += Math.pow(compareToVector[i], 2);
		}
		
		return Math.sqrt(searchTermSquaredSum * result);
	}
	
	private double computeSquaredSum(double[] array) {
		double result = 0.0d;
		for (int i = 0; i < array.length; i++) {
			result += Math.pow(array[i], 2);
		}
		return result;
	}
}
