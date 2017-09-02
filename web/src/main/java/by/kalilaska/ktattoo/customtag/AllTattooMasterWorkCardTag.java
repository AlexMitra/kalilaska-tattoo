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
import by.kalilaska.ktattoo.webname.ImageNameList;

public class AllTattooMasterWorkCardTag extends TagSupport {
	private final static Logger LOGGER = LogManager.getLogger(AllTattooMasterWorkCardTag.class);
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
					out.write("<div class='master-item-container'>");					
					out.write("<img src='" + writePhotoUrl(master) + "' class='master-photo'/>");					
					
					out.write("<span class='master-name'>");					
					out.write(master.getName());
					out.write("</span>");
					out.write("<button class='master-portfolio-button' data-toggle='modal' data-target='#Modal" + master.getId() + "'>");
					out.write("PORTFOLIO");
					out.write("</button>");
					out.write("</div>");
				} catch (IOException e) {
					LOGGER.log(Level.ERROR, "output exception: " + e.getMessage());
				}
			}
		}
		
		return SKIP_BODY;		
	}
	
	private String writePhotoUrl(TattooMasterBean master) {
		if(master.getMasterPhotoUrl()==null) {
			return ImageNameList.DEFAULT_PHOTO;
		}else {
			return master.getMasterPhotoUrl();
		}
	}
}
