package by.tr.web.dao.impl.mysql.query;

public final class TagQuery {

	public static final String SELECT_ALL_TAGS = 
			"SELECT * FROM keywords;";

	public static final String SELECT_TAG_NAMES = 
			"SELECT keyword FROM keywords;";

	public static final String INSERT_TAG =
			"INSERT INTO keywords "
			+ "(keyword) VALUES (?);";
	
	private TagQuery() {

	}
}
