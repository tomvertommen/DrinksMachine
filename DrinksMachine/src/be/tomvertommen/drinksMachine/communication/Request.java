package be.tomvertommen.drinksMachine.communication;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import be.tomvertommen.drinksMachine.IDrinksMachine;
import be.tomvertommen.drinksMachine.business.Drink;
import be.tomvertommen.drinksMachine.business.Status;

/**
 * Represents a request sent by the {@link IDrinksMachine} 
 * @author Tom Vertommen
 *
 */
@XmlRootElement(name="request")
@XmlAccessorType(XmlAccessType.FIELD)
public class Request {
	
	private String name;
	private boolean on;
	private String mode;
	private String drink;
	private String ingredient;
	private boolean forceLoad;
	private boolean log;
	private String size;
	private String strength;

	/**
	 * 
	 * @return the {@link Drink} size
	 */
	public String getSize() {
		return size;
	}

	/**
	 * sets the {@link Drink} size
	 * @param size
	 */
	public void setSize(String size) {
		this.size = size;
	}

	/**
	 * 
	 * @return the {@link Drink} strength
	 */
	public String getStrength() {
		return strength;
	}

	/**
	 * sets the {@link Drink} strength
	 * @param strength
	 */
	public void setStrength(String strength) {
		this.strength = strength;
	}

	/**
	 * 
	 * @return true if the {@link Status} should be force loaded from disc
	 */
	public boolean isForceLoad() {
		return forceLoad;
	}

	/**
	 * sets if loading the {@link Status} from disc should be forced
	 * @param forceLoad
	 */
	public void setForceLoad(boolean forceLoad) {
		this.forceLoad = forceLoad;
	}

	/**
	 * 
	 * @return the ingredient
	 */
	public String getIngredient() {
		return ingredient;
	}

	/**
	 * sets the ingredient
	 * @param ingredient
	 */
	public void setIngredient(String ingredient) {
		this.ingredient = ingredient;
	}

	/**
	 * 
	 * @return the {@link IDrinksMachine} mode
	 */
	public String getMode() {
		return mode;
	}

	/**
	 * sets the {@link IDrinksMachine} mode
	 * @param mode
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}

	/**
	 * 
	 * @return true if on
	 */
	public boolean isOn() {
		return on;
	}

	/**
	 * set on
	 * @param on
	 */
	public void setOn(boolean on) {
		this.on = on;
	}

	/**
	 * 
	 * @return the request name
	 */
	public String getName() {
		return name;
	}

	/**
	 * sets the request name
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 
	 * @return the drink
	 */
	public String getDrink() {
		return drink;
	}

	/**
	 * sets the drink
	 * @param drink
	 */
	public void setDrink(String drink) {
		this.drink = drink;
	}

	/**
	 * 
	 * @return if logging is needed while handling the request
	 */
	public boolean isLog() {
		return log;
	}

	/**
	 * sets if logging is needed while handling the request
	 * @param log
	 */
	public void setLog(boolean log) {
		this.log = log;
	}
	
}
