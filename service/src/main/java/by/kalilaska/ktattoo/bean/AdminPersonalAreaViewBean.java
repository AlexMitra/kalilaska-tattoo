package by.kalilaska.ktattoo.bean;

import java.util.List;

public class AdminPersonalAreaViewBean extends AbstractPersonalAreaViewBean {
	private List<AccountBean> accounts;
	private List<RoleBean> roles;

	public AdminPersonalAreaViewBean() {
		//accounts = new LinkedList<>();
		//allowedRoles = new LinkedList<>();
	}	
	
	public List<AccountBean> getAccounts() {
		return accounts;
	}

	public List<RoleBean> getRoles() {
		return roles;
	}

	public void setAccounts(List<AccountBean> accounts) {
		//if(accounts != null) {
			this.accounts = accounts;
		//}		
	}

	public void setRoles(List<RoleBean> roles) {
		//if(allowedRoles != null) {
			this.roles = roles;
		//}		
	}

	public void addAccount(AccountBean account) {
		if(account != null && !accounts.contains(account)) {
			accounts.add(account);
		}		
	}
	
	public void removeAccount(AccountBean account) {
		accounts.remove(account);
	}
	
	public void addRole(RoleBean role) {
		if(role != null && !roles.contains(role)) {
			roles.add(role);
		}
	}
	
	public void removeRole(RoleBean role) {
		if(role != null) {
			roles.remove(role);
		}
	}

	@Override
	public String toString() {
		return "AdminPersonalAreaViewBean [accounts=" + accounts + ", roles=" + roles + ", getId()="
				+ getId() + ", getName()=" + getName() + ", getEmail()=" + getEmail() + ", isAllowed()=" + isAllowed()
				+ ", getRole()=" + getRole() + ", getConsultations()=" + getConsultations()
				+ ", getSeances()=" + getSeances() + "]";
	}

}
