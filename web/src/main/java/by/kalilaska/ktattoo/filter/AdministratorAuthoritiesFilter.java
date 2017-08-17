package by.kalilaska.ktattoo.filter;

//@WebFilter(urlPatterns = {"/personalArea-allAccounts.html"})
//public class AdministratorAuthoritiesFilter implements Filter{
//
//	public AdministratorAuthoritiesFilter() {
//	}
//
//	@Override
//	public void init(FilterConfig filterConfig) throws ServletException {
//	}
//
//	@Override
//	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//			throws IOException, ServletException {
//		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
//		HttpSession session = httpServletRequest.getSession();
//		
//		Object bean = session.getAttribute(SessionAttrNameList.ATTRIBUTE_FOR_PERSONAL_AREA_VIEW_BEAN);
//    	if(bean != null && bean.getClass().equals(AdminPersonalAreaViewBean.class)) {
//    		request.setAttribute(RequestAttrNameList.ATTRIBUTE_FOR_AUTHORITIES, 
//    				AccountRoleType.ADMINISTRATOR);
//        }
//		
//		chain.doFilter(request, response);
//	}
//
//	@Override
//	public void destroy() {
//
//	}
//
//}
