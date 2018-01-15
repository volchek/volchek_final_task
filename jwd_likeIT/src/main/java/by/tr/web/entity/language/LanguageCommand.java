package by.tr.web.entity.language;

import java.util.HashMap;
import java.util.Map;

public class LanguageCommand {

	private static final LanguageCommand instance = new LanguageCommand();

	private final static Map<String, Language> languages = new HashMap<>();
	private static Map<String, Language> lowerCaseLanguages = new HashMap<>();
	static {
		languages.put(Language.C.getName(), Language.C);
		languages.put(Language.CPP.getName(), Language.CPP);
		languages.put(Language.C_SHARP.getName(), Language.C_SHARP);
		languages.put(Language.JAVA.getName(), Language.JAVA);
		languages.put(Language.PYTHON.getName(), Language.PYTHON);
		languages.put(Language.SWIFT.getName(), Language.SWIFT);
		languages.put(Language.PERL.getName(), Language.PERL);
		languages.put(Language.HTML.getName(), Language.HTML);
		languages.put(Language.CSS.getName(), Language.CSS);
		languages.put(Language.SQL.getName(), Language.SQL);
		languages.put(Language.JAVASCRIPT.getName(), Language.JAVASCRIPT);
		languages.put(Language.PHP.getName(), Language.PHP);
		languages.put(Language.RUST.getName(), Language.RUST);
		languages.put(Language.SCRATCH.getName(), Language.SCRATCH);
		languages.put(Language.GO.getName(), Language.GO);
		languages.put(Language.CPP_11.getName(), Language.CPP_11);
		languages.put(Language.CPP_14.getName(), Language.CPP_14);
		languages.put(Language.JAVA_7.getName(), Language.JAVA_7);
		languages.put(Language.JAVA_8.getName(), Language.JAVA_8);
		languages.put(Language.PYTHON_2.getName(), Language.PYTHON_2);
		languages.put(Language.PYTHON_3.getName(), Language.PYTHON_3);
		languages.put(Language.ADA.getName(), Language.ADA);
		languages.put(Language.ASSEMBLER.getName(), Language.ASSEMBLER);
		languages.put(Language.AUTOHOTKEY.getName(), Language.AUTOHOTKEY);
		languages.put(Language.BASH.getName(), Language.BASH);
		languages.put(Language.BASIC.getName(), Language.BASIC);
		languages.put(Language.CAML.getName(), Language.CAML);
		languages.put(Language.COBOL.getName(), Language.COBOL);
		languages.put(Language.COFFEESCRIPT.getName(), Language.COFFEESCRIPT);
		languages.put(Language.CUDA.getName(), Language.CUDA);
		languages.put(Language.DELPHI.getName(), Language.DELPHI);
		languages.put(Language.ECMASCRIPT.getName(), Language.ECMASCRIPT);
		languages.put(Language.ERLANG.getName(), Language.ERLANG);
		languages.put(Language.F_SHARP.getName(), Language.F_SHARP);
		languages.put(Language.FORTRAN.getName(), Language.FORTRAN);
		languages.put(Language.FOXPRO.getName(), Language.FOXPRO);
		languages.put(Language.HASKELL.getName(), Language.HASKELL);
		languages.put(Language.ICON.getName(), Language.ICON);
		languages.put(Language.JULIA.getName(), Language.JULIA);
		languages.put(Language.KOTLIN.getName(), Language.KOTLIN);
		languages.put(Language.LATEX.getName(), Language.LATEX);
		languages.put(Language.LISP.getName(), Language.LISP);
		languages.put(Language.LUA.getName(), Language.LUA);
		languages.put(Language.MATLAB.getName(), Language.MATLAB);
		languages.put(Language.MATHEMATICA.getName(), Language.MATHEMATICA);
		languages.put(Language.OBJECT_PASCAL.getName(), Language.OBJECT_PASCAL);
		languages.put(Language.OBJECTIVE_C.getName(), Language.OBJECTIVE_C);
		languages.put(Language.OCAML.getName(), Language.OCAML);
		languages.put(Language.OCTAVE.getName(), Language.OCTAVE);
		languages.put(Language.PASCAL.getName(), Language.PASCAL);
		languages.put(Language.POSTSCRIPT.getName(), Language.POSTSCRIPT);
		languages.put(Language.POWERSHELL.getName(), Language.POWERSHELL);
		languages.put(Language.PROLOG.getName(), Language.PROLOG);
		languages.put(Language.R.getName(), Language.R);
		languages.put(Language.RUBY.getName(), Language.RUBY);
		languages.put(Language.SCALA.getName(), Language.SCALA);
		languages.put(Language.SIMULA.getName(), Language.SIMULA);
		languages.put(Language.SMALLTALK.getName(), Language.SMALLTALK);
		languages.put(Language.TEX.getName(), Language.TEX);
		languages.put(Language.TSQL.getName(), Language.TSQL);
		languages.put(Language.TYPESCRIPT.getName(), Language.TYPESCRIPT);
		languages.put(Language.UNIX_SHELL.getName(), Language.UNIX_SHELL);
		languages.put(Language.UNITY.getName(), Language.UNITY);
		languages.put(Language.VISUAL_BACIC.getName(), Language.VISUAL_BACIC);
		languages.put(Language.WOLFRAM_LANGUAGE.getName(), Language.WOLFRAM_LANGUAGE);
		languages.put(Language.XML.getName(), Language.XML);
	}
	static {
		addLowerCaseLanguageName();
	}

	private LanguageCommand() {

	}

	public static LanguageCommand getInstance() {
		return instance;
	}

	public Map<String, Language> getLanguages() {
		return languages;
	}

	public Language getLanguage(String languageName) {
		return languages.get(languageName);
	}

	public Map<String, Language> getLowerCaseLanguages() {
		return lowerCaseLanguages;
	}

	public Language getLowerCaseLanguage(String languageName) {
		return lowerCaseLanguages.get(languageName);
	}

	private static void addLowerCaseLanguageName() {
		for (Map.Entry<String, Language> entry : languages.entrySet()) {
			String lowerCaseLanguageName = entry.getKey().toLowerCase();
			lowerCaseLanguages.put(lowerCaseLanguageName, entry.getValue());
		}
	}

}
