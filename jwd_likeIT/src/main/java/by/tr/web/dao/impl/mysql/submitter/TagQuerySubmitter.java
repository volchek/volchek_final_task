package by.tr.web.dao.impl.mysql.submitter;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.tr.web.dao.exception.database.MySqlException;
import by.tr.web.dao.impl.mysql.query.TagQuery;
import by.tr.web.dao.impl.mysql.util.DatabaseField;

public final class TagQuerySubmitter {

	private final static Logger logger = LogManager.getLogger(TagQuerySubmitter.class.getName());

	public static List<String> getTagNames(Connection conn) throws MySqlException {

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

	public static Map<String, Integer> getAllTagInfo(Connection conn) throws MySqlException {

		try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(TagQuery.SELECT_ALL_TAGS)) {

			Map<String, Integer> tagInfo = new HashMap<String, Integer>();

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

	public static void insertTag(Connection conn, String tag) throws MySqlException {
		try (PreparedStatement ps = conn.prepareStatement(TagQuery.INSERT_TAG)) {
			ps.setString(1, tag);
			ps.executeUpdate();
		} catch (SQLException ex) {
			logger.error("Can't insert the tag with name='" + tag + "'");
			throw new MySqlException("Can't insert a new tag", ex);
		}
	}

	private TagQuerySubmitter() {

	}
}
