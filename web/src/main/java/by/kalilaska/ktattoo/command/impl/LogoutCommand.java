package by.kalilaska.ktattoo.command.impl;

import by.kalilaska.ktattoo.command.IActionCommand;
import by.kalilaska.ktattoo.controller.SessionRequestContent;
import by.kalilaska.ktattoo.manager.PathViewManager;
import by.kalilaska.ktattoo.webexception.ViewSourceNotFoundException;
import by.kalilaska.ktattoo.webtype.TransitionType;


/**
 * Created by lovcov on 13.07.2017.
 */
public class LogoutCommand implements IActionCommand {
	private PathViewManager viewManager;
    protected String view;
    
    public LogoutCommand(String viewPath) {
    	try {
    		viewManager = new PathViewManager();
    		this.view = viewManager.getProperty(viewPath);
    	}catch (ViewSourceNotFoundException e) {
			//LOG
		}           
    }
    
    public void execute(SessionRequestContent content) {
    	content.setTransition(TransitionType.SESSION_INVALIDATE);
    }

    @Override
    public String getView(SessionRequestContent content) {
        execute(content);
        return view;
    }
}
