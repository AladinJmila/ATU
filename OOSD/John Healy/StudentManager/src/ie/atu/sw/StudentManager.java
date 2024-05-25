package ie.atu.sw;

import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;

// to package the java app, navigate into the bin directory of the project
// then run the following command:
// jar -cf nameOfFinalOutput.jar *
// cf stands for Create File and Jar is the jar where we put the coffee beans

// To run the deployed package, run the following:
// For linux
// java -cp .:./smanager.jar ie.atu.sw.Runner
// cp stands for Class Path
// For windows 
// it's practically the same except the path syntax is different
// To be safe just provide the full path to the package and if it contains spaces
// just wrap it in double quotes

public class StudentManager {
	private static final int INITIAL_CAPACITY = 10;
	private Student[] student = null;
	
	
	
	public StudentManager() {
		student = new Student[INITIAL_CAPACITY];
		init();
	}

	public void add(Student s) {
		for (int i = 0; i < student.length; i++) {
			if (student[i] == null) {
				student[i] = s;
				return;
			}
		}
		
		int index = getExpandedIndex();
		student[index] = s;
		
	}
	
	private int getExpandedIndex() {
		Student[] temp = new Student[student.length * 2];
		
		for(int i = 0; i < student.length; i++) {
			temp[i] = student[i];
		}
		
		int index = student.length;
		student = temp;
		
		return index;
	}
	
	public boolean delete(String sid) {
		for (int i = 0; i < student.length; i++) {
			if(student[i] != null && student[i].sid().equals(sid)) {
				student[i] = null;
				return true;
			}
		}
		return false;
	}
	
	public Student getStudentByID(String sid) {
		for (int i = 0; i < student.length; i++) {
			if(student[i] != null && student[i].sid().equals(sid)) {
				return student[i];
			}
		}
		return null;
	}
	
	public Student[] getStudentsByFirstname(String firstname) {
		// find the number of matches
		int matches = 0;
		for (int i = 0; i < student.length; i++) {
			if(student[i] != null && student[i].firstname().equals(firstname)) {
				matches++;
			}
		}
		
		// create array of length equals to matches
		Student[] temp = new Student[matches];
		
		// copy all matches to the new array
		int index = 0;
		for (int i = 0; i < student.length; i++) {
			if(student[i] != null && student[i].firstname().equals(firstname)) {
				temp[index++] = student[i];
			}
		}
		
		return temp;
	}
	
	public int size() {
		int total = 0;
		for(int i = 0; i < student.length; i++) {
			if(student[i] != null) total++;
		}
		return total;
	}
	
	private void init() {
		ThreadLocalRandom rand = ThreadLocalRandom.current();
		
		String[] fnames = {"Joe", "Jane", "Anne", "Pat"};
		String[] surnames = {"Smith", "Murphy", "Burke", "O'Brien"};
		Course[] courses = Course.values();
		int max = 100_000;
		long start = System.currentTimeMillis();
		
		for (int i = 0; i < max; i++) {
			Student rs = new Student("G00" + i, 
					fnames[rand.nextInt(0, fnames.length)], 
					surnames[rand.nextInt(0, surnames.length)], 
					LocalDate.now(),
					new Address("Galway"),
					courses[rand.nextInt(0, courses.length)]
			);
			add(rs);
		}
		
		System.out.println("Time (ms): " + (System.currentTimeMillis() - start));
	}
}
