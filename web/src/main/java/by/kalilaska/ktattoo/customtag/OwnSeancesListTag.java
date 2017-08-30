package by.kalilaska.ktattoo.customtag;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;

import by.kalilaska.ktattoo.bean.SeanceBean;
import by.kalilaska.ktattoo.webname.I18nNameList;
import by.kalilaska.ktattoo.webutil.DateUtil;

public class OwnSeancesListTag  extends AbstractI18nMessageTag {
	
	private List<SeanceBean> seances;	
	private DateUtil dateUtil = new DateUtil();
	
	public void setSeanceList(List<SeanceBean> seances) {
		this.seances = seances;
	}
	
	@Override
    public int doStartTag() throws JspTagException {
		initResourceBundle();
		if(seances != null && resourceBundle != null) {
			String messageBeginWith = getMessage(I18nNameList.SEANCE_LIST_ITEM_BEGIN_WITH);
			JspWriter out = pageContext.getOut();
			for (SeanceBean seance : seances) {
				try {
					if(dateUtil.isBefore(seance.getDate())) {
						out.write("<li class='consultations-seances-list-item'>");
						out.write(messageBeginWith + " " + dateUtil.writeDate(seance.getDate()));
						out.write("</li>");
					}
				} catch (IOException e) {
					// LOG			
				}
			}
		}
		
		return SKIP_BODY;		
	}
}
