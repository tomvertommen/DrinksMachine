package be.tomvertommen.drinksMachine.communication;

/**
 * The names of all possible requests.
 * @author Tom Vertommen
 *
 */
public enum RequestName {
	
	SET_MODE,
	GET_DRINK,
	ADD_INGREDIENT,
	REMOVE_INGREDIENT,
	ADD_MAX_QTY,
	REMOVE_MAX_QTY,
	ADD_ALARM_QTY,
	REMOVE_ALARM_QTY,
	GET_STOCK_QTYS,
	GET_STATUS,
	SET_POWER,
	SET_PLUGGED_IN,
	SET_WATER,
	SET_CARD,
	PREPARE;
	
}
