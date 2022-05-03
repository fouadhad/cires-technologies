package ma.cirestechnologies.service.implementation;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import com.github.vatbub.randomusers.internal.Random;
import ma.cirestechnologies.constant.SecurityConstants;
import ma.cirestechnologies.entity.User;
import ma.cirestechnologies.exception.model.AuthorityNotFoundException;
import ma.cirestechnologies.exception.model.ParameterNullException;
import ma.cirestechnologies.model.HttpResponseWithRegisteredUsers;
import ma.cirestechnologies.model.HttpResponseWithUserProfile;
import ma.cirestechnologies.model.UserDetailsImplementation;
import ma.cirestechnologies.repository.UserRepository;
import ma.cirestechnologies.service.UserService;

@Service
public class UserServiceImplementation implements UserService,UserDetailsService {
	private UserRepository userRepository;
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	private static final String COUNT_NOT_NULL = "count";
	
	@Autowired
	public UserServiceImplementation(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
		super();
		this.userRepository = userRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	@Override
	public List<User> generateUsers(int count) throws ParameterNullException {
		List<User> users = null;
		if(count>0) {
			Faker faker = new Faker(Locale.US);
			users = new ArrayList<User>(); 
			User user = null;
			for(int i=0;i<count;i++) {
				user = new User();
				user.setFirstName(faker.name().firstName());
				user.setLastName(faker.name().lastName());
				user.setBirthDate(faker.date().birthday(18, 60));
				user.setCity(faker.address().cityName());
				user.setCountry(faker.address().countryCode());
				user.setAvatar(faker.avatar().image());
				user.setCompany(faker.company().name());
				user.setJobPosition(faker.job().position());
				user.setMobile(faker.phoneNumber().phoneNumber());
				user.setUsername(faker.name().username());
				user.setEmail(faker.internet().emailAddress());
				user.setPassword(faker.internet().password(6, 10, true, true, true));
				user.setRole(SecurityConstants.ROLE[Random.range(0, 1)]);
				users.add(user);
			}
		}
		else {
			throw new ParameterNullException(SecurityConstants.NOT_NULL_PARAMETER+COUNT_NOT_NULL);
		}
		return users;
	}

	@Override
	public List<User> retrieveUsersFromFile(MultipartFile usersFile) throws IOException {
		if(usersFile==null) {
			throw new NullPointerException(SecurityConstants.FILE_CONTENT);
		}
		ObjectMapper mapper = new ObjectMapper();
		TypeReference<List<User>> typeReference = new TypeReference<List<User>>(){};
		InputStream inputStream = usersFile.getInputStream();
		List<User> users = mapper.readValue(inputStream,typeReference);
		return users;
	}

	@Override
	public HttpResponseWithRegisteredUsers addUsers(List<User> users) {
		AtomicInteger numberOfSavedUsers = new AtomicInteger(0);
		EmailValidator validator = EmailValidator.getInstance();
		users.forEach((User user)->{
			  User userByUsername = userRepository.findByUsername(user.getUsername());
			  User userByEmail = userRepository.findByEmail(user.getEmail());
			  if(userByUsername==null && userByEmail==null) {
					 if (validator.isValid(user.getEmail())) {
						  user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
						  userRepository.save(user);
						  numberOfSavedUsers.incrementAndGet();
				     }
			  }
		});
		
		
		return new HttpResponseWithRegisteredUsers(users.size(), numberOfSavedUsers.get(), (users.size()-numberOfSavedUsers.get()));
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = userRepository.findByUsernameOrEmail(username, username);
		
		if(user==null) {
			throw new UsernameNotFoundException(SecurityConstants.NO_USER_FOUND_BY_USERNAME+username);
		}
		
		UserDetailsImplementation userDetailsImplementation=new UserDetailsImplementation(user);
		
		return userDetailsImplementation;
	}
	
	public User findUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	
	public User findUserByUsername(String username) {
		User user = userRepository.findByUsername(username);
		if(user==null) {
			throw new UsernameNotFoundException(SecurityConstants.NO_USER_FOUND_BY_USERNAME+username);
		}
		return user;
	}

	@Override
	public HttpResponseWithUserProfile getUserProfile(User loggedUser, User searchedUser, Authentication authentication) throws AuthorityNotFoundException {
		HttpResponseWithUserProfile httpResponse = new HttpResponseWithUserProfile();
		if(searchedUser.getId()==loggedUser.getId()) {
			BeanUtils.copyProperties(loggedUser,httpResponse);
		}
		else {
			if(isAdmin(authentication.getAuthorities())) {
				BeanUtils.copyProperties(searchedUser,httpResponse);
			}
			else {
				throw new AuthorityNotFoundException(SecurityConstants.AUTHORITY_MESSAGE);
			}
		}
		return httpResponse;
	}
	
	private boolean isAdmin(Collection<? extends GrantedAuthority> authorities) {
		boolean[] wrapper = {false};
		authorities.forEach((GrantedAuthority grantedAuthority)->{
			if(grantedAuthority.getAuthority().equals("ADMIN")) {
				wrapper[0]=true;
			}
		});
		return wrapper[0];
	}

}
