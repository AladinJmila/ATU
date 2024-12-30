package ie.atu.sw;

import static java.lang.System.out;

/**
 * A utitliy class for logging messages to the console with color and formatting
 */

public final class ConsoleLogger {
	public static final String TAB = "   ";

	private ConsoleLogger() {
	}

	/**
	 * Logs a message and a new line using a custom color theme picked specifically
	 * for this project.
	 * 
	 * @param message
	 * @param newLine
	 */
	public static void cyanBoldTitle(String message, boolean newLine) {
		out.println();
		out.print(ConsoleColour.CYAN_BOLD);
		if (newLine) {
			out.println(message);
		} else {
			out.print(message);
		}
		out.print(ConsoleColour.WHITE_BOLD);
	}

	/**
	 * Logs a message using a custom color theme picked specifically
	 * for this project.
	 * 
	 * @param message
	 */
	public static void cyanBoldTitle(String message) {
		cyanBoldTitle(message, false);
	}

	/**
	 * Logs an informational message in green with a prefix to the console
	 * 
	 * @param prefix
	 * @param message
	 */
	public static void info(String prefix, String message) {
		out.println("");
		out.print(ConsoleColour.GREEN_BOLD);
		out.println(prefix + LogLevel.INFO.getMessage() + " " + message);
		out.print(ConsoleColour.RESET);
	}

	/**
	 * Logs an informational message in green to the console
	 * 
	 * @param message
	 */
	public static void info(String message) {
		info("", message);
	}

	/**
	 * Logs a warning message in yellow with a prefix to the console
	 * 
	 * @param prefix
	 * @param message
	 */
	public static void warn(String prefix, String message) {
		out.println("");
		out.print(ConsoleColour.YELLOW_BOLD);
		out.println(prefix + LogLevel.WARN.getMessage() + " " + message);
		out.print(ConsoleColour.RESET);
	}

	/**
	 * Logs a warning message in yellow to the console
	 * 
	 * @param message
	 */
	public static void warn(String message) {
		warn("", message);
	}

	/**
	 * Logs an error message in red with a prefix to the console
	 * 
	 * @param prefix
	 * @param message
	 */
	public static void error(String prefix, String message) {
		out.println("");
		out.print(ConsoleColour.RED_BOLD);
		out.println(prefix + LogLevel.ERROR.getMessage() + " " + message);
		out.print(ConsoleColour.RESET);
	}

	/**
	 * Logs an error message in red to the console
	 * 
	 * @param message
	 */
	public static void error(String message) {
		error("", message);
	}
}
