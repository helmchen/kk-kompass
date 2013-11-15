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
import ch.helmchen.kompass.util.AlphanumericKey;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlValue;

/**
 * The community number of swiss communities. The number is maintained by the Bundesamt für Statistik
 * (BFS). Intern wird die Nummer immer fünfstellig gehalten, um einen alphanumerischen Vergleich zu
 * vereinfachen.
 *
 * @author helmut
 */
@Embeddable
public class CommunityNumber implements Locatable, Serializable {

    private static final int FIELD_SIZE = 5;
    @Column(name = "communityNumber")
    @Size(min = FIELD_SIZE, max = FIELD_SIZE)

    @XmlValue
    private String value;

    /**
     * Erstellt eine neue Instanz dieser Klasse.
     */
    public CommunityNumber() {
    }

    /**
     * Erstellt eine neue Instanz dieser Klasse. Dabei kann der Wert bereits mitgegeben werden.
     *
     * @param aValue
     */
    public CommunityNumber(final String aValue) {
        value = toRightSize(aValue);
    }

    /**
     * Liefert den Wert zurück.
     *
     * @return
     */
    public String getValue() {
        return value;
    }

    /**
     * Setzt den Wert der Gemeindenummer.
     *
     * @param aValue
     */
    public void setValue(final String aValue) {
        value = toRightSize(aValue);
    }

    @Override
    public Integer asGeoId() {
        if (value == null || value.isEmpty()) {
            return null;
        }
        return Integer.parseInt(value);
    }

    @Override
    public String asDbKey() {
        return value;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(value);
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
        final CommunityNumber other = (CommunityNumber) obj;
        if (!Objects.equals(value, other.value)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        final Map<String, Object> properties = new HashMap<String, Object>();
        properties.put("value", value);
        return ApplicationInfo.toString(this, properties);
    }

    private static String toRightSize(final String aString) {
        return AlphanumericKey.asKey(aString, FIELD_SIZE);
    }
}
