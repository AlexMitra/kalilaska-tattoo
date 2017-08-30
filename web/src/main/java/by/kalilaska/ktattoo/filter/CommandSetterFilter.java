package by.kalilaska.ktattoo.filter;

//@WebFilter(urlPatterns = {URINameList.HOME_PAGE_URI, 
//		URINameList.LOGIN_PAGE_URI, 
//		URINameList.REGISTRATION_PAGE_URI, 
//		URINameList.PERSONAL_AREA_PAGE_URI})
//public class CommandSetterFilter implements Filter{
//
//	@Override
//	public void init(FilterConfig filterConfig) throws ServletException {		
//		System.out.println("in CommandSetterFilter filter");
//	}
//
//	@Override
//	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//			throws IOException, ServletException {		
//		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
//		String uri = httpServletRequest.getRequestURI();
//		if(uri != null && !uri.isEmpty()) {
//			String commandName = URICommandNameMap.uriCommandMap.get(uri);			
//			if(commandName != null && !commandName.isEmpty()) {
//				request.setAttribute(RequestAttrNameList.ATTRIBUTE_FOR_COMMAND, commandName);
//			}
//		}
//		
//		
//	}
//
//	@Override
//	public void destroy() {
//				
//	}
//
//}
