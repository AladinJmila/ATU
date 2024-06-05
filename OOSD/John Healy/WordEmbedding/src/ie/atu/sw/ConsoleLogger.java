package ie.atu.sw;

import static java.lang.System.out;

public class ConsoleLogger {

	public void log(LogLevel level, String message) {
		switch (level) {
		case INFO -> logInfo(level, message);
		case WARN -> logWarning(level, message);
		case ERROR -> logError(level, message);
		}
	}

	private void logInfo(LogLevel level, String message) {
		out.print(ConsoleColour.GREEN_BOLD);
		out.println("");
		out.println(level.getMessage() + " " + message);
		out.print(ConsoleColour.RESET);
	}
	
	private void logWarning(LogLevel level, String message) {
		out.print(ConsoleColour.YELLOW_BOLD);
		out.println("");
		out.println(level.getMessage() + " " + message);
		out.print(ConsoleColour.RESET);
	}
	
	private void logError(LogLevel level, String message) {
		out.print(ConsoleColour.RED_BOLD);
		out.println("");
		out.println(level.getMessage() + " " + message);
		out.print(ConsoleColour.RESET);
	}
}
