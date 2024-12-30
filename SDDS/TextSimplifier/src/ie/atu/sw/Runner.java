package ie.atu.sw;

/**
 * Entry point class for the Text Simplifier application.
 * This class contains the main method that initializes and starts the
 * application.
 * 
 * @author Alaeddine Jmila
 * @version 1.0
 */
public class Runner {

	/**
	 * The main entry point of the application.
	 * Creates a new MainMenu instance and initiates the menu handling process.
	 *
	 * @param args Command line arguments (not used)
	 * @throws Exception If an error occurs during menu handling
	 */
	public static void main(String[] args) throws Exception {
		new MainMenu().handleMenu();
	}

}