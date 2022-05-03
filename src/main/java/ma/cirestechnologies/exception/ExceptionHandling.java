package ma.cirestechnologies.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import ma.cirestechnologies.constant.SecurityConstants;
import ma.cirestechnologies.exception.model.AuthorityNotFoundException;
import ma.cirestechnologies.exception.model.ParameterNullException;
import ma.cirestechnologies.model.HttpResponseWithMessage;

@RestControllerAdvice
public class ExceptionHandling {
	
	@ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<HttpResponseWithMessage> missingRequestParameter(MissingServletRequestParameterException exception) {
        return createHttpResponse(HttpStatus.BAD_REQUEST, SecurityConstants.COUNT);
    }
	
	@ExceptionHandler(MissingServletRequestPartException.class)
    public ResponseEntity<HttpResponseWithMessage> missingRequestPart(MissingServletRequestPartException exception) {
        return createHttpResponse(HttpStatus.BAD_REQUEST, SecurityConstants.FILE);
    }
	
	@ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<HttpResponseWithMessage> badCredentialsException() {
        return createHttpResponse(HttpStatus.BAD_REQUEST, SecurityConstants.INCORRECT_CREDENTIALS);
    }
	
	@ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<HttpResponseWithMessage> usernameNotFoundException(UsernameNotFoundException exception) {
        return createHttpResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
    }
	
	@ExceptionHandler(AuthorityNotFoundException.class)
    public ResponseEntity<HttpResponseWithMessage> authorityNotFoundException(AuthorityNotFoundException exception) {
        return createHttpResponse(HttpStatus.UNAUTHORIZED, exception.getMessage());
    }
	
	@ExceptionHandler(ParameterNullException.class)
    public ResponseEntity<HttpResponseWithMessage> parameterNullException(ParameterNullException exception) {
        return createHttpResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
    }
	
	
	@ExceptionHandler(NullPointerException.class)
    public ResponseEntity<HttpResponseWithMessage> nullPointerException(NullPointerException exception) {
        return createHttpResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
    }
	
	private ResponseEntity<HttpResponseWithMessage> createHttpResponse(HttpStatus httpStatus,String message){
    	HttpResponseWithMessage httpResponse = new HttpResponseWithMessage(message.toUpperCase());
    	return new ResponseEntity<HttpResponseWithMessage>(httpResponse,httpStatus);
	}
}
