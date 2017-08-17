package by.kalilaska.ktattoo.servicetype;

import by.kalilaska.ktattoo.bean.AbstractPersonalAreaViewBean;
import by.kalilaska.ktattoo.bean.AdminPersonalAreaViewBean;
import by.kalilaska.ktattoo.bean.MasterPersonalAreaViewBean;
import by.kalilaska.ktattoo.bean.UserPersonalAreaViewBean;

/**
 * Created by lovcov on 22.07.2017.
 */
public enum AccountRoleType {    
    ADMINISTRATOR("Administrator"){

		@Override
		public AbstractPersonalAreaViewBean getPersonalAreaViewBean() {			
			return new AdminPersonalAreaViewBean();
		}
    	
    },
    MASTER("Master"){

		@Override
		public AbstractPersonalAreaViewBean getPersonalAreaViewBean() {			
			return new MasterPersonalAreaViewBean();
		}
    	
    },
    USER("User"){

		@Override
		public AbstractPersonalAreaViewBean getPersonalAreaViewBean() {			
			return new UserPersonalAreaViewBean();
		}
    	
    };
	
	private String role;

	private AccountRoleType(String role) {
		this.role = role;
	}

	public String getRole() {
		return role;
	}
	
	public abstract AbstractPersonalAreaViewBean getPersonalAreaViewBean();
	
//	public static AccountRoleType defineRoleType(List<String> roleNameList) {
//		List<AccountRoleType> roleTypeList = new ArrayList<>();
//		AccountRoleType roleType = null;		
//		
//		if(roleNameList != null && !roleNameList.isEmpty()) {			
//			roleNameList.stream().forEach(name -> roleTypeList.add(valueOf(name.toUpperCase())));			
//		}
//		
//		if(roleTypeList.contains(ADMINISTRATOR)) {
//			roleType = ADMINISTRATOR;
//		}else if(roleTypeList.contains(MASTER)){
//			roleType = MASTER;
//		}else if(roleTypeList.contains(USER)) {
//			roleType = USER;
//		}
//		
//		return roleType;
//	}
	
}
