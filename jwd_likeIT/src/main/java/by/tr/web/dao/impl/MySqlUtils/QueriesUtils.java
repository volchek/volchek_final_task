package by.tr.web.dao.impl.MySqlUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import by.tr.web.entity.User;

public interface QueriesUtils {

	List<User> findUser(Connection conn, Map<String, String> userInfo) throws SQLException;	

}
