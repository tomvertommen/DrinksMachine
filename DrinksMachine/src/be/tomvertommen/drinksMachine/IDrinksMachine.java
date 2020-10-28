package be.tomvertommen.drinksMachine;

import be.tomvertommen.drinksMachine.business.Status;
import be.tomvertommen.drinksMachine.communication.Reply;

/**
 * 
 * @author Tom Vertommen
 *
 */
public interface IDrinksMachine {

	/**
	 * sets drinkMachine mode (READY or MAINTENANCE)
	 * @param mode
	 * @return {@link Reply}
	 */
	Reply setMode(String mode);
	
	/**
	 * adds 1 to the current qty of the given ingredient
	 * @param ingredient
	 * @return {@link Reply}
	 */
	Reply addIngredient(String ingredient);
	
	/**
	 * subtracts 1 of the current qty of the given ingredient
	 * @param ingredient
	 * @return {@link Reply}
	 */
	Reply removeIngredient(String ingredient);
	
	/**
	 * adds 1 to the max qty of the given ingredient
	 * @param ingredient
	 * @return {@link Reply}
	 */
	Reply addMaxQty(String ingredient);
	
	/**
	 * subtracts 1 of the max qty of the given ingredient
	 * @param ingredient
	 * @return {@link Reply}
	 */
	Reply removeMaxQty(String ingredient);
	
	/**
	 * adds 1 to the alarm qty of the given ingredient
	 * @param ingredient
	 * @return {@link Reply}
	 */
	Reply addAlarmQty(String ingredient);
	
	/**
	 * subtracts 1 of the alarm qty of the given ingredient
	 * @param ingredient
	 * @return {@link Reply}
	 */
	Reply removeAlarmQty(String ingredient);
	
	/**
	 * returns the {@link Status} of the drinksMachine
	 * @param forceLoad forces loading the {@link Status} from the persistent store or not
	 * @param log turns on or off logging inside this method
	 * @return {@link Reply}
	 */
	Reply getStatus(boolean forceLoad, boolean log);
	
	/**
	 * sets power
	 * @param on
	 * @return {@link Reply}
	 */
	Reply setPower(boolean on);
	
	/**
	 * sets pluggedIn
	 * @param on
	 * @return {@link Reply}
	 */
	Reply setPluggedIn(boolean on);
	
	/**
	 * sets water connection
	 * @param on
	 * @return {@link Reply}
	 */
	Reply setWater(boolean on);
	
	/**
	 * inserts or removes bank card
	 * @param on
	 * @return {@link Reply}
	 */
	Reply setCard(boolean on);
	
	/**
	 * prepares the given drink with given size and strength
	 * @param drink
	 * @param size
	 * @param strength
	 * @return {@link Reply}
	 */
	Reply prepare(String drink, String size, String strength);
	
}
