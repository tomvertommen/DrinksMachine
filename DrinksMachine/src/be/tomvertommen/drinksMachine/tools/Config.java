package be.tomvertommen.drinksMachine.tools;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * Represents the global configuration.
 * Can only be instantiated once.
 * Provides access to the configured properties.
 * @author Tom Vertommen
 *
 */
public class Config {
	
	private static Logger logger = Logger.getLogger(Config.class);
	private static Config instance;
	private Properties properties;
	
	private Config() {
		properties = new Properties();
		InputStream is = getClass().getClassLoader().getResourceAsStream("application.properties");
		try {
			properties.load(is);
		} catch (IOException e) {
			logger.error("IOException while loading properties", e);
		}
	}
	
	public static synchronized Config getInstance() {
		if(instance == null)
			instance = new Config();
		return instance;
	}
	
	/**
	 * 
	 * @param key
	 * @return the configured property value for the given key
	 */
	public String getProperty(String key) {
		return properties.getProperty(key);
	}

}
