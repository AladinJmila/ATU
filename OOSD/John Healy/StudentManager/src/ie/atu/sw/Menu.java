package ie.atu.sw;

import static java.lang.System.out;
import java.util.Scanner;

public class Menu {
	private Scanner s;
	private boolean keepRunning = true;

	public Menu() {
		s = new Scanner(System.in);
	}
	
	public void start() {
		while(keepRunning) {
			showOptions();
			
			
		}
	}

	private void add() {

	}

	private void delete() {

	}

	private void findByID() {

	}

	private void findStudentByFirstname() {

	}

	private void getTotal() {

	}

	private void showOptions() {
		out.print("*************************************");
		out.print("******   Student Manager 1.0   ******");
		out.print("(1) Add a Student");
		out.print("(2) Delete a Student");
		out.print("(3) Find Student by ID");
		out.print("(4) Find Students by Firstname");
		out.print("(5) Get Total Student Number");
		out.print("(6) Quit");
		out.print("Select an Option [1-6]>");

	}
}
