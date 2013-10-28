/*
 * Copyright 2013 Helmut Gehrer.
 * Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file except in
 * compliance with the License. Obtain your copy at http://www.apache.org/licenses/LICENSE-2.0 .
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing permissions and limitations.
 */
package ch.helmchen.kompass.geo.entities;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.xml.bind.annotation.XmlValue;

/**
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
     *
     */
    public StructureNumber() {
    }

    /**
     *
     * @param aValue
     */
    public StructureNumber(final String aValue) {
        value = aValue;
    }

    /**
     *
     * @param aValue
     * @return
     */
    public static StructureNumber valueOf(String aValue) {
        return new StructureNumber(aValue);
    }

    /**
     *
     * @param aCountry
     * @param aCanton
     * @param aZone
     * @return
     */
    public static StructureNumber valueOf(final Country aCountry, final Canton aCanton, final String aZone) {
        StringBuilder key = new StringBuilder();
        key.append(aCountry.asDbKey());
        key.append(DELIMITER);
        key.append(aCanton.asDbKey());
        key.append(DELIMITER);
        key.append(aZone);
        return new StructureNumber(key.toString());
    }

    /**
     *
     * @param components
     * @return
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
    public void setValue(final String value) {
        this.value = value;
    }

    /**
     *
     * @return
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
        return "StructureNumber{" + "value=" + value + '}';
    }

}
