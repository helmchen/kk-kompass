/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.helmchen.kompass.insurance.entities;

import java.io.Serializable;

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
        return "TelecomContact{" + "usage=" + usage + ", characteristics=" + characteristics + ", value=" + value + '}';
    }
    
    
}
