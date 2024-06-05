package ie.atu.sw;

public enum LogLevel {
	INFO("[INFO]"), WARN("[WARNING]"), ERROR("[ERROR]");

	private final String message;

	LogLevel(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
