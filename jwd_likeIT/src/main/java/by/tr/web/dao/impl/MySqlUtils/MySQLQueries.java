package by.tr.web.dao.impl.MySqlUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import by.tr.web.entity.User;
import by.tr.web.entity.UserAttributes;


public class MySQLQueries implements QueriesUtils {

	private static final String INSERT_USER_QUERY = "INSERT INTO Users (surname, name, login, password, status) VALUES (?, ?, ?, ?, ?)";
	private static final String LOG_IN_QUERY = "SELECT * FROM Users WHERE login = ? and password = ?;";
	private static final String FIND_USER_BY_LOGIN_QUERY = "SELECT * FROM Users WHERE login = ?;";
	
	
	@Override
	public void registrateUser(Connection conn, User user) throws SQLException {
		
		PreparedStatement ps = conn.prepareStatement(INSERT_USER_QUERY);
		
		ps.setString(1, user.getSurname());
		ps.setString(2, user.getName());
		ps.setString(3, user.getLogin());
		ps.setString(4, user.getPassword());
		ps.setString(5, user.getStatus());
		
		ps.executeUpdate();
		closeStatement(ps);
	}


	@Override
	public User signIn(Connection conn, String login, String password) throws SQLException {

		PreparedStatement ps = conn.prepareStatement(LOG_IN_QUERY);
		ps.setString(1, login);
		ps.setString(2, password);
		ResultSet rs = ps.executeQuery();
		
		User user = getFirstUser(rs);
		closeStatement(ps);
		
		return user;
	}


	@Override
	public User findUserByLogin(Connection conn, String login) throws SQLException {
		
		PreparedStatement ps = conn.prepareStatement(FIND_USER_BY_LOGIN_QUERY);
		ps.setString(1, login);
		ResultSet rs = ps.executeQuery();
		
		User user = getFirstUser(rs);
		closeStatement(ps);
		
		return user;
	}
	
	 
	private static List<User> createUsers(ResultSet rs) throws SQLException {

		List<User> result = new ArrayList<User>();
		
		while(rs.next()){
			String surname  = rs.getString(UserAttributes.SURNAME);
			String name = rs.getString(UserAttributes.NAME);
			String login = rs.getString(UserAttributes.LOGIN);
			String status = rs.getString(UserAttributes.STATUS);
			
	 		User user = new User();
			
			user.setSurname(surname);
			user.setName(name);
			user.setLogin(login);
			user.setStatus(status);
			result.add(user);
		}
		
		return result;
	}
	
		
	private User getFirstUser(ResultSet rs) throws SQLException{
	
		List<User> result = createUsers(rs);
		
		if (result != null && !result.isEmpty()){
			return result.get(0);
		}
		
		return null;
	}
	
	private void closeStatement(Statement st) {
		try {
			if (st != null){
				st.close();
			}
		} catch (SQLException ex){
			// LOG
		}
	}
}
