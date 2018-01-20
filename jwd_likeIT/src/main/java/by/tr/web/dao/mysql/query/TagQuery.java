package by.tr.web.dao.mysql.query;

public final class TagQuery {

	public static final String SELECT_ALL_TAGS = 
			"SELECT * FROM keywords;";

	public static final String SELECT_TAG_NAMES = 
			"SELECT keyword FROM keywords;";

	private TagQuery() {

	}
}
