package be.tomvertommen.drinksMachine.business;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * All possible {@link Drink} strengths
 * @author Tom Vertommen
 *
 */
@XmlRootElement(name="strength")
@XmlAccessorType(XmlAccessType.FIELD)
public enum Strength {
	
	REGULAR, STRONG, VERY_STRONG;

}
