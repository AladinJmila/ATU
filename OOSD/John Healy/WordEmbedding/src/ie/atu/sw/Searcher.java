package ie.atu.sw;

public class Searcher {

	public double cosineDistance(double[] v1, double[] v2) {
//		System.out.println("Product: " + computeProduct(v1, v2));
//		System.out.println("Dot Product: " + computeDotProduct(v1, v2));
		return computeProduct(v1, v2) / computeDotProduct(v1, v2);
	}
	
	private double computeProduct(double[] v1, double[] v2) {
		double result = 0.0d;
		
		for (int i = 0; i < v1.length; i++) {
			result += v1[i] * v2[i];
		}
		
		return result;
	}
	
	private double computeDotProduct(double[] v1, double[] v2) {
		double v1SquareSum = 0.0d;
		double v2SquareSum = 0.0d;
		
		for (int i = 0; i < v1.length; i++) {
			v1SquareSum += Math.pow(v1[i], 2);
			v2SquareSum += Math.pow(v2[i], 2);
		}
		
		return Math.sqrt(v1SquareSum * v2SquareSum);
	}
}
