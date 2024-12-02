package ie.gmit.dip;

public class Kangaroo extends JumpingAnimal {
	private Pouch pouch;
	
	public Kangaroo(String name, int maxHeight) {
		super(name, maxHeight);
		this.pouch = new Pouch();
	}
	
	@Override
	public void jump() {
		// custom jump logic
		System.out.println(super.getName() + " is custom jumping...");
		
	}
	
	@Override
	public void sleep() {
		// custom sleep logic
		System.out.println(super.getName() + " is custom sleeping...");
	}
	
	
	private class Pouch {
		
	}
}
