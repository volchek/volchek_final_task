package by.tr.web.dao.impl.mySqlUtil;

import java.sql.Connection;
import java.sql.SQLException;

import by.tr.web.dao.impl.mySqlUtil.mySqlException.MySqlException;
import by.tr.web.entity.User;

public interface QueriesUtils {

	void registerUser(Connection conn, User user) throws SQLException, MySqlException;

	User signIn(Connection conn, String login, String password) throws SQLException, MySqlException;

	User findUserByLogin(Connection conn, String login) throws SQLException, MySqlException;

}
