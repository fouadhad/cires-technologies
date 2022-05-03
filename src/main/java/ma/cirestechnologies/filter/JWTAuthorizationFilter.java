package ma.cirestechnologies.filter;

import java.io.IOException;
import java.util.List;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ma.cirestechnologies.constant.SecurityConstants;
import ma.cirestechnologies.utility.JWTTokenProvider;

@Component
public class JWTAuthorizationFilter extends OncePerRequestFilter{
	
	private JWTTokenProvider jwtTokenProvider;
	
	public JWTAuthorizationFilter(JWTTokenProvider jwtTokenProvider) {
		super();
		this.jwtTokenProvider = jwtTokenProvider;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		if(request.getMethod().equalsIgnoreCase(SecurityConstants.OPTIONS_HTTP_METHOD)) {
			response.setStatus(HttpStatus.OK.value());
		}
		else {
				
			String token = request.getHeader(SecurityConstants.AUTHORIZATION);
			System.out.println("token : "+token);
			if(token == null || !token.startsWith(SecurityConstants.TOKEN_PREFIX)) {
				filterChain.doFilter(request, response);
				return;
			}
			String tokenWithoutPrefix = token.substring(SecurityConstants.TOKEN_PREFIX.length());
			jwtTokenProvider.isTokenValid(tokenWithoutPrefix);
			String email = jwtTokenProvider.getSubject(tokenWithoutPrefix);
			List<GrantedAuthority> authorities = jwtTokenProvider.getAuthorities(tokenWithoutPrefix);
			Authentication authentication = jwtTokenProvider.getAuthentication(email, authorities, request);
			SecurityContextHolder.getContext().setAuthentication(authentication);
		
			}
        filterChain.doFilter(request, response);
		
		}	
}
