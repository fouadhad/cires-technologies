package ma.cirestechnologies.model;

public class HttpResponseWithMessage {
    private String message;
	
	public HttpResponseWithMessage() {
		super();
	}

	public HttpResponseWithMessage(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
