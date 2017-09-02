package by.kalilaska.ktattoo.controller;

/**
 * Servlet implementation class PrgController
 */
//@WebServlet(name = "PrgController", urlPatterns = { "/personalArea-deleteAvatar.html" })
//public class PrgController extends HttpServlet {
//	private static final long serialVersionUID = 1L;       
//
//	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		HttpSession session = request.getSession();
//		Object bean = session.getAttribute(SessionAttrNameList.ATTRIBUTE_FOR_PERSONAL_AREA_VIEW_BEAN);
//		String uri = null;
//		
//		if(bean == null) {			
//			uri = (String)request.getAttribute(RequestAttrNameList.ATTRIBUTE_FOR_VIEW_NAME);
//	    	response.sendRedirect(uri);
//		}else {
//			uri = URINameList.PERSONAL_AREA_PAGE_URI;
//	    	session.setAttribute(SessionAttrNameList.ATTRIBUTE_FOR_COMMAND, 
//	    			CommandNameList.PERSONAL_AREA_VIEW_COMMAND);        	
//	    	
//	    	response.sendRedirect(uri);
//		}
//	}
//
//	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		CommandFactory commandFactory = new CommandFactory();
//		SessionRequestContent content = new SessionRequestContent();
//		content.extractValues(request);
//
//		IActionCommand command = commandFactory.initCommand(content);
//		String redirectedUri = command.getView(content);
//		
//		content.rewriteValues(request);
//		response.sendRedirect(redirectedUri);
//	}
//
//}
