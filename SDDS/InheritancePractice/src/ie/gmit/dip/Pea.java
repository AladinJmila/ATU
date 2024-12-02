package ie.gmit.dip;

public class Pea extends Legume {
	private int mushiness;
	
	public Pea (String name, int mushinesse) {
		super(name);
		this.mushiness = mushiness;
	}
	
	@Override
	public void grow() {
		// custom grow logic
		System.out.println(super.getName() + " is custom growing...");
	}
	
	public void mush() {
		// mush logic
		System.out.println(super.getName() + " is mushing...");
	}
}
