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
}
