package be.tomvertommen.persistence;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;

import org.apache.log4j.Logger;

import be.tomvertommen.drinksMachine.business.Status;
import be.tomvertommen.drinksMachine.tools.Config;
import be.tomvertommen.drinksMachine.tools.Convertor;
import be.tomvertommen.drinksMachine.tools.Property;

/**
 * Returns the {@link Status} from memory or from disc.
 * Allows to save the {@link Status} to memory or to disc.
 * Can only be instantiated once.
 * The path to the location where the {@link Status} is persisted, is configurable in application.properties (path)
 * @author Tom Vertommen
 *
 */
public class Dao {
	
	private static Dao instance;
	
	private Dao() {
		load();
	}
	
	public static synchronized Dao getInstance() {
		if(instance == null)
			instance = new Dao();
		return instance;
	}
	
	private static Logger logger = Logger.getLogger(Dao.class);
	
	private Status status;
	private String path = Config.getInstance().getProperty(Property.PATH);

	/**
	 * 
	 * @return the status
	 */
	public Status getStatus() {
		return status;
	}

	/**
	 * sets the status
	 * @param status
	 */
	public void setStatus(Status status) {
		this.status = status;
	}
	
	/**
	 * persists the {@link Status} to disc
	 */
	public void persist() {
		String s = Convertor.marshall(status, Status.class);
		PrintStream out;
		try {
			out = new PrintStream(new FileOutputStream(path));
		} catch (FileNotFoundException e) {
			logger.error("FileNotFoundException while creating printStream", e);
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		out.print(s);
		out.close();
	}
	
	/**
	 * {@link Dao#load(boolean)} with log set to true
	 */
	public void load() {
		load(true);
	}
	
	/**
	 * Loads the {@link Status} from disc.
	 * If no status is available one is instantiated, persisted and loaded.
	 * @param log if true the process is logged
	 */
	public void load(boolean log) {
		File file = new File(path);
		if(!file.exists()) {
			status = Status.getInstance();
			persist();
		}
	    BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader (file));
		} catch (FileNotFoundException e) {
			logger.error("FileNotFoundException while creating bufferedReader", e);
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	    String line = null;
	    StringBuilder stringBuilder = new StringBuilder();
	    String ls = System.getProperty("line.separator");
	    try {
	    	try {
		        while((line = reader.readLine()) != null) {
		            stringBuilder.append(line);
		            stringBuilder.append(ls);
		        }
	    	} catch (IOException e) {
	    		logger.error("IOException while reading line", e);
				e.printStackTrace();
				throw new RuntimeException(e);
			}
	    	if(log)
	    		logger.info("unmarshalling: " + stringBuilder.toString());
	        status = (Status)Convertor.unmarshall(stringBuilder.toString(), Status.class);
	    } finally {
	    	try {
	    		reader.close();
	    	} catch (IOException e) {
	    		logger.error("IOException while closing reader", e);
	    		throw new RuntimeException(e);
			}
	    }
	}

}
