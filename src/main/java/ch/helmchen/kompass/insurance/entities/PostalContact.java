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
public class PostalContact extends Contact implements Serializable{
    private String street;
    private String buildingNumber;
    private String deliveryInformation;
    private String poBox;
    /* @todo Überdenken; evtl als GeoInformation handhaben? */
    private String postalCode;
    private String cityName;
    private String country;
    
            
    
}
