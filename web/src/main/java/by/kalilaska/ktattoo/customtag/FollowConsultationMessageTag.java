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
import by.kalilaska.ktattoo.webutil.FollowDayType;

public class FollowConsultationMessageTag extends AbstractI18nMessageTag{
	private final static Logger LOGGER = LogManager.getLogger(FollowConsultationMessageTag.class);
	private List<ConsultationBean> consultations;	
	private DateUtil dateUtil = new DateUtil();

	public void setConsultationList(List<ConsultationBean> consultations) {
		this.consultations = consultations;
	}
	
	@Override
    public int doStartTag() throws JspTagException {
		initResourceBundle();
		if(resourceBundle != null) {
			String message = getMessage(I18nNameList.CONSULTATION_MESSAGE_NEGATIVE);
			if(consultations != null) {
				ConsultationBean consultation = null;
				int count = 0;
				while (count < consultations.size()) {
					consultation = consultations.get(count);
					
					if(dateUtil.getFollowDayType(consultation.getDate()) == FollowDayType.TOMORROW) {
						message = getMessage(I18nNameList.CONSULTATION_MESSAGE_POSITIVE_TOMORROW);						
					}
					if(dateUtil.getFollowDayType(consultation.getDate()) == FollowDayType.TODAY) {
						message = getMessage(I18nNameList.CONSULTATION_MESSAGE_POSITIVE_TODAY);						
						break;
					}
					
					count++;
				}
			}
			try {
				JspWriter out = pageContext.getOut();
				out.write(message);
			} catch (IOException e) {
				LOGGER.log(Level.ERROR, "output exception: " + e.getMessage());
			}
		}		
		
		return SKIP_BODY;
	}
}
