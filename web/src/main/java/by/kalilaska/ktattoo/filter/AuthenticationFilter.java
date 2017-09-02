package by.kalilaska.ktattoo.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.kalilaska.ktattoo.pathlist.PathBodyList;
import by.kalilaska.ktattoo.webexception.ViewSourceNotFoundWebException;
import by.kalilaska.ktattoo.webmanager.PathBodyManager;
import by.kalilaska.ktattoo.webname.CommandNameList;
import by.kalilaska.ktattoo.webname.RequestAttrNameList;
import by.kalilaska.ktattoo.webname.SessionAttrNameList;
import by.kalilaska.ktattoo.webname.URINameList;

@WebFilter(urlPatterns = {
		"/personalArea.html", 
		"/personalArea-edit.html", 
		"/personalArea-allAccounts.html",
		"/personalArea-deleteAvatar.html", 
		"/personalArea-updateAvatar.html", 
		"/personalArea-addConsultation.html", 
		"/personalArea-allConsultations.html", 
		"/personalArea-addStyle.html"})

public class AuthenticationFilter implements Filter{
	private final static Logger LOGGER = LogManager.getLogger(AuthenticationFilter.class);
	private PathBodyManager bodyManager;

	public AuthenticationFilter() {		
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		try {
			bodyManager = new PathBodyManager();
		} catch (ViewSourceNotFoundWebException e) {
			LOGGER.log(Level.ERROR, "can not find configuration file for view creation: " + e.getMessage());
		}
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpSession session = httpServletRequest.getSession();
		
		Object bean = session.getAttribute(SessionAttrNameList.ATTRIBUTE_FOR_PERSONAL_AREA_VIEW_BEAN);
    	if(bean == null) {
    		session.setAttribute(SessionAttrNameList.ATTRIBUTE_FOR_COMMAND, 
        			CommandNameList.LOGIN_VIEW_COMMAND);
    		
    		String redirectedUri = URINameList.LOGIN_PAGE_URI;
	        request.setAttribute(RequestAttrNameList.ATTRIBUTE_FOR_VIEW_NAME, redirectedUri);
	        
    		if(bodyManager != null) {
    			String viewBody = bodyManager.getProperty(PathBodyList.LOGIN_VIEW_BODY);
    			session.setAttribute(SessionAttrNameList.ATTRIBUTE_FOR_VIEW_BODY, viewBody);
    		}
	        
        }
    	chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
	}

}
