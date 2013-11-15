/*
 * Copyright 2013 Helmut Gehrer.
 * Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file except in
 * compliance with the License. Obtain your copy at http://www.apache.org/licenses/LICENSE-2.0 .
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing permissions and limitations.
 */
package ch.helmchen.kompass.geo.entities;

import ch.helmchen.kompass.meta.ApplicationInfo;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.StringTokenizer;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.xml.bind.annotation.XmlValue;

/**
 * Hierarchischer Schlüssel für die Abbildung von hierarchischen Strukturen. Die unterschiedlichen
 * Tokens der Nummer sollten dabei die gleiche Länge haben. Als Token Delimiter dient dabei ein Punkt.
 *
 * @author helmut
 */
@Embeddable
public class StructureNumber implements Serializable {

    private static final char DELIMITER = '.';

    @Column(name = "structureNumber")
    @XmlValue
    private String value;

    /**
     * Erstellt eine neue Instanz.
     */
    public StructureNumber() {
    }

    /**
     * Erstellt eine neue Instanz. Dabei wird der komplette Key als Schlüssel mitgegeben.
     *
     * @param aValue Wert, welcher als StructureNumber zu übernehmen ist.
     */
    public StructureNumber(final String aValue) {
        value = aValue;
    }

    /**
     * Erstellt eine neue Instanz aus dem übergebenen String.
     * @param aValue Wert, welcher als StructureNumber verwendet werden soll.
     * @return Erstellte StructureNumber.
     */
    public static StructureNumber valueOf(String aValue) {
        return new StructureNumber(aValue);
    }

    /**
     * Erstellt eine StrucctureNumber anhand von Land, Kanton und Prämienregionscode. Dieser
     * Konstructor ist für die Abbildung der Prämienregionen gedacht.
     *
     * @param aCountry Land
     * @param aCanton Kanton
     * @param aZone Prämienregion im Kanton.
     * @return StructureNumer aus den übergebenen Parametern.
     */
    public static StructureNumber valueOf(
            final Country aCountry, final Canton aCanton, final String aZone) {
        StringBuilder key = new StringBuilder();
        key.append(aCountry.asDbKey());
        key.append(DELIMITER);
        key.append(aCanton.asDbKey());
        key.append(DELIMITER);
        key.append(aZone);
        return new StructureNumber(key.toString());
    }

    /**
     * Erstellt eine StructureNumber aus beliebigen Locatable-Teilen.
     *
     * @param components Locatables, aus welchen die Strukturnummer erstellt werden soll. Die
     * Komponenten müssen dabei in ihrer hierarchischen Reihenfolge übergeben werden.
     * @return Strukturnummer aus den übergebenen Komponenten.
     */
    public static StructureNumber valueOf(final Locatable... components) {
        if (components == null) {
            return null;
        }
        StringBuilder key = new StringBuilder();

        for (int i = 0; i < components.length; i++) {
            if (i > 0) {
                key.append(DELIMITER);
            }
            key.append(components[i].asDbKey());
        }
        return new StructureNumber(key.toString());
    }

    /**
     * Liefert den Wert zurück.
     *
     * @return Wert.
     */
    public String getValue() {
        return value;
    }

    /**
     * Setzt den Wert der Strukturnummer.
     *
     * @param aValue Wert.
     */
    public void setValue(final String aValue) {
        value = aValue;
    }

    /**
     * Liefert die Strukturtiefe dieses Elements.
     *
     * @return Strukturtiefe.
     */
    public StructureDepth getDepth() {
        if (value == null || value.isEmpty()) {
            return null;
        }
        int resultPos = 0;
        for (char c : value.toCharArray()) {
            if (DELIMITER == c) {
                resultPos++;
            }
        }
        if (resultPos <= StructureDepth.values().length) {
            return StructureDepth.values()[resultPos];

        }
        //undefined
        return null;
    }

    /**
     * Liefert das Elternelement von diesem Key zurück.
     *
     * @return Elternelement.
     */
    public StructureNumber getParent() {
        final int iOwnDepth = getDepth().ordinal();
        final StringBuilder result = new StringBuilder();
        final StringTokenizer stoParts = new StringTokenizer(value, String.valueOf(DELIMITER));
        int iCurrentDepth = 0;
        while (stoParts.hasMoreTokens()) {
            if (iCurrentDepth++ >= iOwnDepth) {
                break;
            }
            if (iCurrentDepth > 1) {
                result.append(DELIMITER);
            }
            result.append(stoParts.nextToken());
        }
        return StructureNumber.valueOf(result.toString());
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + Objects.hashCode(this.value);
        return hash;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final StructureNumber other = (StructureNumber) obj;
        if (!Objects.equals(this.value, other.value)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put("value", value);
        return ApplicationInfo.toString(this, properties);
    }

}
