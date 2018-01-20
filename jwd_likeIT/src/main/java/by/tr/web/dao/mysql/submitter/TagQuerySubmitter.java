package by.tr.web.dao.mysql.submitter;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.tr.web.dao.database.util.exception.MySqlException;
import by.tr.web.dao.mysql.query.TagQuery;
import by.tr.web.dao.mysql.query.util.DatabaseField;

public class TagQuerySubmitter {

	private final static Logger logger = LogManager.getLogger(TagQuerySubmitter.class.getName());

	public List<String> getTagNames(Connection conn) throws MySqlException {

		try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(TagQuery.SELECT_TAG_NAMES)) {
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

	public Map<String, Integer> getAllTagInfo(Connection conn) throws MySqlException {

		Map<String, Integer> tagInfo = new HashMap<String, Integer>();

		try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(TagQuery.SELECT_ALL_TAGS)) {

			while (rs.next()) {
				String tagName = rs.getString(DatabaseField.TAG_NAME);
				Integer tagID = rs.getInt(DatabaseField.TAG_ID);
				tagInfo.put(tagName, tagID);
			}
			return tagInfo;

		} catch (SQLException ex) {
			logger.error("Can't get information about all tags");
			throw new MySqlException("Can't get information about all tags", ex);
		}
	}
}
