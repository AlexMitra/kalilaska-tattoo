package by.kalilaska.ktattoo.webtype;

public enum LanguageType {
	EN("en_US"),
	BE("be_BY");
	
	private String locale;

	private LanguageType(String locale) {
		this.locale = locale;
	}

	public String getLocale() {
		return locale;
	}
}
