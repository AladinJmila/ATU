package ie.gmit.dip;

public class Bean extends Legume{
	private String name;
	
	public Bean(String name) {
		super(name);
		this.name = "Local name: " + name;
	}
	
	@Override
	public void grow() {
		// Custom grow logic
		System.out.println(super.getName() + " is custom growing...");
	}
}
