package by.kalilaska.ktattoo.customtag;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.kalilaska.ktattoo.bean.TattooStyleBean;

public class OwnStylesListTag  extends TagSupport {
	private final static Logger LOGGER = LogManager.getLogger(OwnStylesListTag.class);
	private List<TattooStyleBean> styles;
	
	public void setStyleList(List<TattooStyleBean> styles) {		
		this.styles = styles;
	}
	
	@Override
    public int doStartTag() throws JspTagException {		
		if(styles != null) {
			JspWriter out = pageContext.getOut();
			for (TattooStyleBean style : styles) {
				try {
					out.write("<li id='tattoo-style-" + style.getId() + "' class='edit-profile-style-list-item'>");
					out.write("<input id='edit-profile-style-" + style.getId() 
					+ "' type='hidden' name='edit_profile_tattoo_style_" + style.getId() 
					+ "' value='" + style.getId() + "'/>");
					out.write(style.getName());
					out.write("<a class='edit-profile-style-item-remove' href='#' onclick=\"editProfile.removeTattooStyle(" + style.getId() + ")\">");
					out.write("<i class='fa fa-times fa-lg' aria-hidden='true'></i>");
					out.write("</a>");					
					out.write("</li>");
					
				} catch (IOException e) {
					LOGGER.log(Level.ERROR, "output exception: " + e.getMessage());
				}
			}
		}
		
		return SKIP_BODY;		
	}

}
