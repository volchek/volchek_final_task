package by.tr.web.dao.impl.mysql_util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.tr.web.dao.impl.mysql_util.mysql_exception.MySqlException;

public class MySQLTagQuery {

	private static final String SELECT_ALL_TAGS = "SELECT * FROM keywords;";
	private static final String SELECT_TAG_NAMES = "SELECT keyword FROM keywords;";

	private final static Logger logger = LogManager.getLogger(MySQLTagQuery.class.getName());

	private Map<String, Integer> tags = new ConcurrentHashMap<String, Integer>();

	public void getAllTags(Connection conn) throws MySqlException {

		try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(SELECT_ALL_TAGS)) {
			while (rs.next()) {
				String tagName = rs.getString(DatabaseField.TAG_NAME);
				Integer tagID = rs.getInt(DatabaseField.TAG_ID);
				tags.put(tagName, tagID);
			}
		} catch (SQLException ex) {
			logger.error("Can't get full tag list");
			throw new MySqlException("Can't get full tag list", ex);
		}
	}

	public List<String> getTagNames(Connection conn) throws MySqlException {

		try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(SELECT_TAG_NAMES)) {
			List<String> tags = new ArrayList<String>();
			while (rs.next()) {
				String tagName = rs.getString(DatabaseField.TAG_NAME);
				tags.add(tagName);
			}
			return tags;
		} catch (SQLException ex) {
			logger.error("Can't get a list of tag names");
			throw new MySqlException("Can't get a list of tag names");
		}
	}
	
}
