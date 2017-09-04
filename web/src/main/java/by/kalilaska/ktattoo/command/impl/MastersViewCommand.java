package by.kalilaska.ktattoo.command.impl;

import java.util.List;

import by.kalilaska.ktattoo.bean.TattooMasterBean;
import by.kalilaska.ktattoo.controller.SessionRequestContent;
import by.kalilaska.ktattoo.service.TattooMasterService;
import by.kalilaska.ktattoo.webname.RequestAttrNameList;

public class MastersViewCommand extends SimpleViewCommand {
	private TattooMasterService tattooMasterService;

	public MastersViewCommand(TattooMasterService tattooMasterService, String viewPath, String viewBodyPath) {
		super(viewPath, viewBodyPath);
		this.tattooMasterService = tattooMasterService;
	}
    
	@Override
	protected void handle(SessionRequestContent content) {    	
    	List<TattooMasterBean> masters = tattooMasterService.findAllAllowedMasters();
    	
        content.insertRequestAttribute(RequestAttrNameList.ATTRIBUTE_FOR_VIEW_BODY, viewBody);
        content.insertRequestAttribute(RequestAttrNameList.ATTRIBUTE_FOR_MASTER_BEAN_LIST, masters);    	
    }
}
