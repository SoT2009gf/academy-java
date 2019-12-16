package sk.tsystems.gamestudio.service;

public class GameStudioException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public GameStudioException() {
	}

	public GameStudioException(String message) {
		super(message);
	}

	public GameStudioException(Throwable cause) {
		super(cause);
	}

	public GameStudioException(String message, Throwable cause) {
		super(message, cause);
	}

	public GameStudioException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
