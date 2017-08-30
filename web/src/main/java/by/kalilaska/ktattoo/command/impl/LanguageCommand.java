package by.kalilaska.ktattoo.command.impl;

import by.kalilaska.ktattoo.command.IActionCommand;
import by.kalilaska.ktattoo.controller.SessionRequestContent;
import by.kalilaska.ktattoo.webexception.ViewSourceNotFoundException;
import by.kalilaska.ktattoo.webmanager.PathViewManager;
import by.kalilaska.ktattoo.webname.SessionAttrNameList;
import by.kalilaska.ktattoo.webtype.LanguageType;

public class LanguageCommand implements IActionCommand {

	private PathViewManager viewManager;
	private String defaultView;
	private String view;
	private LanguageType language;
    
	public LanguageCommand(String viewPath, LanguageType language) {
		this.language = language;
		initViewManager();
		if(viewManager != null && viewPath != null) {
			this.defaultView = viewManager.getProperty(viewPath);
		}
	}
	
    private void initViewManager() {
    	try {
			viewManager = new PathViewManager();			
		} catch (ViewSourceNotFoundException e) {
			// LOG			
		}
    }
	
	private void execute(SessionRequestContent content) {	
		
		if(content.getSessionAttributes().get(SessionAttrNameList.ATTRIBUTE_FOR_VIEW) == null){
			content.insertSessionAttribute(SessionAttrNameList.ATTRIBUTE_FOR_VIEW, defaultView);
			view = defaultView;
		}else {
			view = (String)content.getSessionAttributes().get(SessionAttrNameList.ATTRIBUTE_FOR_VIEW);
		}
		
		content.insertSessionAttribute(SessionAttrNameList.ATTRIBUTE_FOR_LANGUAGE, language.getLocale());
	}

    @Override
    public String getView(SessionRequestContent content) {
        execute(content);
        return view;
    }
}
