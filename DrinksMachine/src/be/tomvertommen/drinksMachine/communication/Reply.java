package be.tomvertommen.drinksMachine.communication;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import be.tomvertommen.drinksMachine.IDrinksMachine;
import be.tomvertommen.drinksMachine.business.Drink;
import be.tomvertommen.drinksMachine.business.Status;

/**
 * Represents a reply sent by the {@link IDrinksMachine} after processing a {@link Request}
 * @author Tom Vertommen
 *
 */
@XmlRootElement(name="reply")
@XmlAccessorType(XmlAccessType.FIELD)
public class Reply {
	
	private boolean success;
	private String[] messages;
	private Status status;
	
	@XmlElement(name = "drink")
	private List<Drink> drinks;

	/**
	 * 
	 * @return drinks
	 */
	public List<Drink> getDrinks() {
		return drinks;
	}

	/**
	 * sets drinks
	 * @param drinks
	 */
	public void setDrinks(List<Drink> drinks) {
		this.drinks = drinks;
	}

	/**
	 * 
	 * @return messages
	 */
	public String[] getMessages() {
		return messages;
	}

	/**
	 * sets messages
	 * @param messages
	 */
	public void setMessages(String[] messages) {
		this.messages = messages;
	}

	/**
	 * 
	 * @return true if the request was successfully processed
	 */
	public boolean isSuccess() {
		return success;
	}

	/**
	 * sets if a request was successfully processed
	 * @param success
	 */
	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	/**
	 * 
	 * @return a reply with succes set to true
	 */
	public static Reply getSuccessReply() {
		Reply reply = new Reply();
		reply.setSuccess(true);
		return reply;
	}
	
	/**
	 * 
	 * @param message
	 * @return a reply with success set to false and the given message added to the messages
	 */
	public static Reply getFailedReply(String message) {
		Reply reply = new Reply();
		reply.setMessages(new String[] {message});
		return reply;
	}
	
	/**
	 * 
	 * @param messages
	 * @return a reply with success set to false and the given messages set as the messages
	 */
	public static Reply getFailedReply(String[] messages) {
		Reply reply = new Reply();
		reply.setMessages(messages);
		return reply;
	}

	/**
	 * 
	 * @return the {@link IDrinksMachine} status
	 */
	public Status getStatus() {
		return status;
	}

	/**
	 * sets the {@link IDrinksMachine} status
	 * @param status
	 */
	public void setStatus(Status status) {
		this.status = status;
	}

}
