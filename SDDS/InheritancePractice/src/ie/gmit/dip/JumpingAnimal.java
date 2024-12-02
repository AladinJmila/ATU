package ie.gmit.dip;

public abstract class JumpingAnimal extends Animal implements Jumpator {
	private int maxHeight;
	
	public JumpingAnimal(String name, int maxHeight) {
		super(name);
		this.maxHeight = maxHeight;
	}
	
	public void jump() {
		// jump logic
		System.out.println(super.getName() + " is jumping...");
	}
	
}
