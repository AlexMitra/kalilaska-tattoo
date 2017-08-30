package by.kalilaska.ktattoo.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.kalilaska.ktattoo.command.CommandFactory;
import by.kalilaska.ktattoo.command.IActionCommand;
import by.kalilaska.ktattoo.webname.CommandNameList;
import by.kalilaska.ktattoo.webname.RequestAttrNameList;
import by.kalilaska.ktattoo.webname.SessionAttrNameList;
import by.kalilaska.ktattoo.webname.URINameList;

/**
 * Servlet implementation class PrgController
 */
@WebServlet(name = "PrgController", urlPatterns = { "/personalArea-deleteAvatar.html" })
public class PrgController extends HttpServlet {
	private static final long serialVersionUID = 1L;       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Object bean = session.getAttribute(SessionAttrNameList.ATTRIBUTE_FOR_PERSONAL_AREA_VIEW_BEAN);
		String uri = null;
		
		if(bean == null) {			
			uri = (String)request.getAttribute(RequestAttrNameList.ATTRIBUTE_FOR_VIEW_NAME);
	    	response.sendRedirect(uri);
		}else {
			uri = URINameList.PERSONAL_AREA_PAGE_URI;
	    	session.setAttribute(SessionAttrNameList.ATTRIBUTE_FOR_COMMAND, 
	    			CommandNameList.PERSONAL_AREA_VIEW_COMMAND);        	
	    	
	    	response.sendRedirect(uri);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CommandFactory commandFactory = new CommandFactory();
		SessionRequestContent content = new SessionRequestContent();
		content.extractValues(request);

		IActionCommand command = commandFactory.initCommand(content);
		String redirectedUri = command.getView(content);
		
		content.rewriteValues(request);
		//response.sendRedirect(redirectedUri);
		RequestDispatcher dispatcher = request.getRequestDispatcher(redirectedUri);
		dispatcher.forward(request, response);
	}

}
