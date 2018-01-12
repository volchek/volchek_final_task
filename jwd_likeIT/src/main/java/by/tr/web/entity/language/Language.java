package by.tr.web.entity.language;

public enum Language {

	C("C"), CPP("C++"), C_SHARP("C#"), JAVA("Java"), PYTHON("Python"), 
			SWIFT("Swift"), PERL("Perl"), PHP("php"), HTML("HTML"),
			CSS("CSS"), JAVASCRIPT("JavaScript"), SQL("Sql"),
			RUST("Rust"), SCRATCH("Scratch"), GO("Go");

	private final String name;

	Language(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
