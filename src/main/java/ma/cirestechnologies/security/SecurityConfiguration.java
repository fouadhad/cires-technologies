package ma.cirestechnologies.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ma.cirestechnologies.constant.SecurityConstants;
import ma.cirestechnologies.filter.JWTAuthenticationEntryPoint;
import ma.cirestechnologies.filter.JWTAuthorizationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	private UserDetailsService userDetailsService;
	private JWTAuthorizationFilter jwtAuthorizationFilter;
	private JWTAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	
	@Autowired
	public SecurityConfiguration(BCryptPasswordEncoder bCryptPasswordEncoder,UserDetailsService userDetailsService,JWTAuthorizationFilter jwtAuthorizationFilter, JWTAuthenticationEntryPoint jwtAuthenticationEntryPoint) {
		super();
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.userDetailsService = userDetailsService;
		this.jwtAuthorizationFilter = jwtAuthorizationFilter;
		this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().cors().and()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and().authorizeRequests().antMatchers(SecurityConstants.PUBLIC_URLS).permitAll()
		.anyRequest().authenticated()
		.and().addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
		.exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
		;
	}
	
	@Override
    public void configure(WebSecurity web) throws Exception {
        web
            .ignoring()
            .antMatchers("/h2-console/**");
    }
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

}
