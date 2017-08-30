package by.kalilaska.ktattoo.customtag;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.servlet.jsp.tagext.TagSupport;

import by.kalilaska.ktattoo.webname.PropFileNameList;
import by.kalilaska.ktattoo.webtype.LanguageType;

public abstract class AbstractI18nMessageTag extends TagSupport{

	protected ResourceBundle resourceBundle;
	
	protected void initResourceBundle() {
		String namePropFile = PropFileNameList.I18N_FILE_EN;
		if(LanguageType.getCurrentLocale() == LanguageType.BE) {
			namePropFile = PropFileNameList.I18N_FILE_BE;
		}
		try {
    		resourceBundle = ResourceBundle.getBundle(
    				namePropFile, new Locale(LanguageType.getCurrentLanguage()));
    	}catch(MissingResourceException e) {
    		//LOG
    	}
	}
	
	protected String getMessage(String key) {
		String message = null;
		if(resourceBundle.containsKey(key)) {
			message = resourceBundle.getString(key);
		}
		return message;
	}
}
