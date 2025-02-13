package ma.epicproject.backend.common.exception;


public class RequiredFieldMissingException extends RuntimeException {

	private String[] params;

	public RequiredFieldMissingException(String message) {
		super(message);
	}

	public RequiredFieldMissingException(String message, String[] params) {
		super(message);
		this.params = params;
	}

	public RequiredFieldMissingException(Throwable cause) {
		super(cause);
	}

	public RequiredFieldMissingException(String message, Throwable cause) {
		super(message, cause);
	}

	public String[] getParams() {
		return this.params;
	}

	public void setParams(String[] params) {
		this.params = params;
	}

}