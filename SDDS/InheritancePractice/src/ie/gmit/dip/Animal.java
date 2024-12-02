package ie.gmit.dip;

 public abstract class Animal {
	private int lifeForce;
	private String name;
	
	 
	public Animal(String name) {
		this.name = name;
		this.lifeForce = 23;
	}

	public void eat() {
		// eat logic
		System.out.println(this.name + " is eating...");
	}
	
	public void move() {
		// move logic
		System.out.println(this.name + " is moving...");
	}
	
	public abstract void sleep();
	
	public String getName() {
		return this.name;
	}
}
