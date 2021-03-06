package by.kalilaska.ktattoo.customtag;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.kalilaska.ktattoo.bean.ConsultationBean;
import by.kalilaska.ktattoo.webname.I18nNameList;
import by.kalilaska.ktattoo.webutil.DateUtil;

public class OwnConsultationsListTag extends AbstractI18nMessageTag {
	private final static Logger LOGGER = LogManager.getLogger(OwnConsultationsListTag.class);
	private List<ConsultationBean> consultations;	
	private DateUtil dateUtil = new DateUtil();
	
	public void setConsultationList(List<ConsultationBean> consultations) {
		this.consultations = consultations;
	}
	
	@Override
    public int doStartTag() throws JspTagException {
		initResourceBundle();
		if(consultations != null && resourceBundle != null) {
			String messageBeginWith = getMessage(I18nNameList.CONSULTATION_LIST_ITEM_BEGIN_WITH);
			JspWriter out = pageContext.getOut();
			for (ConsultationBean consultation : consultations) {
				try {
					if(dateUtil.isBefore(consultation.getDate())) {
						out.write("<li class='consultations-seances-list-item'>");						
						out.write(messageBeginWith + " " + dateUtil.writeDate(consultation.getDate()));
						out.write("</li>");
					}
				} catch (IOException e) {
					LOGGER.log(Level.ERROR, "output exception: " + e.getMessage());
				}
			}
		}
		
		return SKIP_BODY;
	}

}
