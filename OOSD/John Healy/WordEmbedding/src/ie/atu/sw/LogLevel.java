package ie.atu.sw;

/*
 * Enum representing different log levels with associated messages.
 */

public enum LogLevel {
	INFO("[INFO]"), WARN("[WARNING]"), ERROR("[ERROR]");

	// The message associated with the log level
	private final String message;

	LogLevel(String message) {
		this.message = message;
	}

	// Get the message associated with the log level
	public String getMessage() {
		return message;
	}
}
