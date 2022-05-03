package ma.cirestechnologies.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ma.cirestechnologies.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
	User findByUsername(String username);
	User findByEmail(String email);
	User findByUsernameOrEmail(String username,String email);

}
