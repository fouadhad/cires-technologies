package ma.cirestechnologies.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
public class User implements Serializable {
	  @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	  @Column(nullable=false,updatable=false)
	  @JsonIgnore
	  private Long id;
	  private String firstName;
	  private String lastName;
	  private Date birthDate;
	  private String city;
	  private String country;
	  private String avatar;
	  private String company;
	  private String jobPosition;
	  private String mobile;
	  @Column(unique=true)
	  private String username;
	  @Column(unique=true)
	  private String email;
	  private String password;
	  private String role;
	  
	  public User() {
			super();
	  }

	public User(String firstName, String lastName, Date birthDate, String city, String country, String avatar,
			String company, String jobPosition, String mobile, String username, String email, String password,
			String role) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthDate = birthDate;
		this.city = city;
		this.country = country;
		this.avatar = avatar;
		this.company = company;
		this.jobPosition = jobPosition;
		this.mobile = mobile;
		this.username = username;
		this.email = email;
		this.password = password;
		this.role = role;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getJobPosition() {
		return jobPosition;
	}

	public void setJobPosition(String jobPosition) {
		this.jobPosition = jobPosition;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	  
}
