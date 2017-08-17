package by.kalilaska.ktattoo.command;

import by.kalilaska.ktattoo.command.impl.AddAccountCommand;
import by.kalilaska.ktattoo.command.impl.AllowAccountCommand;
import by.kalilaska.ktattoo.command.impl.AuthenticationCommand;
import by.kalilaska.ktattoo.command.impl.DeleteAccountCommand;
import by.kalilaska.ktattoo.command.impl.EditAccountCommand;
import by.kalilaska.ktattoo.command.impl.ForbideAccountCommand;
import by.kalilaska.ktattoo.command.impl.LanguageCommand;
import by.kalilaska.ktattoo.command.impl.LogoutCommand;
import by.kalilaska.ktattoo.command.impl.PersonalAreaAllAccountsViewCommand;
import by.kalilaska.ktattoo.command.impl.PersonalAreaViewCommand;
import by.kalilaska.ktattoo.command.impl.SimpleViewCommand;
import by.kalilaska.ktattoo.pathlist.PathBodyContentList;
import by.kalilaska.ktattoo.pathlist.PathBodyList;
import by.kalilaska.ktattoo.pathlist.PathViewList;
import by.kalilaska.ktattoo.service.impl.AccountServiceJdbc;
import by.kalilaska.ktattoo.service.impl.AuthenticationServiceJdbc;
import by.kalilaska.ktattoo.service.impl.ConsultationServiceJdbc;
import by.kalilaska.ktattoo.service.impl.RegistrationServiceJdbc;
import by.kalilaska.ktattoo.service.impl.RoleServiceJdbc;
import by.kalilaska.ktattoo.service.impl.SeanceServiceJdbc;
import by.kalilaska.ktattoo.webtype.LanguageType;


/**
 * Created by lovcov on 13.07.2017.
 */
public enum CommandType {
    ERROR_VIEW (new SimpleViewCommand(PathViewList.ERROR_VIEW_PATH)),    
	
	LANGUAGE_EN (new LanguageCommand(PathViewList.BASE_VIEW_PATH, LanguageType.EN)),
	LANGUAGE_BE (new LanguageCommand(PathViewList.BASE_VIEW_PATH, LanguageType.BE)),
    
    HOME_VIEW (new SimpleViewCommand(
    		PathViewList.BASE_VIEW_PATH, 
    		PathBodyList.HOME_VIEW_BODY)),
    LOGIN_VIEW (new SimpleViewCommand(
    		PathViewList.BASE_VIEW_PATH, 
    		PathBodyList.LOGIN_VIEW_BODY)),
    REGISTRATION_VIEW (new SimpleViewCommand(
    		PathViewList.BASE_VIEW_PATH, 
    		PathBodyList.REGISTRATION_VIEW_BODY)),
    PERSONAL_AREA_VIEW (new PersonalAreaViewCommand(
    		PathViewList.BASE_VIEW_PATH, 
    		PathBodyList.PERSONAL_AREA_VIEW_BODY, 
    		PathBodyContentList.PA_BODY_CONTENT_START)),
    
    ABOUT_US_VIEW (new SimpleViewCommand(
    		PathViewList.BASE_VIEW_PATH, 
    		PathBodyList.ABOUT_US_VIEW_BODY)),
    
    PERSONAL_AREA_ALL_ACCOUNTS_VIEW (new PersonalAreaAllAccountsViewCommand(
    		new AccountServiceJdbc(), new RoleServiceJdbc(), 
    		PathViewList.BASE_VIEW_PATH, 
    		PathBodyList.PERSONAL_AREA_VIEW_BODY, 
    		PathBodyContentList.PA_BODY_CONTENT_ALL_ACCOUNTS)),
    
    ADD_ACCOUNT(new AddAccountCommand(new RegistrationServiceJdbc(new AccountServiceJdbc()), 
    		new AccountServiceJdbc(), 
    		PathViewList.BASE_VIEW_PATH, 
    		PathBodyList.PERSONAL_AREA_VIEW_BODY, 
    		PathBodyContentList.PA_BODY_CONTENT_ALL_ACCOUNTS)),
    
    EDIT_ACCOUNT(new EditAccountCommand(new AccountServiceJdbc(), 
    		PathViewList.BASE_VIEW_PATH, 
    		PathBodyList.PERSONAL_AREA_VIEW_BODY, 
    		PathBodyContentList.PA_BODY_CONTENT_ALL_ACCOUNTS)),
    
    FORBIDE_ACCOUNT(new ForbideAccountCommand(new AccountServiceJdbc(), 
    		PathViewList.BASE_VIEW_PATH, 
    		PathBodyList.PERSONAL_AREA_VIEW_BODY, 
    		PathBodyContentList.PA_BODY_CONTENT_ALL_ACCOUNTS)),
    
    ALLOW_ACCOUNT(new AllowAccountCommand(new AccountServiceJdbc(), 
    		PathViewList.BASE_VIEW_PATH, 
    		PathBodyList.PERSONAL_AREA_VIEW_BODY, 
    		PathBodyContentList.PA_BODY_CONTENT_ALL_ACCOUNTS)),    
    
    DELETE_ACCOUNT(new DeleteAccountCommand(new AccountServiceJdbc(), 
    		PathViewList.BASE_VIEW_PATH, 
    		PathBodyList.PERSONAL_AREA_VIEW_BODY, 
    		PathBodyContentList.PA_BODY_CONTENT_ALL_ACCOUNTS)),
    
    LOGOUT (new LogoutCommand(PathViewList.BASE_VIEW_PATH)),

	
	AUTHENTICATION(new AuthenticationCommand(
			new AuthenticationServiceJdbc(new AccountServiceJdbc(), 
					new ConsultationServiceJdbc(), new SeanceServiceJdbc()), 
			PathViewList.BASE_VIEW_PATH, 
			PathBodyList.PERSONAL_AREA_VIEW_BODY));

    private IActionCommand command;

    CommandType(IActionCommand command) {
        this.command = command;
    }

    public IActionCommand getCommand() {
        return this.command;
    }

    public static IActionCommand getDefaultCommand(){
        return ERROR_VIEW.getCommand();
    }
}
