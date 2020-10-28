package be.tomvertommen.drinksMachine.business;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import be.tomvertommen.drinksMachine.IDrinksMachine;

/**
 * The {@link IDrinksMachine} status.
 * Can be persisted by using JAXB.
 * Can only be instantiated once.
 * Is initialized with stockEntries for all ingredients {@link Ingredient} without {@link StockEntry}.
 * Is initialized with all possible drinks {@link Drink}
 * @author Tom Vertommen
 *
 */
@XmlRootElement(name="status")
@XmlAccessorType(XmlAccessType.FIELD)
public class Status {
	
	private Status() {
		for(Ingredient ingredient : Ingredient.values()) {
			if(getStockEntry(ingredient.name()) == null) {
				StockEntry stockEntry = new StockEntry();
				stockEntry.setCurrent(20);
				stockEntry.setAlarm(40);
				stockEntry.setMax(100);
				stockEntry.setIngredient(ingredient.name());
				stockEntries.add(stockEntry);
			}
		}
		drinks = new Drink[] {
				new Drink(
						"coffee", 
						"Coffee", 
						new Size[] {Size.REGULAR, Size.LARGE, Size.XXL},
						new Strength[] {Strength.REGULAR, Strength.STRONG, Strength.VERY_STRONG},
						new Ingredient[] {Ingredient.COFFEE},
						new double[] {1.5, 2, 2.5}),
				new Drink(
						"blackTea", 
						"Black tea", 
						new Size[] {Size.REGULAR, Size.LARGE},
						new Strength[] {Strength.REGULAR, Strength.STRONG, Strength.VERY_STRONG},
						new Ingredient[] {Ingredient.BLACK_TEA},
						new double[] {1.8, 2.3}),
				new Drink(
						"carrotSoup",
						"Carrot soup",
						new Size[] {Size.REGULAR, Size.LARGE},
						new Strength[] {Strength.REGULAR},
						new Ingredient[] {Ingredient.CARROT_SOUP},
						new double[] {2, 2.5}),
				new Drink(
						"tomatoSoup",
						"Tomato Soup",
						new Size[] {Size.REGULAR, Size.LARGE},
						new Strength[] {Strength.REGULAR},
						new Ingredient[] {Ingredient.TOMATO_SOUP},
						new double[] {2, 2.5}),
				new Drink(
						"pumpkinSoup",
						"Pumpkin soup",
						new Size[] {Size.REGULAR, Size.LARGE},
						new Strength[] {Strength.REGULAR},
						new Ingredient[] {Ingredient.PUMPKIN_SOUP},
						new double[] {2, 2.5}),
				new Drink(
						"earlGrayTea",
						"Earl gray tea",
						new Size[] {Size.REGULAR, Size.LARGE},
						new Strength[] {Strength.REGULAR},
						new Ingredient[] {Ingredient.EARL_GRAY_TEA},
						new double[] {1.8, 2.3}),
				new Drink(
						"decafeine",
						"Decafeine",
						new Size[] {Size.REGULAR, Size.LARGE, Size.XXL},
						new Strength[] {Strength.REGULAR, Strength.STRONG, Strength.VERY_STRONG},
						new Ingredient[] {Ingredient.DECA},
						new double[] {1.5, 2, 2.5}),
				new Drink(
						"colaLight",
						"Cola light",
						new Size[] {Size.REGULAR, Size.LARGE, Size.XXL},
						new Strength[] {Strength.REGULAR},
						new Ingredient[] {Ingredient.COLA_LIGHT_SYRUP},
						new double[] {1.6, 2.2, 2.6}),
				new Drink(
						"cola",
						"Cola",
						new Size[] {Size.REGULAR, Size.LARGE, Size.XXL},
						new Strength[] {Strength.REGULAR},
						new Ingredient[] {Ingredient.COLA_SYRUP},
						new double[] {1.6, 2.2, 2.6}),
				new Drink(
						"citrusTea",
						"Citrus tea",
						new Size[] {Size.REGULAR, Size.LARGE},
						new Strength[] {Strength.REGULAR},
						new Ingredient[] {Ingredient.CITRUS_TEA},
						new double[] {1.8, 2.3}),
				new Drink(
						"chocolateMilk",
						"Chocolate milk",
						new Size[] {Size.REGULAR, Size.LARGE, Size.XXL},
						new Strength[] {Strength.REGULAR, Strength.STRONG},
						new Ingredient[] {Ingredient.COCOA},
						new double[] {1.9, 2.3, 2.7}),
				new Drink(
						"cappuccino",
						"Cappuccino",
						new Size[] {Size.REGULAR, Size.LARGE, Size.XXL},
						new Strength[] {Strength.REGULAR, Strength.STRONG, Strength.VERY_STRONG},
						new Ingredient[] {Ingredient.CAPPUCCINO, Ingredient.COFFEE},
						new double[] {1.7, 2.2, 2.7})
		};
	}
	
