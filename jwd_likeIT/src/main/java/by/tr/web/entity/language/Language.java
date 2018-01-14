package by.tr.web.entity.language;

public enum Language {

	C("C"), CPP("C++"), C_SHARP("C#"), JAVA("Java"), PYTHON("Python"), 
			SWIFT("Swift"), PERL("Perl"), PHP("PHP"), HTML("HTML"),
			CSS("CSS"), JAVASCRIPT("JavaScript"), SQL("SQL"),
			RUST("Rust"), SCRATCH("Scratch"), GO("Go"), 
			CPP_11("C++11"), CPP_14("C++14"), JAVA_7("Java7"), JAVA_8("Java8"),
			PYTHON_2("Python2"), PYTHON_3("Python3"), ADA("Ada"),
			ASSEMBLER("Assembler"), AUTOHOTKEY("AutoHotkey"), BASH("Bash"),
			BASIC("BASIC"), CAML("Caml"), COBOL("Cobol"), COFFEESCRIPT("CoffeeScript"),
			CUDA("CUDA"), DELPHI("Delphi"), ECMASCRIPT("ECMAScript"), ERLANG("Erlang"),
			F_SHARP("F#"), FORTRAN("Fortran"), FOXPRO("FoxPro"), HASKELL("Haskell"),
			ICON("Icon"), JULIA("Julia"), KOTLIN("Kotlin"), LATEX("LaTeX"), 
			LISP("Lisp"), LUA("Lua"), MATLAB("MATLAB"), MATHEMATICA("Mathematica"),
			OBJECT_PASCAL("Object Pascal"), OBJECTIVE_C("Objective-C"), OCAML("OCaml"),
			OCTAVE("Octave"), PASCAL("Pascal"), POSTSCRIPT("PostScript"), 
			POWERSHELL("PowerShell"), PROLOG("Prolog"), R("R"), RUBY("Ruby"),
			SCALA("Scala"), SIMULA("Simula"), SMALLTALK("Smalltalk"), TEX("TeX"),
			TSQL("T-SQL"), TYPESCRIPT("TypeScript"), UNIX_SHELL("Unix shell"), 
			UNITY("UNITY"), VISUAL_BACIC("Visual Basic"), WOLFRAM_LANGUAGE("Wolfram Language"),
			XML("XML");
	

	private final String name;

	Language(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
