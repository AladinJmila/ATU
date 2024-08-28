package ie.atu.sw;

/*
 * The Runner class serves as the entry point of the application,
 * initializing and launching the main menu.
 */

public class Runner {

	public static void main(String[] args) throws Exception {
		MainMenu mainMenu = new MainMenu();
		mainMenu.init();
	}

}