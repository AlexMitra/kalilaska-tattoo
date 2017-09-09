package by.kalilaska.ktattoo.customtag;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.kalilaska.ktattoo.bean.TattooMasterBean;
import by.kalilaska.ktattoo.bean.TattooPhotoBean;
import by.kalilaska.ktattoo.webname.ImageNameList;

public class AllTattooMasterPortfolioDialogTag extends TagSupport {
	private final static Logger LOGGER = LogManager.getLogger(AllTattooMasterPortfolioDialogTag.class);
	private final static String ID_MARKER = "ID";	
	private final static String STYLES_DELIMITER = ", ";
	
	private final static String SCRIPT_TEMPLATE = "<script type='text/javascript'>\n"
			+ "$().fancybox({\n"
			+ "selector: '[data-fancybox=\"tattoo-images-" + ID_MARKER + "\"]',\n"
			+ "loop: true"
			+ "});"
			+ "</script>";
	
	

	private List<TattooMasterBean> masterBeanList;	
	
	public void setMasterList(List<TattooMasterBean> masterBeanList) {
		this.masterBeanList = masterBeanList;
	}
	
	@Override
    public int doStartTag() throws JspTagException {		
		if(masterBeanList != null) {
			JspWriter out = pageContext.getOut();
			for (TattooMasterBean master : masterBeanList) {
				try {
					out.write(makeScript(master.getId()));
					out.write("<div class='modal fade bs-example-modal-lg' id='Modal" + master.getId() + "' tabindex='-1' role='dialog'>");
					out.write("<div class='modal-dialog modal-lg' role='document'>");
					out.write("<div class='modal-content'>");
					out.write("<div class='modal-header'>");
					out.write("<button class='close' data-dismiss='modal' aria-label='Close'><span aria-hidden='true'>&times;</span></button>");
					out.write("<h4 class='modal-title portfolio-dialog-title'>" + master.getName() + "</h4>");
					out.write("</div>");
					out.write("<div class='modal-body master-portfolio-body'>");
					out.write("<img src='" + writePhotoUrl(master) + "' class='master-photo-in-dialog'/>");
					out.write("<span class='master-info'>" + master.getAboutInfo() + "</span>");
					out.write("<span class='master-style-info'>Styles: " + writeStyles(master.getStyleNames()) + "</span>");
					out.write("<div class='portfolio-title'>PORTFOLIO</div>");
					out.write("<div class='portfolio-photos-container'>");
					out.write(makePhotoGallery(master.getId(), master.getPhotos(), true));
					out.write("</div>");
					out.write("<div class='portfolio-title'>SKETCHES</div>");
					out.write("<div class='portfolio-photos-container'>");
					out.write(makePhotoGallery(master.getId(), master.getPhotos(), false));
					out.write("</div>");
					out.write("</div>");
					out.write("<div class='consultation-link-container'>");
					out.write("<form id='add-consultation-view-link-from-masters' action='personalArea-addConsultation.html' method='POST'>");
					
					out.write("<input type='hidden' name='command' value='personal_area_add_consultation_view' />");
					out.write("</form>");
					
					out.write("</div>");
					out.write("<div class='modal-footer'>");
					out.write("<button class='btn btn-default' data-dismiss='modal'>Close</button>");
					out.write("</div></div></div></div>");					

				} catch (IOException e) {
					LOGGER.log(Level.ERROR, "output exception: " + e.getMessage());
				}
			}			
		}
		
		return SKIP_BODY;		
	}
	
	private String makeScript(Integer masterId) {
		return SCRIPT_TEMPLATE.replace(ID_MARKER, masterId.toString());
	}
	
	private String writeStyles(List<String> styles) {
		StringBuilder result = new StringBuilder();
		if(styles != null && !styles.isEmpty()) {
			
			for (int i = 0; i< styles.size(); i++) {
				if(i > 0) {
					result.append(STYLES_DELIMITER);
				}
				result.append(styles.get(i));				
			}
		}
		
		return result.toString();
	}
	
	private String makePhotoGallery(Integer masterId, List<TattooPhotoBean> photos, boolean isDone) {
		StringBuilder result = new StringBuilder();
		if(photos != null && !photos.isEmpty()) {
			
			for (TattooPhotoBean photo : photos) {
				if(photo.isDone() == isDone) {
					result.append("<a href='" + photo.getUrl() + "' data-fancybox='tattoo-images-" + masterId + "' data-caption=''>");
					result.append("<img class='portfolio-photo' src='" + photo.getUrl() + "' alt=''>");
					result.append("</a>");
				}
				
			}
		}
		
		return result.toString();
	}
	
	private String writePhotoUrl(TattooMasterBean master) {
		if(master.getMasterPhotoUrl()==null) {
			return ImageNameList.DEFAULT_PHOTO;
		}else {
			return master.getMasterPhotoUrl();
		}
	}

}
