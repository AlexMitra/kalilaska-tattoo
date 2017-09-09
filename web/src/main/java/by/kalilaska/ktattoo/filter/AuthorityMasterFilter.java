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

import by.kalilaska.ktattoo.bean.MasterPersonalAreaViewBean;
import by.kalilaska.ktattoo.webname.CommandNameList;
import by.kalilaska.ktattoo.webname.SessionAttrNameList;
import by.kalilaska.ktattoo.webname.URINameList;
@WebFilter(urlPatterns = {
		"/personalArea-allConsultations.html", 
		"/personalArea-approveAllConsultations.html", 
		"/personalArea-approveConsultation.html",		
		"/personalArea-allSeances.html", 
		"/personalArea-approveSeance.html", 
		"/personalArea-addStyle.html", 
		"/personalArea-works.html", 
		"/personalArea-addTattoPhoto.html", 
		"/personalArea-addTattoSketch.html", 
		"/personalArea-tattooPhotoDone.html"})
public class AuthorityMasterFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		HttpSession session = httpRequest.getSession();
		
		Object bean = session.getAttribute(SessionAttrNameList.ATTRIBUTE_FOR_PERSONAL_AREA_VIEW_BEAN);		
		
		if(bean != null && !bean.getClass().equals(MasterPersonalAreaViewBean.class)) {				
			session.setAttribute(SessionAttrNameList.ATTRIBUTE_FOR_COMMAND, 
					CommandNameList.PERSONAL_AREA_VIEW_COMMAND);
			
			HttpServletResponse httpResponse = (HttpServletResponse)response;
			httpResponse.sendRedirect(URINameList.PERSONAL_AREA_PAGE_URI);
			
		}else if(bean != null && bean.getClass().equals(MasterPersonalAreaViewBean.class)) {
			chain.doFilter(request, response);
		}
	}

	@Override
	public void destroy() {
	}

}
