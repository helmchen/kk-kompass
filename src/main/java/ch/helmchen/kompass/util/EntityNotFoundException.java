/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.helmchen.kompass.util;

/**
 *
 * @author helmut
 */
public class EntityNotFoundException extends BusinessException {

    /**
     *
     */
    public EntityNotFoundException() {
    }

    /**
     *
     * @param message
     * @param cause
     */
    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
