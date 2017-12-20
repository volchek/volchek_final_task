package by.tr.web.dao.impl.properties_util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesReader {

	private Properties properties;
	private String DATABASE_CONFIG = "database.properties";

	public Properties readDatabaseProperties() {
		try (FileInputStream input = new FileInputStream(getFilePath(DATABASE_CONFIG))) {
			properties = new Properties();
			properties.load(input);
		} catch (IOException ex) {
			System.out.println("CAN'T READ INPUT");
		}
		return properties;
	}

	private String getFilePath(String filename) {

		ClassLoader classLoader = this.getClass().getClassLoader();
		String trueFilePath = classLoader.getResource(filename).getFile();

		return trueFilePath;
	}

}
