package by.kalilaska.ktattoo.customtag;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import by.kalilaska.ktattoo.bean.TattooStyleBean;

public class AllStylesListTag  extends TagSupport {
	
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
					out.write("<li class='edit-profile-dropdown-list-item'>");
					out.write("<a href='#' onclick=\"editProfile.addTattooStyle(" + style.getId() 
					+ ", '" + style.getName() + "')\">");
					out.write(style.getName());
					out.write("</a></li>");					
				} catch (IOException e) {
					// LOG			
				}
			}
		}
		
		return SKIP_BODY;		
	}

}
