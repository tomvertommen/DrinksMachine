package be.tomvertommen.drinksMachine.tools;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

import be.tomvertommen.drinksMachine.DrinksMachine;
import be.tomvertommen.drinksMachine.IDrinksMachine;
import be.tomvertommen.drinksMachine.business.Ingredient;
import be.tomvertommen.drinksMachine.business.Status;
import be.tomvertommen.drinksMachine.business.StockEntry;
import be.tomvertommen.drinksMachine.communication.Reply;

/**
 * Checks if {@link StockEntry} current qtys go below alarm qtys.
 * If this is the case notification mails are sent to the configured emailaddress.
 * Runnable in a separate {@link Thread}.
 * Following properties are configurable in application.properties :
 * password : email account password
 * sleep : hour(s) between checking
 * to : the recipient emailaddress
 * @author Tom Vertommen
 *
 */
public class Notifier implements Runnable {
	
	private static Notifier instance;
	
	private Notifier() { }
	
	public static Notifier getInstance() {
		if(instance == null)
			instance = new Notifier();
		return instance;
	}
	
	private static Logger logger = Logger.getLogger(Notifier.class);
	private Config config = Config.getInstance();
	private IDrinksMachine drinksMachine = DrinksMachine.getInstance();
	private String host = "smtp.gmail.com";
	private String from = "tom.vertommen@gmail.com";
	private String pass = config.getProperty(Property.PASSWORD);
    private String startTTLS = "true";
    private String port = "587";
    private String smtpAuth = "true";
    private String transport = "smtp";

    /**
     * Checks every x hours (configurable) if {@link StockEntry} current qtys go below alarm qtys.
     * If this is the case notification mails are sent to the configured emailaddress.
     */
	@Override
	public void run() {
		while(true) {
			if(Boolean.valueOf(config.getProperty(Property.ENABLE_NOTIFIER)))
				checkAndNotify();
			else
				logger.info("Notifier disabled");
			try {
				Thread.sleep(Integer.parseInt(config.getProperty(Property.SLEEP)) * 60 * 60 * 1000);
			} catch (InterruptedException e) {
				logger.error("InterruptedException while sleeping", e);
			}
		}
	}
    
	private void checkAndNotify() {
		logger.info("checking");
		Status status = drinksMachine.getStatus(false, false).getStatus();
		if(status.alarmLevelReached()) {
			logger.info("alarm level reached; sending notification");
			
	        Properties props = System.getProperties();
	        props.put("mail.smtp.starttls.enable", startTTLS);
	        props.put("mail.smtp.host", host);
	        props.put("mail.smtp.user", from);
	        props.put("mail.smtp.password", pass);
	        props.put("mail.smtp.port", port);
	        props.put("mail.smtp.auth", smtpAuth);

	        String[] to = {config.getProperty(Property.TO)};
	        
	        Session session = Session.getDefaultInstance(props, null);
	        MimeMessage message = new MimeMessage(session);
	        try {
				message.setFrom(new InternetAddress(from));
			} catch (AddressException e) {
				logger.error("AddressException while creating internetAddress", e);
			} catch (MessagingException e) {
				logger.error("MessagingException while setting from", e);
			}

	        InternetAddress[] toAddress = new InternetAddress[to.length];
	        
	        for( int i = 0; i < to.length; i++) {
	            try {
					toAddress[i] = new InternetAddress(to[i]);
				} catch (AddressException e) {
					logger.error("AddressException while creating internetAddress", e);
				}
	        }

	        for( int i = 0; i < toAddress.length; i++) {
	            try {
					message.addRecipient(Message.RecipientType.TO, toAddress[i]);
				} catch (MessagingException e) {
					logger.error("MessagingException while adding recipient", e);
				}
	        }
	        try {
				message.setSubject("DrinksMachine notification - Ingredient alarm level(s) reached");
			} catch (MessagingException e) {
				logger.error("MessagingException while setting subject", e);
			}
	        try {
	        	String content = "";
	        	content += "Hello\n\n";
	        	content += "DrinksMachine alarm level(s) reached:\n\n";
	        	Reply reply = drinksMachine.getStatus(false, false);
	        	for(StockEntry stockEntry : reply.getStatus().getStockEntries()) {
	        		if(stockEntry.getAlarm() >= stockEntry.getCurrent()) {
	        			content += 
	        					" * " + Ingredient.valueOf(stockEntry.getIngredient()).getGuiName() + 
	        					" - alarm: " + stockEntry.getAlarm() + 
	        					" - current: " + stockEntry.getCurrent() + "\n"; 
	        		}
	        	}
	        	content += "\nKind regards,";
	        	content += "\n\nDrinksMachine";
				message.setText(content);
			} catch (MessagingException e) {
				logger.error("MessagingException while setting text", e);
			}
	        Transport transport = null;
			try {
				transport = session.getTransport(this.transport);
			} catch (NoSuchProviderException e) {
				logger.error("NoSuchProviderException while getting transport", e);
			}
	        try {
				transport.connect(host, from, pass);
			} catch (MessagingException e) {
				logger.error("MessagingException while connecting", e);
			}
	        try {
				transport.sendMessage(message, message.getAllRecipients());
			} catch (MessagingException e) {
				logger.error("MessagingException while sending message", e);
			}
	        try {
				transport.close();
			} catch (MessagingException e) {
				logger.error("MessagingException while closing transport", e);
			}
		}
	}

}
