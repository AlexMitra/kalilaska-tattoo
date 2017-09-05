package by.kalilaska.ktattoo.command;

import by.kalilaska.ktattoo.command.impl.AddAccountCommand;
import by.kalilaska.ktattoo.command.impl.AddTattooConsultationCommand;
import by.kalilaska.ktattoo.command.impl.AddTattooEventViewCommand;
import by.kalilaska.ktattoo.command.impl.AddTattooSeanceCommand;
import by.kalilaska.ktattoo.command.impl.AddTattooStyleCommand;
import by.kalilaska.ktattoo.command.impl.AddWorkPhotoCommand;
import by.kalilaska.ktattoo.command.impl.AllAccountsViewCommand;
import by.kalilaska.ktattoo.command.impl.AllTattooConsultationsViewCommand;
import by.kalilaska.ktattoo.command.impl.AllTattooSeancesViewCommand;
import by.kalilaska.ktattoo.command.impl.AllowAccountCommand;
import by.kalilaska.ktattoo.command.impl.ApproveAllTattooConsultationsCommand;
import by.kalilaska.ktattoo.command.impl.ApproveTattooConsultationCommand;
import by.kalilaska.ktattoo.command.impl.ApproveTattooSeanceCommand;
import by.kalilaska.ktattoo.command.impl.AuthenticationCommand;
import by.kalilaska.ktattoo.command.impl.DeleteAccountCommand;
import by.kalilaska.ktattoo.command.impl.DeleteAvatarCommand;
import by.kalilaska.ktattoo.command.impl.EditAccountCommand;
import by.kalilaska.ktattoo.command.impl.EditProfileCommand;
import by.kalilaska.ktattoo.command.impl.EditProfileViewCommand;
import by.kalilaska.ktattoo.command.impl.ForbideAccountCommand;
import by.kalilaska.ktattoo.command.impl.LanguageCommand;
import by.kalilaska.ktattoo.command.impl.LogoutCommand;
import by.kalilaska.ktattoo.command.impl.MastersViewCommand;
import by.kalilaska.ktattoo.command.impl.PersonalAreaViewCommand;
import by.kalilaska.ktattoo.command.impl.RegistrationAccountCommand;
import by.kalilaska.ktattoo.command.impl.SimpleViewBodyContentCommand;
import by.kalilaska.ktattoo.command.impl.SimpleViewCommand;
import by.kalilaska.ktattoo.command.impl.TattooWorkDoneCommand;
import by.kalilaska.ktattoo.command.impl.UpdateAvatarCommand;
import by.kalilaska.ktattoo.command.impl.WorksViewCommand;
import by.kalilaska.ktattoo.pathlist.PathBodyContentList;
import by.kalilaska.ktattoo.pathlist.PathBodyList;
import by.kalilaska.ktattoo.pathlist.PathViewList;
import by.kalilaska.ktattoo.service.impl.AccountServiceImpl;
import by.kalilaska.ktattoo.service.impl.AuthenticationServiceImpl;
import by.kalilaska.ktattoo.service.impl.ConsultationServiceImpl;
import by.kalilaska.ktattoo.service.impl.RegistrationServiceImpl;
import by.kalilaska.ktattoo.service.impl.RoleServiceImpl;
import by.kalilaska.ktattoo.service.impl.SeanceServiceImpl;
import by.kalilaska.ktattoo.service.impl.TattooMasterServiceImpl;
import by.kalilaska.ktattoo.service.impl.TattooPhotoServiceImpl;
import by.kalilaska.ktattoo.service.impl.TattooStyleServiceImpl;
import by.kalilaska.ktattoo.webname.URINameList;
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
    
    MASTERS_VIEW(new MastersViewCommand(
    		new TattooMasterServiceImpl(), 
    		PathViewList.BASE_VIEW_PATH, 
    		PathBodyList.MASTERS_VIEW_BODY)),
    
    LOGIN_VIEW (new SimpleViewCommand(
    		PathViewList.BASE_VIEW_PATH, 
    		PathBodyList.LOGIN_VIEW_BODY)),
    
    REGISTRATION_VIEW (new SimpleViewCommand(
    		PathViewList.BASE_VIEW_PATH, 
    		PathBodyList.REGISTRATION_VIEW_BODY)),
    
    PERSONAL_AREA_VIEW (new PersonalAreaViewCommand(
    		new ConsultationServiceImpl(), 
    		new SeanceServiceImpl(), 
    		PathViewList.BASE_VIEW_PATH, 
    		PathBodyList.PERSONAL_AREA_VIEW_BODY, 
    		PathBodyContentList.PA_BODY_CONTENT_START)),
    
    ABOUT_US_VIEW (new SimpleViewCommand(
    		PathViewList.BASE_VIEW_PATH, 
    		PathBodyList.ABOUT_US_VIEW_BODY)),
    
    PERSONAL_AREA_DELETE_AVATAR(new DeleteAvatarCommand(
    		new AccountServiceImpl(), URINameList.PERSONAL_AREA_PAGE_URI)),
    
    PERSONAL_AREA_UPDATE_AVATAR (new UpdateAvatarCommand(
    		new AccountServiceImpl(), URINameList.PERSONAL_AREA_PAGE_URI)),
    
    PERSONAL_AREA_EDIT_PROFILE_VIEW (new EditProfileViewCommand(
    		new TattooMasterServiceImpl(), new TattooStyleServiceImpl(), 
    		PathViewList.BASE_VIEW_PATH, 
    		PathBodyList.PERSONAL_AREA_VIEW_BODY, 
    		PathBodyContentList.PA_BODY_CONTENT_EDIT_PROFILE)),
    
    PERSONAL_AREA_EDIT_PROFILE (new EditProfileCommand(
    		new AccountServiceImpl(), new TattooMasterServiceImpl(), new TattooStyleServiceImpl(), 
    		URINameList.PERSONAL_AREA_PAGE_URI)),    
    
    PERSONAL_AREA_ADD_CONSULTATION_VIEW (new AddTattooEventViewCommand (
    		new TattooMasterServiceImpl(), 
    		PathViewList.BASE_VIEW_PATH, 
    		PathBodyList.PERSONAL_AREA_VIEW_BODY, 
    		PathBodyContentList.PA_BODY_CONTENT_ADD_TATOO_CONSULTATION)),
    
    PERSONAL_AREA_ADD_CONSULTATION (new AddTattooConsultationCommand (
    		new ConsultationServiceImpl(), URINameList.PERSONAL_AREA_PAGE_URI)),
    
    PERSONAL_AREA_ALL_CONSULTATIONS_VIEW(new AllTattooConsultationsViewCommand (
    		new ConsultationServiceImpl(), 
    		PathViewList.BASE_VIEW_PATH, 
    		PathBodyList.PERSONAL_AREA_VIEW_BODY, 
    		PathBodyContentList.PA_BODY_CONTENT_ALL_TATOO_CONSULTATIONS)),    
    
    PERSONAL_AREA_APPROVE_ALL_CONSULTATIONS(new ApproveAllTattooConsultationsCommand (
    		new ConsultationServiceImpl(), URINameList.PERSONAL_AREA_ALL_CONSULTATIONS_PAGE_URI)),
    
    PERSONAL_AREA_APPROVE_CONSULTATION(new ApproveTattooConsultationCommand (
    		new ConsultationServiceImpl(), URINameList.PERSONAL_AREA_ALL_CONSULTATIONS_PAGE_URI)),    
    
    PERSONAL_AREA_ADD_SEANCE_VIEW(new AddTattooEventViewCommand(
    		new TattooMasterServiceImpl(), 
    		PathViewList.BASE_VIEW_PATH, 
    		PathBodyList.PERSONAL_AREA_VIEW_BODY, 
    		PathBodyContentList.PA_BODY_CONTENT_ADD_TATOO_SEANCE)),
    
    PERSONAL_AREA_ADD_SEANCE (new AddTattooSeanceCommand (
    		new SeanceServiceImpl(), URINameList.PERSONAL_AREA_PAGE_URI)),    

    PERSONAL_AREA_ALL_SEANCES_VIEW(new AllTattooSeancesViewCommand (
    		new SeanceServiceImpl(), 
    		PathViewList.BASE_VIEW_PATH, 
    		PathBodyList.PERSONAL_AREA_VIEW_BODY, 
    		PathBodyContentList.PA_BODY_CONTENT_ALL_TATOO_SEANCES)),    

    PERSONAL_AREA_APPROVE_SEANCE(new ApproveTattooSeanceCommand (
    		new SeanceServiceImpl(), URINameList.PERSONAL_AREA_ALL_SEANCES_PAGE_URI)),
    
    PERSONAL_AREA_ADD_STYLE_VIEW (new SimpleViewBodyContentCommand(    		
    		PathViewList.BASE_VIEW_PATH, 
    		PathBodyList.PERSONAL_AREA_VIEW_BODY, 
    		PathBodyContentList.PA_BODY_CONTENT_ADD_TATOO_STYLE)),    
    
    PERSONAL_AREA_ADD_STYLE (
    		new AddTattooStyleCommand(new TattooStyleServiceImpl(), URINameList.PERSONAL_AREA_PAGE_URI)),    
    
    PERSONAL_AREA_WORKS_VIEW(
    		new WorksViewCommand(new TattooPhotoServiceImpl(), 
    		PathViewList.BASE_VIEW_PATH, 
    	    PathBodyList.PERSONAL_AREA_VIEW_BODY, 
    	    PathBodyContentList.PA_BODY_CONTENT_TATOO_WORKS)),
    
    PERSONAL_AREA_ADD_TATTOO_WORK_PHOTO(
    		new AddWorkPhotoCommand(new TattooPhotoServiceImpl(), URINameList.PERSONAL_AREA_ADD_TATTOO_WORKS_PAGE_URI)),
    
    PERSONAL_AREA_ADD_TATTOO_SKETCH_PHOTO(
    		new AddWorkPhotoCommand(new TattooPhotoServiceImpl(), URINameList.PERSONAL_AREA_ADD_TATTOO_WORKS_PAGE_URI)),    

    PERSONAL_AREA_TATTOO_WORK_DONE(
    		new TattooWorkDoneCommand(new TattooPhotoServiceImpl(), URINameList.PERSONAL_AREA_ADD_TATTOO_WORKS_PAGE_URI)),
    
    PERSONAL_AREA_ALL_ACCOUNTS_VIEW (new AllAccountsViewCommand(
    		new AccountServiceImpl(), new RoleServiceImpl(), 
    		PathViewList.BASE_VIEW_PATH, 
    		PathBodyList.PERSONAL_AREA_VIEW_BODY, 
    		PathBodyContentList.PA_BODY_CONTENT_ALL_ACCOUNTS)),
    
    PERSONAL_AREA_ADD_ACCOUNT(new AddAccountCommand(new RegistrationServiceImpl(new AccountServiceImpl()),
    		new AccountServiceImpl(), 
    		URINameList.PERSONAL_AREA_ALL_ACCOUNTS_PAGE_URI)),
    
    PERSONAL_AREA_EDIT_ACCOUNT(new EditAccountCommand(new AccountServiceImpl(new TattooMasterServiceImpl()),
    		URINameList.PERSONAL_AREA_ALL_ACCOUNTS_PAGE_URI)),
    
    PERSONAL_AREA_FORBIDE_ACCOUNT(new ForbideAccountCommand(new AccountServiceImpl(),
    		URINameList.PERSONAL_AREA_ALL_ACCOUNTS_PAGE_URI)),
    
    PERSONAL_AREA_ALLOW_ACCOUNT(new AllowAccountCommand(new AccountServiceImpl(),
    		URINameList.PERSONAL_AREA_ALL_ACCOUNTS_PAGE_URI)),    
    
    PERSONAL_AREA_DELETE_ACCOUNT(new DeleteAccountCommand(new AccountServiceImpl(),
    		URINameList.PERSONAL_AREA_ALL_ACCOUNTS_PAGE_URI)),
    
    LOGOUT (new LogoutCommand(PathViewList.BASE_VIEW_PATH)),
	
	AUTHENTICATION(new AuthenticationCommand(
			new AuthenticationServiceImpl(new AccountServiceImpl(), new TattooStyleServiceImpl()), 
			URINameList.PERSONAL_AREA_PAGE_URI)),	
	
	REGISTRATION_ACCOUNT(new RegistrationAccountCommand(
			new RegistrationServiceImpl(new AccountServiceImpl()), 			
			URINameList.PERSONAL_AREA_PAGE_URI));

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
