package by.kalilaska.ktattoo.webtype;

public enum LanguageType {
	EN("en_US", "en"),
	BE("be_BY", "be");
	
	private String locale;
	private String language;
	private static LanguageType currentLocale;
	private static String currentLanguage;

	private LanguageType(String locale, String language) {
		this.locale = locale;
		this.language = language;
	}

	public String getLocale() {
		setCurrentLocaleAndLanguage();
		return locale;
	}
	
	public String getLanguage() {
		setCurrentLocaleAndLanguage();
		return locale;
	}
	
	private void setCurrentLocaleAndLanguage() {
		currentLocale = this;
		currentLanguage = this.language;		
	}
	
	public static void resetCurrentLocaleAndLanguage() {
		currentLocale = EN;
		currentLanguage = EN.language;		
	}
	
	public static LanguageType getCurrentLocale() {
		if(currentLocale == null) {
			EN.setCurrentLocaleAndLanguage();
		}		
		return currentLocale;
	}
	
	public static String getCurrentLanguage() {
		if(currentLanguage == null) {
			EN.setCurrentLocaleAndLanguage();
		}		
		return currentLanguage;
	}
}
