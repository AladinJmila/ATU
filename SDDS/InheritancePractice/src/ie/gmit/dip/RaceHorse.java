package ie.gmit.dip;

public class RaceHorse extends JumpingAnimal{
	private int speed;
	
	public RaceHorse(String name, int maxHeight, int speed) {
		super(name, maxHeight);
		this.speed = speed;
	}
	
	public void gallop() {
		// gallop logic
		System.out.println(super.getName() + " is galloping...");
	}
	
	@Override
	public void sleep() {
		// custom sleep logic
		System.out.println(super.getName() + " is custom sleeping...");
	}
	
}
