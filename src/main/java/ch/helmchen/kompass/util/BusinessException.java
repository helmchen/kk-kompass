/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.helmchen.kompass.util;

/**
 *
 * @author helmut
 */
public class BusinessException extends Exception {

    /**
     *
     */
    public BusinessException() {
    }

    /**
     *
     * @param message
     * @param cause
     */
    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
