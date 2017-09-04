package by.kalilaska.ktattoo.command.impl;

import java.util.List;

import by.kalilaska.ktattoo.bean.TattooMasterBean;
import by.kalilaska.ktattoo.controller.SessionRequestContent;
import by.kalilaska.ktattoo.service.TattooMasterService;
import by.kalilaska.ktattoo.webname.RequestAttrNameList;
import by.kalilaska.ktattoo.webname.SessionAttrNameList;

public class AddTattooEventViewCommand extends SimpleViewBodyContentCommand {

	private TattooMasterService masterService;

	public AddTattooEventViewCommand(TattooMasterService masterService, 
			String viewPath, String viewBodyPath, String bodyContentPath) {
		super(viewPath, viewBodyPath, bodyContentPath);
		this.masterService = masterService;
	}
    
	@Override
	protected void handle(SessionRequestContent content) {
    	List<TattooMasterBean> masterList = masterService.findAllAllowedMasters();
    	if(masterList != null) {
    		content.insertRequestAttribute(RequestAttrNameList.ATTRIBUTE_FOR_MASTER_BEAN_LIST, masterList);
    	}    		
        content.insertSessionAttribute(SessionAttrNameList.ATTRIBUTE_FOR_VIEW_BODY, viewBody);
        content.insertSessionAttribute(SessionAttrNameList.ATTRIBUTE_FOR_PA_BODY_CONTENT, bodyContent);

    }

	@Override
	public String getView(SessionRequestContent content) {
		handle(content);
        return view;
	}
}