	private static Status instance;
	
	public static synchronized Status getInstance() {
		if(instance == null)
			instance = new Status();
		return instance;
	}
	
	@XmlElement(name = "stockEntry")
	private List<StockEntry> stockEntries = new ArrayList<StockEntry>();
	
	@XmlElement(name = "drinks")
	private Drink[] drinks;

	private boolean pluggedIn;
	private boolean power;
	private boolean water;
	private String mode = Mode.READY.name();
	private boolean card;
	
	@SuppressWarnings("unused")
	private Size[] sizes = Size.values();
	
	@SuppressWarnings("unused")
	private Strength[] strengths = Strength.values();

	/**
	 * 
	 * @return pluggedIn status
	 */
	public boolean isPluggedIn() {
		return pluggedIn;
	}

	/**
	 * sets pluggedIn status
	 * @param pluggedIn
	 */
	public void setPluggedIn(boolean pluggedIn) {
		this.pluggedIn = pluggedIn;
	}

	/**
	 * 
	 * @return the stockEntries
	 */
	public List<StockEntry> getStockEntries() {
		return stockEntries;
	}

	/**
	 * sets the stockEntries
	 * @param stockEntries
	 */
	public void setStockEntries(List<StockEntry> stockEntries) {
		this.stockEntries = stockEntries;
	}

	/**
	 * 
	 * @return {@link IDrinksMachine} mode
	 */
	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}
	
	/**
	 * 
	 * @param ingredient
	 * @return the stockEntry for the given ingredient
	 */
	public StockEntry getStockEntry(String ingredient) {
		for(StockEntry stockEntry : stockEntries) {
			if(stockEntry.getIngredient().equals(ingredient))
				return stockEntry;
		}
		return null;
	}

	/**
	 * 
	 * @return the power status
	 */
	public boolean isPower() {
		return power;
	}

	/**
	 * sets the power status
	 * @param power
	 */
	public void setPower(boolean power) {
		this.power = power;
	}

	/**
	 * 
	 * @return the water status
	 */
	public boolean isWater() {
		return water;
	}

	/**
	 * sets the water status
	 * @param water
	 */
	public void setWater(boolean water) {
		this.water = water;
	}
	
	/**
	 * 
	 * @return all possible drinks
	 */
	public Drink[] getDrinks() {
		return drinks;
	}

	/**
	 * 
	 * @return the bank card status
	 */
	public boolean isCard() {
		return card;
	}

	/**
	 * sets the bank card status
	 * @param card
	 */
	public void setCard(boolean card) {
		this.card = card;
	}
	
	/**
	 * 
	 * @return true if alarm level is reached for one or more stockEntry
	 */
	public boolean alarmLevelReached() {
		for(StockEntry stockEntry : stockEntries) {
			if(stockEntry.getAlarm() >= stockEntry.getCurrent()) {
				return true;
			}
		}
		return false;
	}

}
