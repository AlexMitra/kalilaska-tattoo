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
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.kalilaska.ktattoo.webname.CommandNameList;
import by.kalilaska.ktattoo.webname.SessionAttrNameList;
import by.kalilaska.ktattoo.webname.URINameList;

@WebFilter(urlPatterns = {
		"/personalArea.html", 
		"/personalArea-editProfile.html", 		
		"/personalArea-deleteAvatar.html", 
		"/personalArea-updateAvatar.html", 
		"/personalArea-addConsultation.html", 
		"/personalArea-allConsultations.html", 
		"/personalArea-approveAllConsultations.html", 
		"/personalArea-approveConsultation.html", 
		"/personalArea-addSeance.html", 
		"/personalArea-allSeances.html", 
		"/personalArea-approveSeance.html", 
		"/personalArea-addStyle.html", 
		"/personalArea-works.html", 
		"/personalArea-addTattoPhoto.html", 
		"/personalArea-addTattoSketch.html", 
		"/personalArea-tattooPhotoDone.html", 
		"/personalArea-allAccounts.html", 
		"/personalArea-addAccount.html", 
		"/personalArea-editAccount.html", 
		"/personalArea-forbideAccount.html", 
		"/personalArea-allowAccount.html", 
		"/personalArea-deleteAccount.html"})
public class AuthenticationFilter implements Filter{

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httRequest = (HttpServletRequest) request;
		HttpSession session = httRequest.getSession();
		
		Object bean = session.getAttribute(SessionAttrNameList.ATTRIBUTE_FOR_PERSONAL_AREA_VIEW_BEAN);
    	if(bean == null) {    		
    		session.setAttribute(SessionAttrNameList.ATTRIBUTE_FOR_COMMAND, 
        			CommandNameList.LOGIN_VIEW_COMMAND);

    		HttpServletResponse httpResponse = (HttpServletResponse)response;
    		httpResponse.sendRedirect(URINameList.LOGIN_PAGE_URI);    		
        }else {
        	chain.doFilter(request, response);
        }    	
	}

	@Override
	public void destroy() {
	}

}
