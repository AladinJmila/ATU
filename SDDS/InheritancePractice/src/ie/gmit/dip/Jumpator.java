package ie.gmit.dip;

public interface Jumpator {
	public static final int MIN_HEIGHT = 100;
	public enum Unit {METRIC, IMPERIAL};
	
	public static double getHeightAsMeters(double feet) {
		return 0.0;
	}
	
	public void jump();
	
	default void jump(double height, Unit unit) {
		// some boiler plate functionality
		jump();
	};
}
