package ie.atu.sw;

import static java.lang.System.out;

public class ConsoleLogger {

	// Log a message with a given log level
	public void log(LogLevel level, String message) {
		switch (level) {
		case INFO -> logInfo(level, message);
		case WARN -> logWarning(level, message);
		case ERROR -> logError(level, message);
		}
	}

	// Log informational messages in green
	private void logInfo(LogLevel level, String message) {
		out.print(ConsoleColour.GREEN_BOLD);
		out.println("");
		out.println(level.getMessage() + " " + message);
		out.print(ConsoleColour.RESET);
	}
	
	// Log warning messages in yellow
	private void logWarning(LogLevel level, String message) {
		out.print(ConsoleColour.YELLOW_BOLD);
		out.println("");
		out.println(level.getMessage() + " " + message);
		out.print(ConsoleColour.RESET);
	}
	
	// log error messages in red
	private void logError(LogLevel level, String message) {
		out.print(ConsoleColour.RED_BOLD);
		out.println("");
		out.println(level.getMessage() + " " + message);
		out.print(ConsoleColour.RESET);
	}
}
