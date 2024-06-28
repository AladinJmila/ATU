package ie.atu.sw;

import static java.lang.System.out;

public class ConsoleLogger {
	private String prefix = "";

	// Log a message with a given log level
	public void log(LogLevel level, String message) {
		switch (level) {
		case INFO -> logInfo(level, message);
//		case WARN -> logWarning(level, message);
		case ERROR -> logError(level, message);
		}
	}
	
	public void log(String prefix, LogLevel level, String message) {
		this.prefix = prefix;
		switch (level) {
		case INFO -> logInfo(level, message);
//		case WARN -> logWarning(level, message);
		case ERROR -> logError(level, message);
		}
	}
	
	public void cyanBoldTitle (String message) {
		out.println();
		out.print(ConsoleColour.CYAN_BOLD);
		out.println(message);
		out.print(ConsoleColour.WHITE_BOLD);
	}

	// Log informational messages in green
	private void logInfo(LogLevel level, String message) {
		out.print(ConsoleColour.GREEN_BOLD);
		out.println("");
		out.println(prefix + level.getMessage() + " " + message);
		out.print(ConsoleColour.RESET);
	}
	
	// Log warning messages in yellow
	public void warn(String message) {
		out.print(ConsoleColour.YELLOW_BOLD);
		out.println("");
		out.println(prefix + LogLevel.WARN.getMessage() + " " + message);
		out.print(ConsoleColour.RESET);
	}
	
	public void warn(String prefix, String message) {
		this.prefix = prefix;
		out.print(ConsoleColour.YELLOW_BOLD);
		out.println("");
		out.println(prefix + LogLevel.WARN.getMessage() + " " + message);
		out.print(ConsoleColour.RESET);
	}
	
	
	// log error messages in red
	private void logError(LogLevel level, String message) {
		out.print(ConsoleColour.RED_BOLD);
		out.println("");
		out.println(prefix + level.getMessage() + " " + message);
		out.print(ConsoleColour.RESET);
	}
	
}
