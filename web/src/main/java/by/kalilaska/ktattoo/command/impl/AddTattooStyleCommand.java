package by.kalilaska.ktattoo.command.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.kalilaska.ktattoo.bean.MasterPersonalAreaViewBean;
import by.kalilaska.ktattoo.bean.TattooStyleBean;
import by.kalilaska.ktattoo.command.IActionCommand;
import by.kalilaska.ktattoo.controller.SessionRequestContent;
import by.kalilaska.ktattoo.service.TattooStyleService;
import by.kalilaska.ktattoo.webname.CommandNameList;
import by.kalilaska.ktattoo.webname.RequestAttrNameList;
import by.kalilaska.ktattoo.webname.RequestParamNameList;
import by.kalilaska.ktattoo.webname.SessionAttrNameList;
import by.kalilaska.ktattoo.webname.URINameList;
import by.kalilaska.ktattoo.webtype.TransitionType;

public class AddTattooStyleCommand implements IActionCommand{
	private final static Logger LOGGER = LogManager.getLogger(AddTattooStyleCommand.class);

	private TattooStyleService styleService;
	private String redirectedURI;
	private String defaultURI;
	
	public AddTattooStyleCommand(TattooStyleService styleService, String redirectedURI) {		
    	this.styleService = styleService;
    	this.defaultURI = redirectedURI;
    }
    
    private void defineWay(SessionRequestContent content) {
    	Object bean = content.getSessionAttributes().get(SessionAttrNameList.ATTRIBUTE_FOR_PERSONAL_AREA_VIEW_BEAN);
    	if(bean == null) {
    		redirectedURI = (String)content.getRequestAttributes().get(RequestAttrNameList.ATTRIBUTE_FOR_VIEW_NAME);        	
    	}else {
    		if(bean.getClass().equals(MasterPersonalAreaViewBean.class)) {    			
    			execute(content);
            }else {
            	redirectedURI = defaultURI;
            	content.insertSessionAttribute(SessionAttrNameList.ATTRIBUTE_FOR_COMMAND, 
            			CommandNameList.PERSONAL_AREA_VIEW_COMMAND);
            }    		
    	}
    	content.setTransition(TransitionType.SEND_REDIRECT);
    }
    
    private void execute(SessionRequestContent content) {
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
    
    private String getFirstParameter(String arr[]) {
    	String parameter = null;
    	if(arr != null && arr.length > 0) {
    		parameter = arr[0];
    	}
    	return parameter;
    }

    
    @Override
    public String getView(SessionRequestContent content) {
        defineWay(content);
        return redirectedURI;
    }
}
