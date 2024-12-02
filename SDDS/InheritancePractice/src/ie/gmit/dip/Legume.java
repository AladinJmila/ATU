package ie.gmit.dip;

public abstract class Legume {
	private String name;
	
	public Legume(String name) {
		this.name = name;
	}
	
	public abstract void grow();
	
	public String getName() {
		return this.name;
	}
}
