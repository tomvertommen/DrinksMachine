package be.tomvertommen.drinksMachine.business;

import java.util.Arrays;
import java.util.Comparator;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author Tom Vertommen
 *
 */
@XmlRootElement(name="ingredient")
@XmlAccessorType(XmlAccessType.FIELD)
public enum Ingredient {
	
	EARL_GRAY_TEA("Earl gray tea"), 
	BLACK_TEA("Black tea"), 
	CARROT_SOUP("Carrot soup"), 
	CITRUS_TEA("Citrus tea"), 
	COFFEE("Coffee"), 
	COLA_SYRUP("Cola syrup"), 
	COLA_LIGHT_SYRUP("Cola light syrup"), 
	TOMATO_SOUP("Tomato soup"), 
	PUMPKIN_SOUP("Pumpkin soup"), 
	COCOA("Cocoa"), 
	CAPPUCCINO("Cappuccino"), 
	DECA("Deca");
	
	private String guiName;
	
	private Ingredient(String guiName) {
		this.guiName = guiName;
	}
	
	/**
	 * 
	 * @return the GUI friendly name
	 */
	public String getGuiName() {
		return this.guiName;
	}
	
	/**
	 * 
	 * @return all possible ingredients sorted alfabetically by guiName 
	 */
	public static Ingredient[] valuesSorted() {
		Ingredient[] ingredients = Ingredient.values();
		Arrays.sort(ingredients, new Comparator<Ingredient>() {
			@Override
			public int compare(Ingredient o1, Ingredient o2) {
				return o1.guiName.compareToIgnoreCase(o2.guiName);
			}
		});
		return ingredients;
	}

}
