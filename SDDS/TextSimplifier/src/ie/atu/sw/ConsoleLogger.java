package ie.atu.sw;

import static java.lang.System.out;

/*
 * Handles various styled logging operations
 */

public abstract class ConsoleLogger {
	public static final String TAB = "   ";

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

	public static void cyanBoldTitle(String message) {
		cyanBoldTitle(message, false);
	}

	// Log informational messages in green
	public static void info(String prefix, String message) {
		out.println("");
		out.print(ConsoleColour.GREEN_BOLD);
		out.println(prefix + LogLevel.INFO.getMessage() + " " + message);
		out.print(ConsoleColour.RESET);
	}

	public static void info(String message) {
		info("", message);
	}

	// Log warning messages in yellow
	public static void warn(String prefix, String message) {
		out.println("");
		out.print(ConsoleColour.YELLOW_BOLD);
		out.println(prefix + LogLevel.WARN.getMessage() + " " + message);
		out.print(ConsoleColour.RESET);
	}

	public static void warn(String message) {
		warn("", message);
	}

	// log error messages in red
	public static void error(String prefix, String message) {
		out.println("");
		out.print(ConsoleColour.RED_BOLD);
		out.println(prefix + LogLevel.ERROR.getMessage() + " " + message);
		out.print(ConsoleColour.RESET);
	}

	public static void error(String message) {
		error("", message);
	}
}
