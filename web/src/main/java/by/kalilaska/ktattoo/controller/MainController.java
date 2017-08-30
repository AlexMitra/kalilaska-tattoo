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
import by.kalilaska.ktattoo.webexception.ViewSourceNotFoundException;
import by.kalilaska.ktattoo.webmanager.PathViewManager;
import by.kalilaska.ktattoo.webtype.TransitionType;

/**
 * Created by lovcov on 13.07.2017.
 */
@WebServlet(name = "MainController", urlPatterns = { "/home.html", "/masters.html", "/login.html", "/registration.html",
		"/personalArea.html",  "/about-us.html", "/personalArea-edit.html", "/personalArea-allAccounts.html"})
public class MainController extends HttpServlet {
	private final static Logger LOGGER = LogManager.getLogger(MainController.class);

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);		
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {		
		executeRequest(request, response);
	}

	private void executeRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		LOGGER.log(Level.INFO, "in main controller");
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
			} catch (ViewSourceNotFoundException e) {
				// LOG				
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
			//LOG
			dispatcher = getServletContext().getRequestDispatcher(view);
			dispatcher.forward(request, response);
			break;
		}
	}

}
