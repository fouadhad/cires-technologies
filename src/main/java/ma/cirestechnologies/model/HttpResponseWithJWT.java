package ma.cirestechnologies.model;

public class HttpResponseWithJWT {
	private String accessToken;

	public HttpResponseWithJWT() {
		super();
	}

	public HttpResponseWithJWT(String accessToken) {
		super();
		this.accessToken = accessToken;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	
	
}
