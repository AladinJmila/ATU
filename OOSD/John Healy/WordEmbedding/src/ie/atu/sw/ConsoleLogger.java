package ie.atu.sw;

import static java.lang.System.out;

public class ConsoleLogger {
	
	public void cyanBoldTitle (String message) {
		out.println();
		out.print(ConsoleColour.CYAN_BOLD);
		out.println(message);
		out.print(ConsoleColour.WHITE_BOLD);
	}

	// Log informational messages in green
	public void info(String message) {
		out.println("");
		out.print(ConsoleColour.GREEN_BOLD);
		out.println(LogLevel.INFO.getMessage() + " " + message);
		out.print(ConsoleColour.RESET);
	}
	
	public void info(String prefix, String message) {
		out.println("");
		out.print(ConsoleColour.GREEN_BOLD);
		out.println(prefix + LogLevel.INFO.getMessage() + " " + message);
		out.print(ConsoleColour.RESET);
	}
	
	// Log warning messages in yellow
	public void warn(String message) {
		out.println("");
		out.print(ConsoleColour.YELLOW_BOLD);
		out.println(LogLevel.WARN.getMessage() + " " + message);
		out.print(ConsoleColour.RESET);
	}
	
	public void warn(String prefix, String message) {
		out.println("");
		out.print(ConsoleColour.YELLOW_BOLD);
		out.println(prefix + LogLevel.WARN.getMessage() + " " + message);
		out.print(ConsoleColour.RESET);
	}
	
	
	// log error messages in red
	public void error(String message) {
		out.println("");
		out.print(ConsoleColour.RED_BOLD);
		out.println(LogLevel.ERROR.getMessage() + " " + message);
		out.print(ConsoleColour.RESET);
	}
	
	public void error(String prefix, String message) {
		out.println("");
		out.print(ConsoleColour.RED_BOLD);
		out.println(prefix + LogLevel.ERROR.getMessage() + " " + message);
		out.print(ConsoleColour.RESET);
	}
	
}
