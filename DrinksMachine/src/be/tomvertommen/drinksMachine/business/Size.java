package be.tomvertommen.drinksMachine.business;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The possible {@link Drink} sizes
 * @author Tom Vertommen
 *
 */
@XmlRootElement(name="size")
@XmlAccessorType(XmlAccessType.FIELD)
public enum Size {
	
	REGULAR, LARGE, XXL;

}
