package ma.cirestechnologies.constant;

public class SecurityConstants {
	public static final String[] ROLE = {"USER","ADMIN"};
	public static final String[] PUBLIC_URLS = {"/api/users/generate","/api/users/batch","/h2-console/**","/api/auth","/swagger-ui.html/**","/swagger-ui/**","/v3/api-docs/**"};
	public static final String NO_USER_FOUND_BY_USERNAME = "No user found by username: ";
	public static final String MY_COMPANY = "CIRES TECHNOLOGIES";
	public static final String TOKEN_CANNOT_BE_VERIFIED = "Token cannot be verified";
	public static final String AUTHORITIES = "authorities";
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String OPTIONS_HTTP_METHOD = "OPTIONS";
	public static final String AUTHORIZATION = "Authorization";
	public static final String FORBIDDEN_MESSAGE = "You need to log in to access this page";
	public static final String AUTHORITY_MESSAGE = "Yon don't have the right authority to access this profile";
	public static final String COUNT="The parameter 'count' is required";
	public static final String FILE="The parameter 'file' is required";
	public static final String INCORRECT_CREDENTIALS = "Username / password incorrect. Please try again";
	public static final String NOT_NULL_PARAMETER = "You can't assign 0 or less to this parameter: ";
	public static final String FILE_CONTENT = "Please select a valid file that contains users"; 
}
