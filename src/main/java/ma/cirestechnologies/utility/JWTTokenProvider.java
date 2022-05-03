package ma.cirestechnologies.utility;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import static com.auth0.jwt.algorithms.Algorithm.*;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import static java.util.Arrays.stream;
import java.util.ArrayList;
import ma.cirestechnologies.constant.SecurityConstants;
import ma.cirestechnologies.model.UserDetailsImplementation;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;

@Component
public class JWTTokenProvider {
	
	@Value("${jwt.secret}")
	private String secret;
	
	public String generateJWTToken(UserDetailsImplementation userDetails){
		String[] authorities = getAuthoritiesFromUserDetails(userDetails);
		return JWT.create()
				.withIssuer(SecurityConstants.MY_COMPANY)
				.withIssuedAt(new Date())
				.withSubject(userDetails.getEmail())
				.withArrayClaim(SecurityConstants.AUTHORITIES,authorities)
				.sign(HMAC512(this.secret.getBytes()));			
	}
	
	public Authentication getAuthentication(String username,List<GrantedAuthority> authorities,HttpServletRequest request){
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username,null,authorities);
		usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		return usernamePasswordAuthenticationToken;
	}
	
	public void isTokenValid(String token) {
		JWTVerifier verifier = getJWTVerifier();
		try {
		DecodedJWT decodedJWT = verifier.verify(token);
		}
		catch(JWTVerificationException exception) {
			 SecurityContextHolder.clearContext();
			throw new JWTVerificationException(SecurityConstants.TOKEN_CANNOT_BE_VERIFIED);
		}
	}
	
	public String getSubject(String token){
		String subject=null;
		JWTVerifier verifier = getJWTVerifier();
		try {
		subject = verifier.verify(token).getSubject();
		}
		catch(JWTVerificationException exception) {
			 SecurityContextHolder.clearContext();
			 throw new JWTVerificationException(SecurityConstants.TOKEN_CANNOT_BE_VERIFIED);
		}
		return subject;
	}
	
	public List<GrantedAuthority> getAuthorities(String token){
		String[] authorities = getAuthoritiesFromToken(token);
		return stream(authorities).map(SimpleGrantedAuthority::new).collect(Collectors.toList());	
	}
	
	private String[] getAuthoritiesFromToken(String token) {
		JWTVerifier verifier = getJWTVerifier();
		String[] authorities=null;
		try {
		 authorities =  verifier.verify(token).getClaim(SecurityConstants.AUTHORITIES).asArray(String.class);
		}
		catch(JWTVerificationException exception){
			throw new JWTVerificationException(SecurityConstants.TOKEN_CANNOT_BE_VERIFIED);
		}
		return authorities;
	}
	
	private String[] getAuthoritiesFromUserDetails(UserDetails userDetails) {
		List<String> authorities = new ArrayList<>();
		for(GrantedAuthority grantedAuthority: userDetails.getAuthorities()) {
			authorities.add(grantedAuthority.getAuthority());
		}
		return authorities.toArray(new String[authorities.size()]);
	}
	
	private JWTVerifier getJWTVerifier(){
		JWTVerifier verifier;
		Algorithm algorithm = HMAC512(this.secret.getBytes());
		verifier = JWT.require(algorithm).withIssuer(SecurityConstants.MY_COMPANY).build();
		return verifier;
	}
	

}
