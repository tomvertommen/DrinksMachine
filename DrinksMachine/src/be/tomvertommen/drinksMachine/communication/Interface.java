package be.tomvertommen.drinksMachine.communication;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import org.apache.log4j.Logger;

import be.tomvertommen.drinksMachine.DrinksMachine;
import be.tomvertommen.drinksMachine.IDrinksMachine;
import be.tomvertommen.drinksMachine.tools.Config;
import be.tomvertommen.drinksMachine.tools.Convertor;
import be.tomvertommen.drinksMachine.tools.Property;

/**
 * Makes the {@link IDrinksMachine} available for the client for XML over TCP/IP communication.
 * The port used is configurable in application.properties.
 * Runnable in a separate {@link Thread}
 * @author Tom Vertommen
 *
 */
public class Interface implements Runnable {
	
	private static Logger logger = Logger.getLogger(Interface.class);
	private IDrinksMachine drinksMachine = DrinksMachine.getInstance();
	
	private static Interface instance;
	
	private Interface() { }
	
	public static synchronized Interface getInstance() {
		if(instance == null)
			instance = new Interface();
		return instance;
	}
	
	private int port = Integer.valueOf(Config.getInstance().getProperty(Property.PORT));
	private ServerSocket serverSocket;
	private Socket socket;
	private OutputStream outPutstream;
	private PrintWriter out;
	private InputStream inputStream;
	private BufferedReader in;
	
	private void connect() {
		try {
			if(serverSocket != null)
				serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			if(socket != null)
				socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(out != null)
			out.close();
		try {
			if(in != null)
				in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			serverSocket = new ServerSocket(port);
			socket = serverSocket.accept();
			outPutstream = socket.getOutputStream();
			inputStream = socket.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		out = new PrintWriter(outPutstream, true);
        in = new BufferedReader(new InputStreamReader(inputStream));
	}

	/**
	 * Connects to the client and reconnects if necessary.
	 * Waits for {@link Request} messages serialized to xml with JAXB.
	 * Expects messages to be separated with {@value Constants#END_OF_MESSAGE}.
	 * Sends a serialized {@link Reply}
	 * 
	 */
	@Override
	public void run() {
		connect();
        while(true) {
    		String xml = "";
			try {
				String line = in.readLine() + System.getProperty("line.separator");
				while (!line.startsWith(Constants.END_OF_MESSAGE)) {
					xml += line + System.getProperty("line.separator");
					line = in.readLine();
				}
			} catch(NullPointerException e) {
				logger.warn("client disconnected; trying to reconnect", e);
				xml = null;
				connect();
			} catch(SocketException e) {
				logger.warn("client disconnected; trying to reconnect", e);
				xml = null;
				connect();
			} catch (IOException e) {
				logger.error("IOException while reading line", e);
				throw new RuntimeException(e);
			}
    		if(xml != null) {
    			Request request = null;
				request = (Request)Convertor.unmarshall(xml, Request.class);
				if(request.isLog())
					logger.info(xml);
				String reply = Convertor.marshall(handle(request), Reply.class);
				if(request.isLog())
					logger.info(reply);
				reply += Constants.END_OF_MESSAGE;
    			out.println(reply);
    		}
        }
	}
	
	private Reply handle(Request request) {
		String requestName = request.getName();
		if(RequestName.SET_MODE.name().equals(requestName)) {
			return drinksMachine.setMode(request.getMode());
		} else if(RequestName.ADD_INGREDIENT.name().equals(requestName)) {
			return drinksMachine.addIngredient(request.getIngredient());
		} else if(RequestName.REMOVE_INGREDIENT.name().equals(requestName)) {
			return drinksMachine.removeIngredient(request.getIngredient());
		} else if(RequestName.GET_STATUS.name().equals(requestName)) {
			return drinksMachine.getStatus(request.isForceLoad(), request.isLog());
		} else if(RequestName.SET_POWER.name().equals(requestName)) {
			return drinksMachine.setPower(request.isOn());
		} else if(RequestName.ADD_MAX_QTY.name().equals(requestName)) {
			return drinksMachine.addMaxQty(request.getIngredient());
		} else if(RequestName.REMOVE_MAX_QTY.name().equals(requestName)) {
			return drinksMachine.removeMaxQty(request.getIngredient());
		} else if(RequestName.ADD_ALARM_QTY.name().equals(requestName)) {
			return drinksMachine.addAlarmQty(request.getIngredient());
		} else if(RequestName.REMOVE_ALARM_QTY.name().equals(requestName)) {
			return drinksMachine.removeAlarmQty(request.getIngredient());
		} else if(RequestName.SET_PLUGGED_IN.name().equals(requestName)) {
			return drinksMachine.setPluggedIn(request.isOn());
		} else if(RequestName.SET_WATER.name().equals(requestName)) {
			return drinksMachine.setWater(request.isOn());
		} else if(RequestName.SET_CARD.name().equals(requestName)) {
			return drinksMachine.setCard(request.isOn());
		} else if(RequestName.PREPARE.name().equals(requestName)) {
			return drinksMachine.prepare(request.getDrink(), request.getSize(), request.getStrength());
		}
		
		Reply reply = new Reply();
		reply.setMessages(new String[] {"Request not handled; unknown request name: " + requestName});
		return reply;
	}

}
