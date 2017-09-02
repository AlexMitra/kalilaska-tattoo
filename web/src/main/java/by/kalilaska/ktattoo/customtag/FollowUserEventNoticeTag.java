package by.kalilaska.ktattoo.customtag;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.kalilaska.ktattoo.bean.ConsultationBean;
import by.kalilaska.ktattoo.bean.SeanceBean;
import by.kalilaska.ktattoo.webname.I18nNameList;
import by.kalilaska.ktattoo.webutil.DateUtil;
import by.kalilaska.ktattoo.webutil.FollowDayType;

public class FollowUserEventNoticeTag extends AbstractI18nMessageTag{
	private final static Logger LOGGER = LogManager.getLogger(FollowUserEventNoticeTag.class);
	private List<ConsultationBean> consultations;
	private List<SeanceBean> seances;	
	private DateUtil dateUtil = new DateUtil();

	public void setConsultationList(List<ConsultationBean> consultations) {
		this.consultations = consultations;
	}
	
	public void setSeanceList(List<SeanceBean> seances) {
		this.seances = seances;
	}
	
	@Override
    public int doStartTag() throws JspTagException {
		initResourceBundle();
		FollowDayType dayType = null;
		int count = 0;
		String message = null;
		
		if(consultations != null) {
			ConsultationBean consultation = null;
			while (count < consultations.size()) {
				consultation = consultations.get(count);
				dayType = dateUtil.getFollowDayType(consultation.getDate());
				if(dayType == FollowDayType.TOMORROW || dayType == FollowDayType.TODAY) {
					message = getMessage(I18nNameList.FOLLOW_USER_CONSULTATION_MARKER);
					makeNotice(message);
					break;
				}
				count++;
			}
		}
		
		if(seances != null) {
			SeanceBean seance = null;
			dayType = null;
			message = null;
			count = 0;
			while (count < seances.size()) {
				seance = seances.get(count);
				dayType = dateUtil.getFollowDayType(seance.getDate());
				if(dayType == FollowDayType.TOMORROW || dayType == FollowDayType.TODAY) {
					message = getMessage(I18nNameList.FOLLOW_USER_SEANCE_MARKER);
					makeNotice(message);
					break;
				}
				count++;
			}
		}					
		
		return SKIP_BODY;
	}
	
	private void makeNotice(String message) {
		if(message != null) {
			try {				
				JspWriter out = pageContext.getOut();
				out.write("<span class='signal-message remind-message'>");			
				out.write(message);
				out.write("</span>");
			} catch (IOException e) {
				LOGGER.log(Level.ERROR, "output exception: " + e.getMessage());
			}
		}
	}

}
