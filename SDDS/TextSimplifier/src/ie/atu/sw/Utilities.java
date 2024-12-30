package ie.atu.sw;

/**
 * A utility class providing helper methods for file operations and console
 * output.
 */
public final class Utilities {
	private Utilities() {
	}

	/**
	 * Launches a file using the default system application based on the operating
	 * system.
	 * Supports Windows, MacOS, and Linux-based systems.
	 *
	 * @param filePath the path to the file to be opened
	 */
	public static void launchFile(String filePath) {
		String os = System.getProperty("os.name").toLowerCase();
		Runtime runtime = Runtime.getRuntime();

		try {
			if (os.contains("win")) {
				runtime.exec(new String[] { "cmd.exe", "/c", "start", filePath });
			} else if (os.contains("mac")) {
				runtime.exec(new String[] { "open", filePath });
			} else if (os.contains("nix") || os.contains("nux")) {
				runtime.exec(new String[] { "xdg-open", filePath });
			} else {
				System.out.print(ConsoleColour.YELLOW_BOLD);
				System.out.println("[WARNING] Unsupported operating system: " + os);
				System.out.println("Please open the file manually.");
				System.out.print(ConsoleColour.RESET);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * Displays a progress bar in the console showing the completion status.
	 * The progress bar uses Unicode block characters to create a visual
	 * representation
	 * of the progress.
	 *
	 * @param index the current progress value
	 * @param total the total value representing 100% completion
	 */
	public static void printProgress(int index, int total) {
		if (index > total)
			return; // Out of range
		int size = 50; // Must be less than console width
		char done = '█'; // Change to whatever you like.
		char todo = '░'; // Change to whatever you like.

		// Compute basic metrics for the meter
		int complete = (100 * index) / total;
		int completeLen = size * complete / 100;

		/*
		 * A StringBuilder should be used for string concatenation inside a loop.
		 * However, as the number of loop iterations is small, using the "+" operator
		 * may be more efficient as the instructions can be optimized by the compiler.
		 * Either way, the performance overhead will be marginal.
		 */
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for (int i = 0; i < size; i++) {
			sb.append((i < completeLen) ? done : todo);
		}

		/*
		 * The line feed escape character "\r" returns the cursor to the start of the
		 * current line. Calling print(...) overwrites the existing line and creates the
		 * illusion of an animation.
		 */
		System.out.print("\r" + sb + "] " + complete + "%");

		// Once the meter reaches its max, move to a new line.
		if (done == total)
			System.out.println("\n");
	}
}
