package by.tr.web.dao.impl.mysql.submitter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.tr.web.entity.text.TextType;
import by.tr.web.dao.exception.database.MySqlException;
import by.tr.web.dao.impl.mysql.query.MarkQuery;

public final class MarkQuerySubmitter {

	private final static Logger logger = LogManager.getLogger(MarkQuerySubmitter.class);

	public static boolean checkIfMarkExists(Connection conn, int textId, int userId, String queryTemplate)
			throws MySqlException {

		ResultSet rs = null;

		try (PreparedStatement ps = conn.prepareStatement(queryTemplate)) {

			ps.setInt(1, textId);
			ps.setInt(2, userId);
			rs = ps.executeQuery();

			if (!rs.next()) {
				return false;
			}
			return true;
		} catch (SQLException ex) {
			logger.error("Can't create a prepared statement and check if the mark exists");
			throw new MySqlException("Failed to execute a query to select marks", ex);
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				logger.warn("Can't close result set");
			}
		}
	}

	public static void addMarkWithoutCheckingCommandType(Connection conn, int textId, int userId, int mark,
			String queryTemplate) throws MySqlException {

		try (PreparedStatement ps = conn.prepareStatement(queryTemplate)) {
			ps.setInt(1, mark);
			ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
			ps.setInt(3, textId);
			ps.setInt(4, userId);
			ps.executeUpdate();

		} catch (SQLException ex) {
			logger.error("Can't insert / update a mark submitted by user with id = " + userId
					+ " for the text with id = " + textId);
			throw new MySqlException("Failed to execute command and insert / update a new mark", ex);
		}
	}

	public static void addedMark(Connection conn, int textId, int userId, int mark, TextType textType)
			throws MySqlException {

		try {
			conn.setAutoCommit(false);

			if (TextType.QUESTION.equals(textType)) {
				addMarkIntoDatabase(conn, textId, userId, mark, MarkQuery.CHECK_QUESTION_MARK,
						MarkQuery.INSERT_QUESTION_MARK, MarkQuery.UPDATE_QUESTION_MARK);
			} else if (TextType.ANSWER.equals(textType)) {
				addMarkIntoDatabase(conn, textId, userId, mark, MarkQuery.CHECK_ANSWER_MARK,
						MarkQuery.INSERT_ANSWER_MARK, MarkQuery.UPDATE_ANSWER_MARK);
			}
		} catch (SQLException ex) {
			throw new MySqlException("Can't execute query", ex);
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException ex) {
				logger.warn("Can't set an autocommit mode");
			}
		}
	}

	public static Double getAverageMark(Connection conn, int textId, TextType textType) throws MySqlException {

		try {
			PreparedStatement ps = null;
			if (TextType.QUESTION.equals(textType)) {
				ps = conn.prepareStatement(MarkQuery.FIND_AVERAGE_QUESTION_MARK);
			} else if (TextType.ANSWER.equals(textType)) {
				ps = conn.prepareStatement(MarkQuery.FIND_AVERAGE_ANSWER_MARK);
			}

			ps.setInt(1, textId);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getDouble(1);
			}
			return null;

		} catch (SQLException ex) {
			logger.error("Can't execure a query and get average score for text with id = " + textId);
			throw new MySqlException("Can't execute a query and calculate average score", ex);
		}
	}

	public static boolean deleteMark(Connection conn, int textId, int userId, TextType textType) throws MySqlException {

		try {
			conn.setAutoCommit(false);

			if (TextType.QUESTION.equals(textType)) {
				return deleteMarkFromDatabase(conn, textId, userId, MarkQuery.CHECK_QUESTION_MARK,
						MarkQuery.DELETE_QUESTION_MARK);
			} else if (TextType.ANSWER.equals(textType)) {
				return deleteMarkFromDatabase(conn, textId, userId, MarkQuery.CHECK_ANSWER_MARK,
						MarkQuery.DELETE_ANSWER_MARK);
			}
			return false;
		} catch (SQLException ex) {
			throw new MySqlException("Can't execute query and delete user mark", ex);
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException ex) {
				logger.warn("Can't set an autocommit mode");
			}
		}

	}

	private static void addMarkIntoDatabase(Connection conn, int textId, int userId, int mark, String checkQuery,
			String insertQuery, String updateQuery) throws MySqlException {
		
		if (checkIfMarkExists(conn, textId, userId, checkQuery)) {
			addMarkWithoutCheckingCommandType(conn, textId, userId, mark, updateQuery);
		} else {
			addMarkWithoutCheckingCommandType(conn, textId, userId, mark, insertQuery);
		}
	}

	private static boolean deleteMarkFromDatabase(Connection conn, int textId, int userId, String checkQuery,
			String deleteQuery) throws MySqlException {
		
		if (!checkIfMarkExists(conn, textId, userId, checkQuery)) {
			logger.warn("Can't delete an unexisting mark");
			return false;
		}

		try (PreparedStatement ps = conn.prepareStatement(deleteQuery)) {
			ps.setInt(1, textId);
			ps.setInt(2, userId);
			return ps.execute();
		} catch (SQLException ex) {
			logger.error("Can't delete a mark added by user with id = " + userId + " on the text with id = " + textId);
			throw new MySqlException("Can't delete user mark", ex);
		}
	}

	private MarkQuerySubmitter() {

	}

}
