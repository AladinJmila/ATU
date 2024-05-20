package ie.atu.sw;

public enum Course {
	SOFT_DEV 	("Software Development", "Computing"),
	DATA_ANALY	("Data Analytics", "Computing"), 
	WEB_DEV 	("Web Application Development", "Computing"), 
	CHEM 		("Chemistry", "Science"),
	PYHS 		("Physics", "Science"),
	MEDI		("Medicine", "Medicine"),
	ACC			("Accounting", "Commerce");

	private final String cname;
	private final String cfaculty;
	
	Course(String name, String faculty) {
		this.cname = name;
		this.cfaculty = faculty;
	}

	public String getCourseName() {
		return cname;
	}

	public String getFaculty() {
		return cfaculty;
	}
	
	
}
