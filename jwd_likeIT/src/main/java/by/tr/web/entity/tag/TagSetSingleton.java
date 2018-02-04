package by.tr.web.entity.tag;

public class TagSetSingleton {

	private static TagSetSingleton instance = new TagSetSingleton();

	private TagSet tagSet = new TagSet();

	private TagSetSingleton() {

	}

	public static TagSetSingleton getInstance() {
		return instance;
	}

	public TagSet getTagSet() {
		return tagSet;
	}

}
