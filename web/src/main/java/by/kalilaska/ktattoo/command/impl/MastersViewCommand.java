package by.kalilaska.ktattoo.command.impl;

import java.util.List;

import by.kalilaska.ktattoo.bean.TattooMasterBean;
import by.kalilaska.ktattoo.command.IActionCommand;
import by.kalilaska.ktattoo.controller.SessionRequestContent;
import by.kalilaska.ktattoo.service.TattooMasterService;
import by.kalilaska.ktattoo.webexception.ViewSourceNotFoundException;
import by.kalilaska.ktattoo.webmanager.PathBodyManager;
import by.kalilaska.ktattoo.webmanager.PathViewManager;
import by.kalilaska.ktattoo.webname.RequestAttrNameList;

public class MastersViewCommand implements IActionCommand {
	private TattooMasterService tattooMasterService;
	private PathViewManager viewManager;
	private PathBodyManager bodyManager;		
	private String view;
	private String viewBody;	

	public MastersViewCommand(TattooMasterService tattooMasterService, String viewPath, String viewBodyPath) {
		this.tattooMasterService = tattooMasterService;
		initManager();
		if(viewManager != null && viewPath != null) {
    		this.view = viewManager.getProperty(viewPath);
    	}
    	if(bodyManager != null && viewBodyPath != null) {
    		this.viewBody = bodyManager.getProperty(viewBodyPath);
    	}
	}
	
    private void initManager() {
    	try {
			viewManager = new PathViewManager();
			bodyManager = new PathBodyManager();			
		} catch (ViewSourceNotFoundException e) {
			// LOG
		}
    }
    
    private void execute(SessionRequestContent content) {    	
    	List<TattooMasterBean> masters = tattooMasterService.findAllAllowedMasters();
    	
        content.insertRequestAttribute(RequestAttrNameList.ATTRIBUTE_FOR_VIEW_BODY, viewBody);
        content.insertRequestAttribute(RequestAttrNameList.ATTRIBUTE_FOR_MASTER_BEAN_LIST, masters);    	
    }

	@Override
	public String getView(SessionRequestContent content) {
		execute(content);
        return view;
	}
}
