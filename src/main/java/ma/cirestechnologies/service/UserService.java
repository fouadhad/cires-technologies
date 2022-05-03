package ma.cirestechnologies.service;

import java.io.IOException;
import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;
import ma.cirestechnologies.entity.User;
import ma.cirestechnologies.exception.model.AuthorityNotFoundException;
import ma.cirestechnologies.exception.model.ParameterNullException;
import ma.cirestechnologies.model.HttpResponseWithRegisteredUsers;
import ma.cirestechnologies.model.HttpResponseWithUserProfile;

public interface UserService {
	public List<User> generateUsers(int count) throws ParameterNullException;
	public List<User> retrieveUsersFromFile(MultipartFile usersFile) throws IOException;
	public HttpResponseWithRegisteredUsers addUsers(List<User> users); 
	public User findUserByEmail(String email);
	public User findUserByUsername(String username);
	public HttpResponseWithUserProfile getUserProfile(User loggeduser, User searchedUser, Authentication authentication) throws AuthorityNotFoundException;
}
