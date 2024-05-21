package ie.atu.sw;

public class Runner {
	public static void main(String[] args) {
		StudentManager sm = new StudentManager();
		Student s = null;
		
		sm.add(s);
		sm.delete(s.sid());
	}
}
