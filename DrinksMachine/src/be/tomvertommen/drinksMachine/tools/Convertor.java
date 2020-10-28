package be.tomvertommen.drinksMachine.tools;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;

import org.apache.log4j.Logger;

import be.tomvertommen.drinksMachine.business.Status;

/**
 * Utility class for marshalling and unmarshalling.
 * Used when communicating by XML over TCP/IP
 * Used when persisting and loading {@link Status} from and to disc.
 * @author Tom Vertommen
 *
 */
public class Convertor {
	
	private static Logger logger = Logger.getLogger(Convertor.class);
	
	/**
	 * 
	 * @param o
	 * @param c
	 * @return given {@link Object} marshalled to {@link String}
	 */
	public static String marshall(Object o, Class<?> c) {
		StringWriter writer = new StringWriter();
		JAXBContext context;
		try {
			context = JAXBContext.newInstance(c);
		} catch (JAXBException e) {
			logger.error("Exception while getting JAXBContext", e);
			throw new RuntimeException(e);
		}
		javax.xml.bind.Marshaller m = null;
		try {
			m = context.createMarshaller();
		} catch (JAXBException e) {
			logger.error("Exception while creating marshaller", e);
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		try {
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		} catch (PropertyException e) {
			logger.error("Exception while setting marshaller property", e);
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		try {
			m.marshal(o, writer);
		} catch (JAXBException e) {
			logger.error("Exception while marshalling", e);
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		String returnValue = writer.toString();
		return returnValue;
	}
	
	/**
	 * 
	 * @param s
	 * @param c
	 * @return given {@link String} unmarshalled to {@link Object}
	 */
	public static Object unmarshall(String s, Class<?> c) {
		JAXBContext context = null;
		try {
			context = JAXBContext.newInstance(c);
		} catch (JAXBException e) {
			logger.error("Exception while getting JAXBContext", e);
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		javax.xml.bind.Unmarshaller m = null;
		try {
			m = context.createUnmarshaller();
		} catch (JAXBException e) {
			logger.error("Exception while creating unmarshaller", e);
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		Object o;
		try {
			o = m.unmarshal(new StringReader(s));
		} catch (JAXBException e) {
			logger.error("Exception while unmarshalling", e);
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return o;
	}
	
}
