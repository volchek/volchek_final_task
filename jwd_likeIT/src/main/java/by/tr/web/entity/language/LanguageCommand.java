package by.tr.web.entity.language;

import java.util.HashMap;
import java.util.Map;

public class LanguageCommand {

	private static final LanguageCommand instance = new LanguageCommand();

	private final Map<String, Language> langCommand = new HashMap<>();
	{
		langCommand.put(Language.C.getName(), Language.C);
		langCommand.put(Language.CPP.getName(), Language.CPP);
		langCommand.put(Language.C_SHARP.getName(), Language.C_SHARP);
		langCommand.put(Language.JAVA.getName(), Language.JAVA);
		langCommand.put(Language.PYTHON.getName(), Language.PYTHON);
		langCommand.put(Language.SWIFT.getName(), Language.SWIFT);
		langCommand.put(Language.PERL.getName(), Language.PERL);
		langCommand.put(Language.HTML.getName(), Language.HTML);
		langCommand.put(Language.CSS.getName(), Language.CSS);
		langCommand.put(Language.SQL.getName(), Language.SQL);
		langCommand.put(Language.JAVASCRIPT.getName(), Language.JAVASCRIPT);
		langCommand.put(Language.PHP.getName(), Language.PHP);
	}

	public static LanguageCommand getInstance() {
		return instance;
	}

	public Map<String, Language> getCommand() {
		return langCommand;
	}

	public Language getLanguage(String languageName) {
		return langCommand.get(languageName);
	}

	private LanguageCommand() {

	}

}
