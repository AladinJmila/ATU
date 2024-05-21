package ie.atu.sw;

public class StudentManager {
	private static final int INITIAL_CAPACITY = 10;
	private Student[] student = null;
	
	
	
	public StudentManager() {
		student = new Student[INITIAL_CAPACITY];
	}

	public void add(Student s) {
		for (int i = 0; i < student.length; i++) {
			if (student[i] == null) {
				student[i] = s;
			}
		}
		
	}
	
	public boolean delete(String sid) {
		return false;
	}
	
	public Student getStudentByID(String sid) {
		return null;
	}
	
	public Student[] getStudentsByFirstname(String firstname) {
		return null;
	}
	
	public int size() {
		return 0;
	}
}
