/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.helmchen.kompass.insurance.entities;

import ch.helmchen.kompass.meta.ApplicationInfo;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author helmut
 */
public class TelecomContact extends Contact implements Serializable {

    private String characteristics;
    private String value;

    /**
     *
     * @return
     */
    public String getCharacteristics() {
        return characteristics;
    }

    /**
     *
     * @param characteristics
     */
    public void setCharacteristics(String characteristics) {
        this.characteristics = characteristics;
    }

    /**
     *
     * @return
     */
    public String getValue() {
        return value;
    }

    /**
     *
     * @param value
     */
    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        final Map<String, Object> properties = new HashMap<String, Object>();
        properties.put("usage", usage);
        properties.put("characteristics", characteristics);
        properties.put("value", value);
        return ApplicationInfo.toString(this, properties);
    }

}
