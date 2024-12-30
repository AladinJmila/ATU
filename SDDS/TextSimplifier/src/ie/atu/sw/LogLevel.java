package ie.atu.sw;

/**
 * Enum representing different log levels with associated messages.
 */
public enum LogLevel {
	INFO("[INFO]"), WARN("[WARNING]"), ERROR("[ERROR]");

	/** The message suffix associated with the log level */
	private final String message;

	/**
	 * Constructs a LogLevel with the specified message suffix.
	 *
	 * @param message The message suffix for this log level
	 */
	LogLevel(String message) {
		this.message = message;
	}

	/**
	 * Returns the message suffix associated with this log level.
	 *
	 * @return The message suffix as a String
	 */
	public String getMessage() {
		return message;
	}
}
