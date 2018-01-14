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
		langCommand.put(Language.RUST.getName(), Language.RUST);
		langCommand.put(Language.SCRATCH.getName(), Language.SCRATCH);
		langCommand.put(Language.GO.getName(), Language.GO);
		langCommand.put(Language.CPP_11.getName(), Language.CPP_11);
		langCommand.put(Language.CPP_14.getName(), Language.CPP_14);
		langCommand.put(Language.JAVA_7.getName(), Language.JAVA_7);
		langCommand.put(Language.JAVA_8.getName(), Language.JAVA_8);
		langCommand.put(Language.PYTHON_2.getName(), Language.PYTHON_2);
		langCommand.put(Language.PYTHON_3.getName(), Language.PYTHON_3);
		langCommand.put(Language.ADA.getName(), Language.ADA);
		langCommand.put(Language.ASSEMBLER.getName(), Language.ASSEMBLER);
		langCommand.put(Language.AUTOHOTKEY.getName(), Language.AUTOHOTKEY);
		langCommand.put(Language.BASH.getName(), Language.BASH);
		langCommand.put(Language.BASIC.getName(), Language.BASIC);
		langCommand.put(Language.CAML.getName(), Language.CAML);
		langCommand.put(Language.COBOL.getName(), Language.COBOL);
		langCommand.put(Language.COFFEESCRIPT.getName(), Language.COFFEESCRIPT);
		langCommand.put(Language.CUDA.getName(), Language.CUDA);
		langCommand.put(Language.DELPHI.getName(), Language.DELPHI);
		langCommand.put(Language.ECMASCRIPT.getName(), Language.ECMASCRIPT);
		langCommand.put(Language.ERLANG.getName(), Language.ERLANG);
		langCommand.put(Language.F_SHARP.getName(), Language.F_SHARP);
		langCommand.put(Language.FORTRAN.getName(), Language.FORTRAN);
		langCommand.put(Language.FOXPRO.getName(), Language.FOXPRO);
		langCommand.put(Language.HASKELL.getName(), Language.HASKELL);
		langCommand.put(Language.ICON.getName(), Language.ICON);
		langCommand.put(Language.JULIA.getName(), Language.JULIA);
		langCommand.put(Language.KOTLIN.getName(), Language.KOTLIN);
		langCommand.put(Language.LATEX.getName(), Language.LATEX);
		langCommand.put(Language.LISP.getName(), Language.LISP);
		langCommand.put(Language.LUA.getName(), Language.LUA);
		langCommand.put(Language.MATLAB.getName(), Language.MATLAB);
		langCommand.put(Language.MATHEMATICA.getName(), Language.MATHEMATICA);
		langCommand.put(Language.OBJECT_PASCAL.getName(), Language.OBJECT_PASCAL);
		langCommand.put(Language.OBJECTIVE_C.getName(), Language.OBJECTIVE_C);
		langCommand.put(Language.OCAML.getName(), Language.OCAML);
		langCommand.put(Language.OCTAVE.getName(), Language.OCTAVE);
		langCommand.put(Language.PASCAL.getName(), Language.PASCAL);
		langCommand.put(Language.POSTSCRIPT.getName(), Language.POSTSCRIPT);
		langCommand.put(Language.POWERSHELL.getName(), Language.POWERSHELL);
		langCommand.put(Language.PROLOG.getName(), Language.PROLOG);
		langCommand.put(Language.R.getName(), Language.R);
		langCommand.put(Language.RUBY.getName(), Language.RUBY);
		langCommand.put(Language.SCALA.getName(), Language.SCALA);
		langCommand.put(Language.SIMULA.getName(), Language.SIMULA);
		langCommand.put(Language.SMALLTALK.getName(), Language.SMALLTALK);
		langCommand.put(Language.TEX.getName(), Language.TEX);
		langCommand.put(Language.TSQL.getName(), Language.TSQL);
		langCommand.put(Language.TYPESCRIPT.getName(), Language.TYPESCRIPT);
		langCommand.put(Language.UNIX_SHELL.getName(), Language.UNIX_SHELL);
		langCommand.put(Language.UNITY.getName(), Language.UNITY);
		langCommand.put(Language.VISUAL_BACIC.getName(), Language.VISUAL_BACIC);
		langCommand.put(Language.WOLFRAM_LANGUAGE.getName(), Language.WOLFRAM_LANGUAGE);
		langCommand.put(Language.XML.getName(), Language.XML);
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
