package ie.atu.sw;

import static java.lang.System.out;

public class ConsoleLogger {
	
	public static final String TAB = "   ";
	
	public void cyanBoldTitle (String message, boolean newLine) {
		out.println();
		out.print(ConsoleColour.CYAN_BOLD);
		if (newLine) {
			out.println(message);			
		} else {
			out.print(message);			
		}
		out.print(ConsoleColour.WHITE_BOLD);
	}
	
	public void cyanBoldTitle (String message) {
		cyanBoldTitle(message, false);
	}

	// Log informational messages in green
	public void info(String prefix, String message) {
		out.println("");
		out.print(ConsoleColour.GREEN_BOLD);
		out.println(prefix + LogLevel.INFO.getMessage() + " " + message);
		out.print(ConsoleColour.RESET);
	}
	
	public void info(String message) {
		info("", message);
	}
	
	// Log warning messages in yellow
	public void warn(String prefix, String message) {
		out.println("");
		out.print(ConsoleColour.YELLOW_BOLD);
		out.println(prefix + LogLevel.WARN.getMessage() + " " + message);
		out.print(ConsoleColour.RESET);
	}
	
	public void warn(String message) {
		warn("", message);
	}
	
	// log error messages in red
	public void error(String prefix, String message) {
		out.println("");
		out.print(ConsoleColour.RED_BOLD);
		out.println(prefix + LogLevel.ERROR.getMessage() + " " + message);
		out.print(ConsoleColour.RESET);
	}
	
	public void error(String message) {
		error("", message);
	}
}
