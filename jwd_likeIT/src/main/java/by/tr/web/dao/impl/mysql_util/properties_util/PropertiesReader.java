package by.tr.web.dao.impl.mysql_util.properties_util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.tr.web.dao.impl.mysql_util.mysql_exception.MySqlException;
import by.tr.web.dao.impl.mysql_util.mysql_exception.MySqlFatalException;

public class PropertiesReader {
	
	private String DATABASE_CONFIG = "database.properties";
	private String POOL_CONFIG = "pool.properties";

	private final static Logger logger = LogManager.getLogger();

	public Properties readDatabaseProperties() throws MySqlFatalException {
		try (FileInputStream input = new FileInputStream(getFilePath(DATABASE_CONFIG))) {
			Properties properties = new Properties();
			properties.load(input);
			return properties;
		} catch (IOException ex) {
			logger.fatal("Can't read database properties");
			throw new MySqlFatalException("Can't read database properties");
		}
	}

	public Properties readPoolProperties() throws MySqlException {
		try (FileInputStream input = new FileInputStream(getFilePath(POOL_CONFIG))) {
			Properties properties = new Properties();
			properties.load(input);
			return properties;
		} catch (IOException ex) {
			logger.error("Can't read connection pool properties");
			throw new MySqlException("Can't read connection pool properties");
		}
	}

	private String getFilePath(String filename) {

		ClassLoader classLoader = this.getClass().getClassLoader();
		String trueFilePath = classLoader.getResource(filename).getFile();

		return trueFilePath;
	}

}
