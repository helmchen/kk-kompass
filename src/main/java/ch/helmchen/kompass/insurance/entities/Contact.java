/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.helmchen.kompass.insurance.entities;

/**
 * Abstract Superclass for all contacts. Handles the usage of a contact.
 * @author helmut
 */
public abstract class Contact {
     ContactUsage usage;

    /**
     *
     * @return
     */
    public ContactUsage getUsage() {
        return usage;
    }

    /**
     *
     * @param aUsage
     */
    public void setUsage(ContactUsage aUsage) {
        usage = aUsage;
    }
    
    
}
