package by.kalilaska.ktattoo.webname;

import java.util.HashMap;

public class URICommandNameMap {
	public final static HashMap<String,String> uriCommandMap = new HashMap<>();
	
	static {
		uriCommandMap.put(URINameList.HOME_PAGE_URI, CommandNameList.HOME_VIEW_COMMAND);
		uriCommandMap.put(URINameList.LOGIN_PAGE_URI, CommandNameList.LOGIN_VIEW_COMMAND);
		uriCommandMap.put(URINameList.REGISTRATION_PAGE_URI, CommandNameList.REGISTRATION_VIEW_COMMAND);
		uriCommandMap.put(URINameList.PERSONAL_AREA_PAGE_URI , CommandNameList.PERSONAL_AREA_VIEW_COMMAND);
	}
}
