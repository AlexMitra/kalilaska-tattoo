package by.kalilaska.ktattoo.bean;

public class AccountBean {
	private int id;
	private String name;
	private String email;
	private String password;
	private String confirmPassword;
	private String phone;
	private String photoURL;
	private boolean isAllowed;
	private String role;

	public AccountBean() {		
	}

	public AccountBean(int id, String name, String email, String password, String phone, String photoURL,
			boolean isAllowed, String role) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.phone = phone;
		this.photoURL = photoURL;
		this.isAllowed = isAllowed;
		this.role = role;
	}	

	public AccountBean(int id, String name, String email, String phone, String photoURL, boolean isAllowed,
			String role) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.photoURL = photoURL;
		this.isAllowed = isAllowed;
		this.role = role;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}
	
	public String getConfirmPassword() {
		return confirmPassword;
	}

	public String getPhone() {
		return phone;
	}

	public String getPhotoURL() {
		return photoURL;
	}

	public boolean isAllowed() {
		return isAllowed;
	}

	public String getRole() {
		return role;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setPhotoURL(String photoURL) {
		this.photoURL = photoURL;
	}

	public void setAllowed(boolean isAllowed) {
		this.isAllowed = isAllowed;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "AccountBean [id=" + id + ", name=" + name + ", email=" + email + ", isAllowed=" + isAllowed + ", role="
				+ role + "]";
	}
	
}
