package by.kalilaska.ktattoo.command;

import by.kalilaska.ktattoo.controller.SessionRequestContent;
import by.kalilaska.ktattoo.webtype.TransitionType;

public abstract class AbstractPrgCommand implements IActionCommand {
	
	protected String redirectedURI;
	protected String defaultURI;

	public AbstractPrgCommand(String redirectedURI) {
		this.defaultURI = redirectedURI;
	}
	
	protected abstract void handle(SessionRequestContent content);
	
	protected String getFirstParameter(String arr[]) {
    	String parameter = null;
    	if(arr != null && arr.length > 0) {
    		parameter = arr[0];
    	}
    	return parameter;
    }
	
	@Override
	public String getView(SessionRequestContent content) {
		handle(content);
		content.setTransition(TransitionType.SEND_REDIRECT);
		return redirectedURI;
	}
}
