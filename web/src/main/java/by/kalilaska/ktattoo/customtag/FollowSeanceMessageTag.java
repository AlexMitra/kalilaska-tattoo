package by.kalilaska.ktattoo.customtag;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.kalilaska.ktattoo.bean.SeanceBean;
import by.kalilaska.ktattoo.webname.I18nNameList;
import by.kalilaska.ktattoo.webutil.DateUtil;
import by.kalilaska.ktattoo.webutil.FollowDayType;

public class FollowSeanceMessageTag extends AbstractI18nMessageTag{
	private final static Logger LOGGER = LogManager.getLogger(FollowSeanceMessageTag.class);
	private List<SeanceBean> seances;	
	private DateUtil dateUtil = new DateUtil();
	
	public void setSeanceList(List<SeanceBean> seances) {
		this.seances = seances;
	}
	
	@Override
    public int doStartTag() throws JspTagException {
		initResourceBundle();
		if(resourceBundle != null) {
			String message = getMessage(I18nNameList.SEANCE_MESSAGE_NEGATIVE);
			if(seances != null) {
				SeanceBean seance = null;
				int count = 0;
				while (count < seances.size()) {
					seance = seances.get(count);
					
					if(dateUtil.getFollowDayType(seance.getDate()) == FollowDayType.TOMORROW) {
						message = getMessage(I18nNameList.SEANCE_MESSAGE_POSITIVE_TOMORROW);
					}
					if(dateUtil.getFollowDayType(seance.getDate()) == FollowDayType.TODAY) {
						message = getMessage(I18nNameList.SEANCE_MESSAGE_POSITIVE_TODAY);
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
