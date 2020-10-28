package be.tomvertommen.drinksMachine;

import java.util.Map;

import org.apache.log4j.Logger;

import be.tomvertommen.drinksMachine.business.Drink;
import be.tomvertommen.drinksMachine.business.Ingredient;
import be.tomvertommen.drinksMachine.business.Mode;
import be.tomvertommen.drinksMachine.business.Size;
import be.tomvertommen.drinksMachine.business.Status;
import be.tomvertommen.drinksMachine.business.StockEntry;
import be.tomvertommen.drinksMachine.business.Strength;
import be.tomvertommen.drinksMachine.communication.Reply;
import be.tomvertommen.persistence.Dao;

/**
 * 
 * @author Tom Vertommen
 *
 */
public class DrinksMachine implements IDrinksMachine {

	private Dao dao = Dao.getInstance();
	
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(DrinksMachine.class);
	
	private static DrinksMachine instance;
	
	public synchronized static DrinksMachine getInstance(){
		if(instance == null)
			instance = new DrinksMachine();
		return instance;
	}

	/**
	 * @see IDrinksMachine#setMode(String)
	 */
	@Override
	public synchronized Reply setMode(String mode) {
		Status status = dao.getStatus();
		status.setMode(mode);
		if(!status.getMode().equals(Mode.READY.name()))
			status.setCard(false);
		dao.persist();
		Reply reply = Reply.getSuccessReply();
		reply.setStatus(status);
		return reply;
	}

	/**
	 * @see IDrinksMachine#addIngredient(String)
	 */
	@Override
	public synchronized Reply addIngredient(String ingredient) {
		Status status = dao.getStatus();
		StockEntry stockEntry = status.getStockEntry(ingredient);
		if(stockEntry.getCurrent() < stockEntry.getMax()) {
			stockEntry.addCurrent(1);
			dao.persist();
			Reply reply = Reply.getSuccessReply();
			reply.setStatus(status);
			return reply;
		}
		return Reply.getFailedReply(new String[] {"Current qty not increased", "Current qty can not be bigger than max qty."});
	}

	/**
	 * @see IDrinksMachine#removeIngredient(String)
	 */
	@Override
	public synchronized Reply removeIngredient(String ingredient) {
		Status status = dao.getStatus();
		StockEntry stockEntry = status.getStockEntry(ingredient);
		if(stockEntry.getCurrent() > 0) {
			stockEntry.addCurrent(-1);
			dao.persist();
			Reply reply = Reply.getSuccessReply();
			reply.setStatus(status);
			return reply;
		}
		return Reply.getFailedReply(new String[] {"Current qty not decreased.", "Current qty can not be smaller than 0."});
	}

	/**
	 * @see IDrinksMachine#addMaxQty(String)
	 */
	@Override
	public synchronized Reply addMaxQty(String ingredient) {
		Status status = dao.getStatus();
		status.getStockEntry(ingredient).addMax(1);
		dao.persist();
		Reply reply = Reply.getSuccessReply();
		reply.setStatus(status);
		return reply;
	}

	/**
	 * @see IDrinksMachine#removeMaxQty(String)
	 */
	@Override
	public synchronized Reply removeMaxQty(String ingredient) {
		Status status = dao.getStatus();
		StockEntry stockEntry = status.getStockEntry(ingredient);
		if(stockEntry.getCurrent() <= stockEntry.getMax() - 1) {
			stockEntry.addMax(-1);
			dao.persist();
			Reply reply = Reply.getSuccessReply();
			reply.setStatus(status);
			return reply;
		}
		return Reply.getFailedReply(new String[] {"Max qty not decreased.", "Max qty can not be smaller than current qty."});
	}

	/**
	 * @see IDrinksMachine#addAlarmQty(String)
	 */
	@Override
	public synchronized Reply addAlarmQty(String ingredient) {
		Status status = dao.getStatus();
		status.getStockEntry(ingredient).addAlarm(1);
		dao.persist();
		Reply reply = Reply.getSuccessReply();
		reply.setStatus(status);
		return reply;
	}

