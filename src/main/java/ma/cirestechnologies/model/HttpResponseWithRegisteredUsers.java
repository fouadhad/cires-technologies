package ma.cirestechnologies.model;

public class HttpResponseWithRegisteredUsers {
	private int totalUsers;
	private int registeredUsers;
	private int unregisteredUsers;
	
	public HttpResponseWithRegisteredUsers(int totalUsers, int registeredUsers, int unregisteredUsers) {
		super();
		this.totalUsers = totalUsers;
		this.registeredUsers = registeredUsers;
		this.unregisteredUsers = unregisteredUsers;
	}

	public int getTotalUsers() {
		return totalUsers;
	}

	public void setTotalUsers(int totalUsers) {
		this.totalUsers = totalUsers;
	}

	public int getRegisteredUsers() {
		return registeredUsers;
	}

	public void setRegisteredUsers(int registeredUsers) {
		this.registeredUsers = registeredUsers;
	}

	public int getUnregisteredUsers() {
		return unregisteredUsers;
	}

	public void setUnregisteredUsers(int unregisteredUsers) {
		this.unregisteredUsers = unregisteredUsers;
	}
	
	

}
