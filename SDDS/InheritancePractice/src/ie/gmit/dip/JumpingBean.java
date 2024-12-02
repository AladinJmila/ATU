package ie.gmit.dip;

public class JumpingBean extends Bean implements Jumpator{

	public JumpingBean(String name) {
		super(name);
	}
	
	@Override
	public void jump() {
		// Custom jump logic
		System.out.println(super.getName() + " is custom jumping...");
	}
}
