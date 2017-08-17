package by.kalilaska.ktattoo.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import by.kalilaska.ktattoo.webname.CommandNameList;
import by.kalilaska.ktattoo.webname.SessionAttrNameList;
import by.kalilaska.ktattoo.webname.URINameList;

/**
 * Servlet implementation class UploadController
 */
@WebServlet(name = "UploadController", urlPatterns = { "/personalArea/loadImage.html" })
public class UploadController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		if(checkAuthentification(request, response)) {
			String uploadFolder = "/images";
			String uploadPath = request.getServletContext().getRealPath(uploadFolder);
			String uploadPath2 = "D:/JAVA/EPAM/kalilaska-tattoo/web/src/main/webapp/images";
			
			int maxFileSize = 40*1024;
			int maxRequestSize = 50*1024;			

			if (ServletFileUpload.isMultipartContent(request)) {
				
				ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory());
				upload.setFileSizeMax(maxFileSize);
				upload.setSizeMax(maxRequestSize);
					
				File uploadDir = new File(uploadPath);
				if(!uploadDir.exists()) {
					uploadDir.mkdir();
				}
				
				try {
					Map<String, List<FileItem>> fileItemsMap = upload.parseParameterMap(request);

					List<FileItem> fileItems = fileItemsMap.get("uploadFile");

					for (FileItem item : fileItems) {

						if (!item.isFormField()) {
							String name = new File(item.getName()).getName();						
							item.write(new File(uploadPath + File.separator + name));
						}
					}

				} catch (Exception e) {
					//set attribute into request
					System.out.println("There was an error: " + e.getMessage());
				}

			} else {
				//set attribute into request
				System.out.println("file do not upload");
			}
			
			String view = "/WEB-INF/jsp/common/baseView.jsp";
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(view);
			dispatcher.forward(request, response);
		}
		
	}
	
	private boolean checkAuthentification(HttpServletRequest request, HttpServletResponse response) throws IOException {
		boolean authentificated = false;
		
		HttpSession session = request.getSession();
    	Object bean = session.getAttribute(SessionAttrNameList.ATTRIBUTE_FOR_PERSONAL_AREA_VIEW_BEAN);
    	
    	String viewBody = null;
    	String uri = null;
    	
    	if(bean == null) {
    		viewBody = "loginBody";
        	
        	uri = URINameList.LOGIN_PAGE_URI;
        	session.setAttribute(SessionAttrNameList.ATTRIBUTE_FOR_COMMAND, 
        			CommandNameList.LOGIN_VIEW_COMMAND);
        	session.setAttribute(SessionAttrNameList.ATTRIBUTE_FOR_VIEW_BODY, viewBody);
        	
        	response.sendRedirect(uri);
    	}else {            
        	viewBody = "personalAreaBody";
        	session.setAttribute(SessionAttrNameList.ATTRIBUTE_FOR_VIEW_BODY, viewBody);
        	authentificated = true;
    	}
    	
    	return authentificated;
	}

}
