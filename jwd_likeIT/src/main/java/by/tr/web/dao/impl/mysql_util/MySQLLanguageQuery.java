package by.tr.web.dao.impl.mysql_util;

import java.util.List;

import by.tr.web.entity.Language;

public class MySQLLanguageQuery {

	private static final String SELECT_ALL_LANGUAGES = "SELECT * FROM Languages WHERE login = ?;";
	private static final String FIND_LANGUAGE = "SELECT * FROM Languages WHERE language = ?;";
	
	private static List<Language> languages;
	
}
