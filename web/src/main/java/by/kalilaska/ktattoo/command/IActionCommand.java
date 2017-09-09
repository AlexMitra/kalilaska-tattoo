package by.kalilaska.ktattoo.command;

import by.kalilaska.ktattoo.controller.SessionRequestContent;


/**
 * Created by lovcov on 23.07.2017.
 */
public interface IActionCommand {    
    String getView(SessionRequestContent content);
}
