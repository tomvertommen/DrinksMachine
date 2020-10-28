package be.tomvertommen.drinksMachine;

import org.apache.log4j.Logger;

import be.tomvertommen.drinksMachine.communication.Interface;
import be.tomvertommen.drinksMachine.tools.Notifier;

/**
 * 
 * @author Tom Vertommen
 * Main entry point for the drinksMachine application.
 * Starts the {@link Interface}
 * Starts the {@link Notifier}
 */
public class Main {
	
	private static Logger logger = Logger.getLogger(Main.class);

	public static void main(String[] args) {
		new Thread(Interface.getInstance()).start();
		new Thread(Notifier.getInstance()).start();;
		logger.info("drinksmachine up and running");
	}

}
