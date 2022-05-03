package ma.cirestechnologies.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import ma.cirestechnologies.constant.SecurityConstants;
import ma.cirestechnologies.entity.User;
import ma.cirestechnologies.exception.model.AuthorityNotFoundException;
import ma.cirestechnologies.exception.model.ParameterNullException;
import ma.cirestechnologies.model.AuthenticationCredentials;
import ma.cirestechnologies.model.HttpResponseWithJWT;
import ma.cirestechnologies.model.HttpResponseWithRegisteredUsers;
import ma.cirestechnologies.model.HttpResponseWithUserProfile;
import ma.cirestechnologies.model.UserDetailsImplementation;
import ma.cirestechnologies.service.UserService;
import ma.cirestechnologies.utility.JWTTokenProvider;

@RestController
@RequestMapping(value="/api")
public class UserController {
	private UserService userService;
	private AuthenticationManager authenticationManager;
	private JWTTokenProvider jwtTokenProvider;
	
	@Autowired
	public UserController(UserService userService, AuthenticationManager authenticationManager,JWTTokenProvider jwtTokenProvider) {
		super();
		this.userService = userService;
		this.authenticationManager = authenticationManager;
		this.jwtTokenProvider = jwtTokenProvider;
	}

	@GetMapping("/users/generate")
	public ResponseEntity<InputStreamResource> generateUsers(@RequestParam(value="count",required = true) int count) throws JsonProcessingException, ParameterNullException{
		List<User> users = userService.generateUsers(count);
		ObjectMapper mapper = new ObjectMapper();  
		byte[] buf = mapper.writeValueAsBytes(users);
		return ResponseEntity
		        .ok()
		        .contentLength(buf.length)
		        .contentType(
		                MediaType.parseMediaType("application/octet-stream"))
		        .header("Content-Disposition", "attachment; filename=\"users.json\"")
		        .body(new InputStreamResource(new ByteArrayInputStream(buf)));
	}
	
	@PostMapping(value="/users/batch",consumes = "multipart/form-data")
	public HttpResponseWithRegisteredUsers addUsers(@RequestBody(required = true) MultipartFile usersFile) throws IOException{
		List<User> users = userService.retrieveUsersFromFile(usersFile);
		HttpResponseWithRegisteredUsers httpResponse = userService.addUsers(users);
		return httpResponse;
	}
	
	@PostMapping("/auth")
	public ResponseEntity<HttpResponseWithJWT> login(@RequestBody(required = true) AuthenticationCredentials authenticationCredentials){	
		Authentication authentication = authenticate(authenticationCredentials.getUsername(),authenticationCredentials.getPassword());
		UserDetailsImplementation userDetails = (UserDetailsImplementation) authentication.getPrincipal();
		String token = jwtTokenProvider.generateJWTToken(userDetails);
		HttpResponseWithJWT httpResponse = new HttpResponseWithJWT(SecurityConstants.TOKEN_PREFIX+token);
		return new ResponseEntity<>(httpResponse,HttpStatus.OK);	
	}
	
	@GetMapping("/users/me")
	@SecurityRequirement(name = "cirestechnologiesapi")
	public HttpResponseWithUserProfile getConnectedUserProfile(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = (String) authentication.getPrincipal();
		User loggedUser = userService.findUserByEmail(email);
		HttpResponseWithUserProfile httpResponse = new HttpResponseWithUserProfile();
		BeanUtils.copyProperties(loggedUser,httpResponse);
		return httpResponse;
	}
	
	@GetMapping("/users/{username}")
	@SecurityRequirement(name = "cirestechnologiesapi")
	public HttpResponseWithUserProfile getUserProfile(@PathVariable(value="username",required = true) String username) throws AuthorityNotFoundException{
		User searchedUser = userService.findUserByUsername(username); 
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = (String) authentication.getPrincipal();
		User loggedUser = userService.findUserByEmail(email);
		return userService.getUserProfile(loggedUser, searchedUser, authentication);
	}
	
	private Authentication authenticate(String username, String password) {
		return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));	
	}
}
