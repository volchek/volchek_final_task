package by.tr.web.dao.language;

import java.util.List;

import by.tr.web.dao.exception.DaoException;

public interface LanguageDao {

	void extractAllLanguageInfo() throws DaoException;

	List<String> findFrequentLanguages() throws DaoException;

	void addLanguage(String language) throws DaoException;

}
