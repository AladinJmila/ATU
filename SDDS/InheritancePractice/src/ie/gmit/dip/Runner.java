package ie.gmit.dip;

public class Runner {

	public static void main(String[] args) {
		Kangaroo roo = new Kangaroo("Kanga", 22);
		RaceHorse orse = new RaceHorse("Rac", 22, 55);
		
		roo.eat();
		roo.move();
		roo.sleep();
		roo.jump();
		System.out.println();
		orse.eat();
		orse.move();
		orse.sleep();
		orse.jump();
		orse.gallop();
		System.out.println();
		
		
		Pea pea = new Pea("Pea", 3);
		Bean bean = new Bean("Bean");
		var jbean = new JumpingBean("Jbean");
		
		pea.grow();
		pea.mush();
		System.out.println();
		bean.grow();
		System.out.println();
		jbean.grow();
		jbean.jump();
		System.out.println();
	}
}
