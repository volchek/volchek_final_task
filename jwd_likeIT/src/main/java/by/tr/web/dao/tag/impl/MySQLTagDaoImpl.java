package by.tr.web.dao.tag.impl;

import java.sql.Connection;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.tr.web.dao.database.util.exception.MySqlException;
import by.tr.web.dao.database.util.pool.ConnectionPool;
import by.tr.web.dao.database.util.pool.ConnectionPoolFactory;
import by.tr.web.dao.exception.DaoException;
import by.tr.web.dao.mysql_query.MySQLTagQuery;
import by.tr.web.dao.tag.TagDao;
import by.tr.web.entity.tag.TagSet;
import by.tr.web.entity.tag.TagSetSingleton;

public class MySQLTagDaoImpl implements TagDao {

	private final static ConnectionPoolFactory poolFactory = ConnectionPoolFactory.getInstance();
	private final static ConnectionPool connPool = poolFactory.getConnectionPool();

	private final static Logger logger = LogManager.getLogger(MySQLTagDaoImpl.class.getName());

	@Override
	public void extractAllTagInfo() throws DaoException {

		Connection conn = null;

		try {
			conn = connPool.getConnection();
			MySQLTagQuery query = new MySQLTagQuery();

			Map<String, Integer> tags = query.getAllTagInfo(conn);
			fillTagMaps(tags);

		} catch (MySqlException ex) {
			logger.error("Can't execute query and get information about all tags", ex);
			throw new DaoException("Failed to execute command and extract information about all tags", ex);
		} finally {
			connPool.closeConnection(conn);
		}
	}

	private void fillTagMaps(Map<String, Integer> tagInfo) throws DaoException {

		if (tagInfo == null) {
			throw new DaoException("There isn't information about tags in the database");
		}

		TagSetSingleton tagSetSingleton = TagSetSingleton.getInstance();
		TagSet tagSet = tagSetSingleton.getTagSet();

		for (Map.Entry<String, Integer> oneTagInfo : tagInfo.entrySet()) {
			tagSet.addTag(oneTagInfo.getKey(), oneTagInfo.getValue());
		}
	}

}
