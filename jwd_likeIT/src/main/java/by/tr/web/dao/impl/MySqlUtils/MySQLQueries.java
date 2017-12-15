package by.tr.web.dao.impl.MySqlUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import by.tr.web.entity.User;


public class MySQLQueries implements QueriesUtils {


	@Override
	public void registrateUser(Connection conn, User user) throws SQLException {
		
		StringBuilder query = new StringBuilder("INSERT INTO Users (surname, name, login, password, status) VALUES (");
		query.append(createValue(user.getSurname())).append(", ");
		query.append(createValue(user.getName())).append(", ");
		query.append(createValue(user.getLogin())).append(", ");
		query.append(createValue(user.getPassword())).append(", ");
		query.append(createValue(user.getStatus())).append(");");
				
		System.out.println(query);
		
		Statement stm = conn.createStatement();
		stm.executeUpdate(query.toString());
		
	}


	@Override
	public User logIn(Connection conn, String login, String password) throws SQLException {

		return null;
	}


	@Override
	public User findUserByLogin(Connection conn, String login) throws SQLException {

		StringBuilder query = new StringBuilder("SELECT * FROM Users WHERE login='");
		query.append(login).append("';");
		
		Statement stm = conn.createStatement();
		ResultSet rs = stm.executeQuery(query.toString());
		
		List<User> result = createUsers(rs);
		if (result != null && !result.isEmpty()){
			return result.get(0);
		}
		return null;
	}
	
	 
	private static List<User> createUsers(ResultSet rs) throws SQLException {

		List<User> result = new ArrayList<User>();
		
		while(rs.next()){
			String surname  = rs.getString("surname");
			String name = rs.getString("name");
			String login = rs.getString("login");
			String status = rs.getString("status");
			
	 		User user = new User();
			
			user.setSurname(surname);
			user.setName(name);
			user.setLogin(login);
			user.setStatus(status);
			result.add(user);
		}
		
		return result;
	}
	
	private String createValue(String value){
		return "'" + value + "'";
	}

}
