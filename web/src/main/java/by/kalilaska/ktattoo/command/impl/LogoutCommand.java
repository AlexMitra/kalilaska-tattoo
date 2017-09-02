package by.kalilaska.ktattoo.command.impl;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.kalilaska.ktattoo.command.IActionCommand;
import by.kalilaska.ktattoo.controller.SessionRequestContent;
import by.kalilaska.ktattoo.webexception.ViewSourceNotFoundWebException;
import by.kalilaska.ktattoo.webmanager.PathViewManager;
import by.kalilaska.ktattoo.webtype.LanguageType;
import by.kalilaska.ktattoo.webtype.TransitionType;


/**
 * Created by lovcov on 13.07.2017.
 */
public class LogoutCommand implements IActionCommand {
	private final static Logger LOGGER = LogManager.getLogger(LogoutCommand.class);
	private PathViewManager viewManager;
    protected String view;
    
    public LogoutCommand(String viewPath) {
    	try {
    		viewManager = new PathViewManager();
    		this.view = viewManager.getProperty(viewPath);
    	}catch (ViewSourceNotFoundWebException e) {
			LOGGER.log(Level.WARN, "can not find configuration file for views creation: " + e.getMessage());
		}           
    }
    
    public void execute(SessionRequestContent content) {
    	LanguageType.resetCurrentLocaleAndLanguage();
    	content.setTransition(TransitionType.SESSION_INVALIDATE);
    }

    @Override
    public String getView(SessionRequestContent content) {
        execute(content);
        return view;
    }
}
