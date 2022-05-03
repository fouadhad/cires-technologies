package ma.cirestechnologies.model;

import java.util.Date;

public class HttpResponseWithUserProfile {
	  private String firstName;
	  private String lastName;
	  private Date birthDate;
	  private String city;
	  private String country;
	  private String avatar;
	  private String company;
	  private String jobPosition;
	  private String mobile;
	  private String username;
	  private String email;
	  private String role;
	  
	  public HttpResponseWithUserProfile() {
		super();
	  }

	  public HttpResponseWithUserProfile(String firstName, String lastName, Date birthDate, String city, String country,
			String avatar, String company, String jobPosition, String mobile, String username, String email,
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
		this.role = role;
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

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	  
	  
	  
	  
}
