/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.helmchen.kompass.insurance.entities;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

/**
 *
 * @author helmut
 */
@XmlEnum
public enum LegalForm {

    /**
     * AG
     */
    @XmlEnumValue("corporation")
    CORPORATION,
    /**
     * GmbH
     */
    @XmlEnumValue("limited corporation")
    LIMITED_CORPORATION,
    /**
     * Verein
     */
    @XmlEnumValue("society")
    SOCIETY,
    /**
     * Stiftung
     */
    @XmlEnumValue("foundation")
    FOUNDATION,
    /**
     * Genossenschaft.
     */
    @XmlEnumValue("cooperative")
    COOPERATIVE
}
