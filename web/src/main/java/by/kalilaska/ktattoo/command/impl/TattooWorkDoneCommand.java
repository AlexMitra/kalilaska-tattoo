package by.kalilaska.ktattoo.command.impl;

import by.kalilaska.ktattoo.command.AbstractPrgCommand;
import by.kalilaska.ktattoo.controller.SessionRequestContent;
import by.kalilaska.ktattoo.service.TattooPhotoService;
import by.kalilaska.ktattoo.webname.CommandNameList;
import by.kalilaska.ktattoo.webname.RequestParamNameList;
import by.kalilaska.ktattoo.webname.SessionAttrNameList;

public class TattooWorkDoneCommand extends AbstractPrgCommand {
	
	TattooPhotoService photoService;

	public TattooWorkDoneCommand(TattooPhotoService photoService, String redirectedURI) {
		super(redirectedURI);
		this.photoService = photoService;
	}

	@Override
	protected void handle(SessionRequestContent content) {
		String idArr[] = content.getRequestParameters().get(RequestParamNameList.PARAMETER_FOR_TATTOO_PHOTO_ID);
		String idStr = getFirstParameter(idArr);
		
    	boolean done = photoService.changeTattooPhotoDone(idStr, true);
        if(done == false) {
        	content.insertSessionAttribute(SessionAttrNameList.ATTRIBUTE_FOR_DELETE_AVATAR_FAILURE, 
        			photoService.getWorningngMessage());
    	}

    	redirectedURI = defaultURI;
    	content.insertSessionAttribute(SessionAttrNameList.ATTRIBUTE_FOR_COMMAND, 
        		CommandNameList.PERSONAL_AREA_WORKS_VIEW_COMMAND);
	}
}
