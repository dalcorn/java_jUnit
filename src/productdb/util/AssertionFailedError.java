package productdb.util;

public class AssertionFailedError extends AssertionError {

	private static final long serialVersionUID= 1L;

	public AssertionFailedError() {
	}

	public AssertionFailedError(String message) {
		super(message);
	}
}
