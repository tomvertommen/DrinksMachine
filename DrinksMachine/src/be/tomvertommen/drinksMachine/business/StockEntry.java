package be.tomvertommen.drinksMachine.business;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Maintains the stock qtys for an ingredient.
 * @author Tom Vertommen
 *
 */
@XmlRootElement(name="stockEntry")
@XmlAccessorType(XmlAccessType.FIELD)
public class StockEntry {
	
	private int current;
	private int max;
	private int alarm;
	private String ingredient;

	/**
	 * 
	 * @return the ingredient name
	 */
	public String getIngredient() {
		return ingredient;
	}

	/**
	 * sets the ingredient name
	 * @param ingredient
	 */
	public void setIngredient(String ingredient) {
		this.ingredient = ingredient;
	}
	
	/**
	 * 
	 * @return the current qty
	 */
	public int getCurrent() {
		return current;
	}
	
	/**
	 * sets the current qty
	 * @param current
	 */
	public void setCurrent(int current) {
		this.current = current;
	}
	
	/**
	 * adds given qty to the current qty
	 * @param quantity
	 */
	public void addCurrent(int quantity) {
		this.current += quantity;
	}
	
	/**
	 * An ingredient cannot be filled beyond it's max qty
	 * @return the max qty
	 */
	public int getMax() {
		return max;
	}
	
	/**
	 * sets the max qty
	 * @param max
	 */
	public void setMax(int max) {
		this.max = max;
	}
	
	/**
	 * adds the given qty to the max qty
	 * @param quantity
	 */
	public void addMax(int quantity) {
		this.max += quantity;
	}
	
	/**
	 * if the current qty goes below the alarm qty notification emails are sent periodically
	 * @return the alrm qty
	 */
	public int getAlarm() {
		return alarm;
	}
	
	/**
	 * sets the alarm qty
	 * @param alarm
	 */
	public void setAlarm(int alarm) {
		this.alarm = alarm;
	}
	
	/**
	 * adds the given qty to the alarm qty
	 * @param quantity
	 */
	public void addAlarm(int quantity) {
		this.alarm += quantity;
	}

}
