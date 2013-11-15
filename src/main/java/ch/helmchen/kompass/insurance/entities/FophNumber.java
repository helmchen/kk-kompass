/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.helmchen.kompass.insurance.entities;

import ch.helmchen.kompass.meta.ApplicationInfo;
import ch.helmchen.kompass.util.AlphanumericKey;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * The official Number for inurance providers in Switzerland. This number is maintained by the
 * "Bundesamt of Gesundheit" (BAG) / Federal Office of public health. Every insurance company in
 * Switzerland receives an Id from the FOPH with the license to operate.
 *
 * @author helmut
 */
public class FophNumber implements Serializable, Comparable<FophNumber> {

    private String value;

    /**
     * No-Arg constructor for the foph Number. Value must be set by
     * {@link #setValue(java.lang.String)}.
     */
    public FophNumber() {
    }

    /**
     * Constructs a Number and sets its value.
     *
     * @param aValue FOPH number.
     */
    public FophNumber(final String aValue) {
        value = aValue;
    }

    /**
     * Delivers the value of the fophNumer.
     *
     * @return the value.
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value of the fophNumber. The number will be set th the official size.
     *
     * @param aValue
     */
    public void setValue(final String aValue) {
        value = toRightSize(aValue);
    }

    /**
     * Checks, if the given String is a valid FophNumber.
     *
     * @param aString the String to check.
     * @return <tt>true</tt>: the given number is a valid FOPH-Number
     * <tt>false</tt>: the given number is not valid.
     */
    public static boolean isValid(String aString) {
        if (aString == null || aString.isEmpty()) {
            return false;
        }
        try {
            return isValid(Integer.parseInt(aString));
        } catch (NumberFormatException notANum) {
            return false;
        }
    }

    /**
     * Checks, if the given Integer is a valid FophNumber. Checks the currently used number range (1 -
     * 2000)
     *
     * @param aNumber the Integer to check.
     * @return <tt>true</tt>: the given number is a valid FOPH-Number
     * <tt>false</tt>: the given number is not valid.
     */
    public static boolean isValid(final Integer aNumber) {
        if (aNumber == null) {
            return false;
        } 
        return (aNumber > 0 && aNumber < 2000);

    }

    /**
     * Creates a new FophNumber from the given value.
     *
     * @param aString the value of the returned FophNumber
     * @return a FophNumber.
     */
    public static FophNumber valueOf(final String aString) {
        if (aString == null || aString.isEmpty()) {
            return null;
        }
        return new FophNumber(toRightSize(aString));
    }

    /**
     * Creates a new FophNumber from the given value.
     *
     * @param aNumber the value of the returned FophNumber
     * @return a FophNumber.
     */
    public static FophNumber valueOf(final Integer aNumber) {
        if (aNumber == null || aNumber.intValue() == 0) {
            return null;
        }
        return new FophNumber(toRightSize(String.valueOf(aNumber)));
    }

    /**
     * sets the length of the given value to 4 by prefixing zeroes.
     *
     * @param aString Value
     * @return value with 4 chars.
     */
    private static String toRightSize(final String aString) {
        // Werte nur bis 2000 erlaubt.
        if (! isValid(aString)) {
            throw new NumberFormatException("'" + aString + "' is not in the allowed range.");
        }
        return AlphanumericKey.asKey(aString, 4);
    }

    @Override
    public String toString() {
        final Map<String, Object> properties = new HashMap<String, Object>();
        properties.put("value", value);
        return ApplicationInfo.toString(this, properties);
    }

   
    @Override
    public int compareTo(FophNumber other) {
        if (value == null || other.value == null) {
            return 0;
        }
        if (value == null) {
            return 1;
        }
        
        if (other.value == null) {
            return -1;
        }
        return value.compareTo(other.value);
    }
    
}
