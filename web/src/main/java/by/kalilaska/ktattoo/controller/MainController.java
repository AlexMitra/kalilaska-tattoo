package by.kalilaska.ktattoo.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.kalilaska.ktattoo.command.CommandFactory;
import by.kalilaska.ktattoo.command.IActionCommand;
import by.kalilaska.ktattoo.pathlist.PathViewList;
import by.kalilaska.ktattoo.webexception.ViewSourceNotFoundWebException;
import by.kalilaska.ktattoo.webmanager.PathViewManager;
import by.kalilaska.ktattoo.webtype.TransitionType;

/**
 * Created by lovcov on 13.07.2017.
 */
@WebServlet(name = "MainController", urlPatterns = { 
		"/home.html", 
		"/masters.html", 
		"/login.html", 
		"/registration.html",
		"/personalArea.html",  
		"/about-us.html", 
		"/personalArea-deleteAvatar.html", 
		"/personalArea-edit.html", 
		"/personalArea-addConsultation.html", 
		"/personalArea-allConsultations.html", 
		"/personalArea-addStyle.html", 
		"/personalArea-allAccounts.html"})

public class MainController extends HttpServlet {
	private final static Logger LOGGER = LogManager.getLogger(MainController.class);

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {		
		executeRequest(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {		
		executeRequest(request, response);
	}

	private void executeRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {		
		CommandFactory commandFactory = new CommandFactory();
		SessionRequestContent content = new SessionRequestContent();
		content.extractValues(request);

		IActionCommand command = commandFactory.initCommand(content);		
		String view = command.getView(content);
		TransitionType transition = content.getTransition();		
		content.rewriteValues(request);
		
		goResponse(request, response, view, transition);
	}
	
	private void goResponse(HttpServletRequest request, HttpServletResponse response, String view, 
			TransitionType transition) throws ServletException, IOException {
		
		if(view == null) {
			transition = TransitionType.NULL_VIEW;
			PathViewManager viewManager;
			try {
				viewManager = new PathViewManager();
				view = viewManager.getProperty(PathViewList.ERROR_500_VIEW_PATH);
			} catch (ViewSourceNotFoundWebException e) {
				LOGGER.log(Level.ERROR, "have got null view from command: " + e.getMessage());
			}
		}		
		
		RequestDispatcher dispatcher;
		
		switch (transition) {
		case FORWARD:
			dispatcher = getServletContext().getRequestDispatcher(view);
			dispatcher.forward(request, response);
			break;
		case SEND_REDIRECT:
			response.sendRedirect(view);
			break;
		case SESSION_INVALIDATE:
			HttpSession session = request.getSession();
			session.invalidate();
			dispatcher = getServletContext().getRequestDispatcher(view);
			dispatcher.forward(request, response);
			break;
		default:			
			dispatcher = getServletContext().getRequestDispatcher(view);
			dispatcher.forward(request, response);
			break;
		}
	}
}
