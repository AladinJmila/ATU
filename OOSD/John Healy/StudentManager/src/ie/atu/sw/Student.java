package ie.atu.sw;

import java.time.LocalDate;

public record Student(String sid, String firstname, String lastName, 
		LocalDate dob, Address address, Course course) {

}
