package be.tomvertommen.drinksMachine.business;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author Tom Vertommen
 *
 * Represents a drink
 *
 */
@XmlRootElement(name="drink")
@XmlAccessorType(XmlAccessType.FIELD)
public class Drink {
	
	public Drink() {}

	private String id;
	private String name;
	
	@XmlElement(name = "price")
	private double[] prices;
	
	@XmlElement(name = "size")
	private Size[] sizes;
	
	@XmlElement(name = "strength")
	private Strength[] strengths;
	
	@XmlElement(name = "ingredient")
	private Ingredient[] ingredients;
	
	private Map<Ingredient, Integer> maxNeededIngredients = new HashMap<Ingredient, Integer>();

	public Drink(String id, String name, Size[] sizes, Strength[] strengths, Ingredient[] ingredients, double[] prices) {
		this.id = id;
		this.name = name;
		this.sizes = sizes;
		this.strengths = strengths;
		this.ingredients = ingredients;
		this.prices = prices;
		for(Ingredient ingredient : ingredients) {
			if(maxNeededIngredients.containsKey(ingredient))
				maxNeededIngredients.put(ingredient, maxNeededIngredients.get(ingredient) + 1);
			else
				maxNeededIngredients.put(ingredient, 1);
		}
		int maxSizeOrdinal = 0;
		for(Size size : this.sizes) {
			maxSizeOrdinal = Math.max(maxSizeOrdinal, size.ordinal());
		}
		int maxStrengthOrdinal = 0;
		for(Strength strength : strengths) {
			maxStrengthOrdinal = Math.max(maxStrengthOrdinal, strength.ordinal());
		}
		for(Map.Entry<Ingredient, Integer> entry : maxNeededIngredients.entrySet()) {
			entry.setValue(entry.getValue() * (maxSizeOrdinal + 1) * (maxStrengthOrdinal + 1));
		}
	}

	/**
	 * 
	 * @return the possible sizes
	 */
	public Size[] getSizes() {
		return sizes;
	}

	/**
	 * 
	 * @return the GUI friendly name
	 */
	public String getName() {
		return name;
	}

	/**
	 * 
	 * @return the program friendly id (no spaces, special characters,...)
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * 
	 * @return the possible strengths
	 */
	public Strength[] getStrengths() {
		return strengths;
	}

	/**
	 * 
	 * @return the ingredients needed for this drink in it's smallest size and strength
	 */
	public Ingredient[] getIngredients() {
		return ingredients;
	}
	
	/**
	 * 
	 * @param size
	 * @param strength
	 * @return the ingredients needed for this drink with the given size and strength
	 */
	public Map<Ingredient, Integer> getIngredients(Size size, Strength strength) {
		Map<Ingredient, Integer> ingredients = new HashMap<Ingredient, Integer>();
		for(Ingredient ingredient : this.ingredients) {
			ingredients.put(ingredient, ingredients.containsKey(ingredient) ? ingredients.get(ingredient) + 1 : 1);
		}
		for(Map.Entry<Ingredient, Integer> entry : ingredients.entrySet()) {
			entry.setValue(entry.getValue() * (size.ordinal() + 1) * (strength.ordinal() + 1));
		}
		return ingredients;
	}

}