	/**
	 * @see IDrinksMachine#removeAlarmQty(String)
	 */
	@Override
	public synchronized Reply removeAlarmQty(String ingredient) {
		Status status = dao.getStatus();
		StockEntry stockEntry = status.getStockEntry(ingredient);
		if(stockEntry.getAlarm() > 0) {
			stockEntry.addAlarm(-1);
			dao.persist();
			Reply reply = Reply.getSuccessReply();
			reply.setStatus(status);
			return reply;
		}
		return Reply.getFailedReply(new String[] {"Alarm qty not decreased.", "Alarm qty can not be smaller than 0."});
	}

	/**
	 * @see IDrinksMachine#getStatus(boolean, boolean)
	 */
	@Override
	public synchronized Reply getStatus(boolean forceLoad, boolean log) {
		if(forceLoad)
			dao.load(log);
		Reply reply = Reply.getSuccessReply();
		reply.setStatus(dao.getStatus());
		return reply;
	}

	/**
	 * @see IDrinksMachine#setPower(boolean)
	 */
	@Override
	public synchronized Reply setPower(boolean on) {
		Reply reply = Reply.getSuccessReply();
		Status status = dao.getStatus();
		if(status.isPluggedIn() && (status.isPower() != on)) {
			status.setPower(on);
			if(status.isPower()) {
				if(status.isWater())
					status.setMode(Mode.READY.name());
				else
					status.setMode(Mode.MAINTENANCE.name());
			}
			if(!status.isPower())
				status.setCard(false);
			dao.persist();
		}
		reply.setStatus(status);
		return reply;
	}

	/**
	 * @see IDrinksMachine#setPluggedIn(boolean)
	 */
	@Override
	public synchronized Reply setPluggedIn(boolean on) {
		Reply reply = Reply.getSuccessReply();
		Status status = dao.getStatus();
		if(on != status.isPluggedIn()) {
			status.setPluggedIn(on);
			if(!status.isPluggedIn())
				status.setPower(false);
			dao.persist();
		}
		reply.setStatus(status);
		return reply;
	}

	/**
	 * @see IDrinksMachine#setWater(boolean)
	 */
	@Override
	public synchronized Reply setWater(boolean on) {
		Reply reply = Reply.getSuccessReply();
		Status status = dao.getStatus();
		if(on != status.isWater()) {
			status.setWater(on);
			if(!status.isWater()) {
				status.setMode(Mode.MAINTENANCE.name());
				status.setCard(false);
			}
			dao.persist();
		}
		reply.setStatus(status);
		return reply;
	}

	/**
	 * @see IDrinksMachine#setCard(boolean)
	 */
	@Override
	public synchronized Reply setCard(boolean on) {
		Reply reply = Reply.getSuccessReply();
		Status status = dao.getStatus();
		if(status.isPluggedIn() && status.isPower() && status.isWater() && status.getMode().equals(Mode.READY.name())) {
			status.setCard(on);
			dao.persist();
		}
		reply.setStatus(status);
		return reply;
	}

	/**
	 * @see IDrinksMachine#prepare(String, String, String)
	 */
	@Override
	public synchronized Reply prepare(String drinkId, String size, String strength) {
		Reply reply = Reply.getSuccessReply();
		Status status = dao.getStatus();
		for(Drink drink : status.getDrinks()) {
			if(drink.getId().equals(drinkId)) {
				for(Map.Entry<Ingredient, Integer> entry : drink.getIngredients(Size.valueOf(size), Strength.valueOf(strength)).entrySet()) {
					status.getStockEntry(entry.getKey().name()).addCurrent(-entry.getValue());
				}
			}
		}
		dao.persist();
		reply.setStatus(status);
		return reply;
	}
	
}
