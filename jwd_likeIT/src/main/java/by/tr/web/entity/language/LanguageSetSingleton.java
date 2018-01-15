package by.tr.web.entity.language;

public class LanguageSetSingleton {

	private static LanguageSetSingleton instance = new LanguageSetSingleton();

	private LanguageSet languageSet = new LanguageSet();

	private LanguageSetSingleton() {
		
	}

	public static LanguageSetSingleton getInstance() {
		return instance;
	}

	public LanguageSet getLanguageSet() {
		return languageSet;
	}

}
