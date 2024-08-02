package ie.atu.sw;

/*
 * Enum representing different log levels with associated messages.
 */

public enum LogLevel {
	// Enum constants for different log levels, each with a specific message
	INFO("[INFO]"), WARN("[WARNING]"), ERROR("[ERROR]");

	// The message associated with the log level
	private final String message;

	// Constructor to initialize the log level with a message
	LogLevel(String message) {
		this.message = message;
	}

	// Get the message associated with the log level
	public String getMessage() {
		return message;
	}
}
