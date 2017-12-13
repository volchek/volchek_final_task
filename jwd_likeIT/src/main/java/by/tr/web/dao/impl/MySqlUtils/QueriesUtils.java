package by.tr.web.dao.impl.MySqlUtils;

import java.sql.Connection;
import java.sql.SQLException;

import by.tr.web.entity.User;

public interface QueriesUtils {

	void registrateUser(Connection conn, User user) throws SQLException;

	User logIn(Connection conn, String login, String password) throws SQLException;
	
	User findUserByLogin(Connection conn, String login) throws SQLException;
	
}
