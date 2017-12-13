package by.tr.web.dao.impl;

import java.util.List;
import java.util.Map;
import java.sql.Connection;
import java.sql.SQLException;

import by.tr.web.entity.User;
import by.tr.web.dao.UserDao;
import by.tr.web.dao.exception.DaoException;
import by.tr.web.dao.impl.MySqlUtils.ConnectionUtils;
import by.tr.web.dao.impl.MySqlUtils.MySQLQueries;
import by.tr.web.dao.impl.MySqlUtils.QueriesUtils;


public class MySQLUserDaoImpl implements UserDao {

	private static final String ERROR_MESSAGE = "User not found";
	
	
/*	
	public List<User> getUserInfo(Map<String, String> userInfo) throws DaoException {
		
		Connection conn = null;
        try {	
        	conn = ConnectionUtils.getConnection();
        
        	QueriesUtils query = new MySQLQueries();
        	List<User> res = query.findUser(conn, userInfo);
            
        	if (res == null || res.isEmpty()){
        		throw new DaoException(ERROR_MESSAGE);
        	}
        	return res;
        }
        catch(ClassNotFoundException ex){
        	throw new DaoException(ex);
        }
        catch(SQLException ex){
        	throw new DaoException(ex);
        }
        finally {
        	ConnectionUtils.close(conn);
        }
	}
*/
}
