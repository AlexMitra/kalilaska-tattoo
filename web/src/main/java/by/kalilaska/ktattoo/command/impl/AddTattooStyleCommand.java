package by.kalilaska.ktattoo.command.impl;

import by.kalilaska.ktattoo.bean.TattooStyleBean;
import by.kalilaska.ktattoo.command.AbstractPrgCommand;
import by.kalilaska.ktattoo.controller.SessionRequestContent;
import by.kalilaska.ktattoo.service.TattooStyleService;
import by.kalilaska.ktattoo.webname.CommandNameList;
import by.kalilaska.ktattoo.webname.RequestParamNameList;
import by.kalilaska.ktattoo.webname.SessionAttrNameList;
import by.kalilaska.ktattoo.webname.URINameList;

public class AddTattooStyleCommand extends AbstractPrgCommand{

	private TattooStyleService styleService;
	
	public AddTattooStyleCommand(TattooStyleService styleService, String redirectedURI) {
		super(redirectedURI);
    	this.styleService = styleService;    	
    }
    
	@Override
    protected void handle(SessionRequestContent content) {
    	String[] nameArr = content.getRequestParameters().get(RequestParamNameList.PARAMETER_FOR_ADD_TATTOO_STYLE_NAME);
        String[] descriptionArr = content.getRequestParameters().get(RequestParamNameList.PARAMETER_FOR_ADD_TATTOO_STYLE_DESCRIPTION);
        
        String name = getFirstParameter(nameArr);
        String description = getFirstParameter(descriptionArr);
        
        TattooStyleBean styleBean = styleService.create(name, description);
        
        if(styleBean != null) {
        	redirectedURI = defaultURI;
        	content.insertSessionAttribute(SessionAttrNameList.ATTRIBUTE_FOR_COMMAND, 
        			CommandNameList.PERSONAL_AREA_VIEW_COMMAND);
        }else {
        	content.insertSessionAttribute(SessionAttrNameList.ATTRIBUTE_FOR_ADD_TATTOO_STYLE_FAILURE,
        			styleService.getWorningMessage() != null ? styleService.getWorningMessage() : "");
        	
        	redirectedURI = URINameList.PERSONAL_AREA_ADD_TATTOO_STYLE_PAGE_URI;
        	content.insertSessionAttribute(SessionAttrNameList.ATTRIBUTE_FOR_COMMAND, 
        			CommandNameList.PERSONAL_AREA_ADD_TATTOO_STYLE_VIEW_COMMAND);
		}        
    }
}
