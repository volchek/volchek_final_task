package by.tr.web.dao.impl.MySqlUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import by.tr.web.entity.User;

public class MySQLQueries implements QueriesUtils {

	public List<User> findUser(Connection conn, Map<String, String> userInfo) throws SQLException {
 
		String query = createQuery(userInfo);
		
		Statement stm = conn.createStatement();
		ResultSet rs = stm.executeQuery(query);
 
		List<User> result = createUsers(rs);
        
		return result;
	}	
	
	
	private static String createQuery(Map<String, String> userInfo){
		
		String surnameKey = User.Fields.SURNAME.getValue();
		String nameKey = User.Fields.NAME.getValue();
		
		String surnameValue = userInfo.get(surnameKey);
		String nameValue = userInfo.get(nameKey);
		
		String sql = constructQuery(surnameKey, surnameValue, nameKey, nameValue);
		
		return sql.toString();
	}
	
	
	private static String constructQuery(String surnameKey, String surnameValue, String nameKey, String nameValue){
		if (surnameValue == null || nameValue == null){
			return null;
		}
		
		StringBuilder sql = new StringBuilder("SELECT * FROM user_info WHERE ");
				
		if (!surnameValue.isEmpty() && !nameValue.isEmpty()){
			findUserBySurnameAndName(sql, surnameKey, surnameValue, nameKey, nameValue);
		}
		else if (surnameValue != null && !surnameValue.isEmpty()){
			findUserBySurname(sql, nameKey, nameValue);
		}
		else if (nameValue != null && !nameValue.isEmpty()){
			findUserByName(sql, nameKey, nameValue);
		}
		
		return sql.toString();
	}
	
	private static void findUserBySurnameAndName(StringBuilder sql, String surnameKey, String surnameValue, String nameKey, String nameValue){
			sql.append(surnameKey).append(" = '").append(surnameValue);
		sql.append("' and ").append(nameKey).append(" = '").append(nameValue);
		sql.append("'");
	}
	
	
	private static void findUserByName(StringBuilder sql, String nameKey, String nameValue){
		sql.append(nameKey).append(" = '").append(nameValue).append("'");
	}
	 
	 
	 private static void findUserBySurname(StringBuilder sql, String surnameKey, String surnameValue){
		sql.append(surnameKey).append(" = '").append(surnameValue).append("'");
	}
	 
	 
	private static List<User> createUsers(ResultSet rs) throws SQLException {
		
		List<User> result = new ArrayList<User>();
		
		while(rs.next()){
			String surname  = rs.getString(User.Fields.SURNAME.getValue());
			String name = rs.getString(User.Fields.NAME.getValue());
			String phone = rs.getString(User.Fields.PHONE.getValue());
			String email = rs.getString(User.Fields.EMAIL.getValue());

	 		User user = new User();
			
			user.setSurname(surname);
			user.setName(name);
			user.setPhone(phone);
			user.setEmail(email);
			
			result.add(user);
		}
		
		return result;
	}

}
