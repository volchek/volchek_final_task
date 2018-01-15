package by.tr.web.dao.impl;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.tr.web.dao.TagDao;
import by.tr.web.dao.exception.DaoException;
import by.tr.web.dao.exception.FatalDaoException;
import by.tr.web.dao.impl.mysql_util.MySQLTagQuery;
import by.tr.web.dao.impl.mysql_util.mysql_exception.MySqlException;
import by.tr.web.dao.impl.mysql_util.pool.ConnectionPool;
import by.tr.web.dao.impl.mysql_util.pool.ConnectionPoolFactory;
import by.tr.web.entity.tag.TagSet;
import by.tr.web.entity.tag.TagSetSingleton;

public class MySQLTagDaoImpl implements TagDao {

	private final static ConnectionPoolFactory poolFactory = ConnectionPoolFactory.getInstance();
	private final static ConnectionPool connPool = poolFactory.getConnectionPool();

	private final static Logger logger = LogManager.getLogger(MySQLTagDaoImpl.class.getName());

	@Override
	public List<String> getTagList() throws DaoException, FatalDaoException {

		Connection conn = null;

		try {
			conn = connPool.getConnection();
			MySQLTagQuery query = new MySQLTagQuery();

			List<String> tags = query.getTagNames(conn);
			return tags;

		} catch (MySqlException ex) {
			logger.error("Can't execute query and get a tag list", ex);
		} finally {
			connPool.closeConnection(conn);
		}

		return null;
	}

	@Override
	public Map<String, Integer> getAllTagInfo() throws DaoException, FatalDaoException {

		Connection conn = null;

		try {
			conn = connPool.getConnection();
			MySQLTagQuery query = new MySQLTagQuery();

			Map<String, Integer> tags = query.getAllTagInfo(conn);
			fillTagMaps(tags);

			return tags;

		} catch (MySqlException ex) {
			logger.error("Can't execute query and get information about all tags", ex);
		} finally {
			connPool.closeConnection(conn);
		}

		return null;
	}

	private void fillTagMaps(Map<String, Integer> tagInfo) throws DaoException {

		if (tagInfo == null) {
			throw new DaoException("Error");
		}

		TagSetSingleton tagSetSingleton = TagSetSingleton.getInstance();
		TagSet tagSet = tagSetSingleton.getTagSet();

		for (Map.Entry<String, Integer> oneTagInfo : tagInfo.entrySet()) {
			tagSet.addTag(oneTagInfo.getKey(), oneTagInfo.getValue());
		}

	}

}
