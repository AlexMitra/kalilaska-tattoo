package by.kalilaska.ktattoo.command;


import by.kalilaska.ktattoo.controller.SessionRequestContent;
import by.kalilaska.ktattoo.webname.RequestAttrNameList;
import by.kalilaska.ktattoo.webname.SessionAttrNameList;


/**
 * Created by lovcov on 13.07.2017.
 */
public class CommandFactory {
    

    public IActionCommand initCommand(SessionRequestContent requestContent) {
        IActionCommand currentCommand = CommandType.getDefaultCommand();
        
        String commandName = null;        
        
        String[] commandNameArr = requestContent.getRequestParameters().get(RequestAttrNameList.ATTRIBUTE_FOR_COMMAND);
        if(commandNameArr != null && commandNameArr.length > 0){
        	commandName = commandNameArr[0];
        }        
        
        if(commandName == null || commandName.isEmpty()) {
        	commandName = (String)requestContent.getSessionAttributes().get(SessionAttrNameList.ATTRIBUTE_FOR_COMMAND);
        }

        if (commandName != null && !commandName.isEmpty()) {        	
        	CommandType commandType = CommandType.valueOf(commandName.toUpperCase());                
        	currentCommand = commandType.getCommand();            
        }       
        
        return currentCommand;
    }
}
