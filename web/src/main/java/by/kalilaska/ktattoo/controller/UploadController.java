package by.kalilaska.ktattoo.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import by.kalilaska.ktattoo.command.CommandType;
import by.kalilaska.ktattoo.command.IActionCommand;
import by.kalilaska.ktattoo.webmanager.WebMessageManager;
import by.kalilaska.ktattoo.webname.CommandNameList;
import by.kalilaska.ktattoo.webname.MessageNameList;
import by.kalilaska.ktattoo.webname.RequestAttrNameList;
import by.kalilaska.ktattoo.webname.RequestParamNameList;
import by.kalilaska.ktattoo.webname.SessionAttrNameList;
import by.kalilaska.ktattoo.webname.URINameList;

/**
 * Servlet implementation class UploadController
 */
@WebServlet(name = "UploadController", urlPatterns = { "/personalArea-updateAvatar.html" })
public class UploadController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
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
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		Object bean = session.getAttribute(SessionAttrNameList.ATTRIBUTE_FOR_PERSONAL_AREA_VIEW_BEAN);
		String redirectedUri = URINameList.PERSONAL_AREA_PAGE_URI;
		
		if(bean == null) {
			redirectedUri = (String)request.getAttribute(RequestAttrNameList.ATTRIBUTE_FOR_VIEW_NAME);
		}else {
			UploadControllerManager controllerManager = new UploadControllerManager();			
			String uploadFolder = controllerManager.getUploadFolder();
			String rootPath = controllerManager.getRootPath();
			String uploadPath = rootPath + uploadFolder;
//			String uploadPath = request.getServletContext().getRealPath(uploadFolder);
			int fileMaxSize = Integer.parseInt(controllerManager.getFileMaxSize());
			int requestMaxSize = Integer.parseInt(controllerManager.getRequestMaxSize());
			
			boolean admittedFileType = false;

			if (ServletFileUpload.isMultipartContent(request)) {
				
				ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory());
				upload.setFileSizeMax(fileMaxSize);
				upload.setSizeMax(requestMaxSize);
					
				File uploadDir = new File(uploadPath);
				if(!uploadDir.exists()) {
					uploadDir.mkdir();
				}
				
				try {
					Map<String, List<FileItem>> fileItemsMap = upload.parseParameterMap(request);
					List<FileItem> fileItems = fileItemsMap.get(RequestParamNameList.PARAMETER_FOR_UPLOAD_FILE);
					
					if(fileItems != null && fileItems.size() > 0) {
						FileItem item = fileItems.get(0);
						if (!item.isFormField()) {
							String name = new File(item.getName()).getName();
							
							List<String> admittedFileSuffixList = controllerManager.getAdmittedFileSuffixList();
							admittedFileType = checkFileSuffix(name, admittedFileSuffixList);
							
							if(admittedFileType) {
								item.write(new File(uploadPath + File.separator + name));
								String photoUrl = uploadFolder + controllerManager.getPhotoUrlSeparator() + name;
								String requestedUri = request.getRequestURI();
								
								if(requestedUri.equals(URINameList.PERSONAL_AREA_UPDATE_AVATAR_URI)) {
									request.setAttribute(
											RequestAttrNameList.ATTRIBUTE_FOR_UPDATED_PHOTO_URL, photoUrl);
									
									SessionRequestContent content = new SessionRequestContent();
									content.extractValues(request);

									IActionCommand command = CommandType.UPDATE_AVATAR.getCommand();
									redirectedUri = command.getView(content);
									content.rewriteValues(request);
									session.setAttribute(SessionAttrNameList.ATTRIBUTE_FOR_COMMAND, 
							        		CommandNameList.PERSONAL_AREA_VIEW_COMMAND);									
								}
							}else {								
								session.setAttribute(SessionAttrNameList.ATTRIBUTE_FOR_UPDATE_AVATAR_FAILURE, 
										WebMessageManager.getMessage(MessageNameList.UNADMITTED_FILE_TYPE));								
							}
						}
					}
				} catch (Exception e) {					
					session.setAttribute(SessionAttrNameList.ATTRIBUTE_FOR_UPDATE_AVATAR_FAILURE, 
							WebMessageManager.getMessage(MessageNameList.UPLOAD_FILE_ERROR) 
							+ e.getMessage());					
				}
			} else {
				session.setAttribute(SessionAttrNameList.ATTRIBUTE_FOR_UPDATE_AVATAR_FAILURE, 
						WebMessageManager.getMessage(MessageNameList.UNABLE_UPLOAD_FILE));				
			}			
	        response.sendRedirect(redirectedUri);
		}
		
	}
	
	private boolean checkFileSuffix(String fileName, List<String> admittedFileSuffixList) {
		boolean matchSuffix = false;
		if(admittedFileSuffixList != null) {
			int count = 0;
			while(count < admittedFileSuffixList.size()) {
				
				if(fileName.endsWith("." + admittedFileSuffixList.get(count))) {
					matchSuffix = true;
					break;
				}
				count++;
			}
		}
		return matchSuffix;
	}
}
