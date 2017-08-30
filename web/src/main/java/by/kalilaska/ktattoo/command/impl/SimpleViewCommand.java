package by.kalilaska.ktattoo.command.impl;

import by.kalilaska.ktattoo.command.IActionCommand;
import by.kalilaska.ktattoo.controller.SessionRequestContent;
import by.kalilaska.ktattoo.webexception.ViewSourceNotFoundException;
import by.kalilaska.ktattoo.webmanager.PathBodyManager;
import by.kalilaska.ktattoo.webmanager.PathViewManager;
import by.kalilaska.ktattoo.webname.SessionAttrNameList;

/**
 * Created by lovcov on 23.07.2017.
 */
public class SimpleViewCommand implements IActionCommand{
	
	private PathViewManager viewManager;
	private PathBodyManager bodyManager;
	private String view;
	private String viewBody;
	
    public SimpleViewCommand(String viewPath) {    	
    	initViewManager();
    	if(viewManager != null && viewPath != null) {
    		this.view = viewManager.getProperty(viewPath);
    	}
    }

    public SimpleViewCommand(String viewPath, String viewBodyPath) {    	
    	initAllManager();
    	if(viewManager != null && viewPath != null) {
    		this.view = viewManager.getProperty(viewPath);
    	}    	
    	if(bodyManager != null && viewBodyPath != null) {
    		this.viewBody = bodyManager.getProperty(viewBodyPath);    		
    	}
    }
    
    private void initViewManager() {
    	try {
			viewManager = new PathViewManager();			
		} catch (ViewSourceNotFoundException e) {
			// LOG			
		}
    }
    
    private void initAllManager() {
    	try {
			viewManager = new PathViewManager();
			bodyManager = new PathBodyManager();
		} catch (ViewSourceNotFoundException e) {
			// LOG			
		}
    }
    
    private void execute(SessionRequestContent content) {    	
        if(viewBody != null){        	
        	content.insertSessionAttribute(SessionAttrNameList.ATTRIBUTE_FOR_VIEW_BODY, viewBody);
        }        
    }

    @Override
    public String getView(SessionRequestContent content) {
        execute(content);
        return view;
    }
}
