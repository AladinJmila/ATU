package ie.atu.sw;

public class Searcher {
	double product;
	public void cosineDistance() {
		System.out.println(product);
		double[] v1 = {1.0, 2.0, 3.0};
		double[] v2 = {4.0, 5.0, 6.0};
		
		
		if (product == 0.0) {
			product = computeProduct(v1, v2);
		}
		
		System.out.println(product);
	}
	
	private double computeProduct(double[] v1, double[] v2) {
		double result = 0.0d;
		
		for (int i = 0; i < v1.length; i++) {
			result += v1[i] * v2[i];
		}
		
		return result;
	}
}
