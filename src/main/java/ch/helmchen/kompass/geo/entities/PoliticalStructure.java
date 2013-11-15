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
import java.util.HashMap;
import java.util.Map;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAttribute;

/**
 * Geografische Information nach politisch / organisatorischem Aufbau.
 *
 * @author helmut
 */
@Entity
public class PoliticalStructure {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @XmlAttribute
    private int id;
    @NotNull
    @Embedded
    private StructureNumber structureNumber;
    @NotNull
    @XmlAttribute
    private int version;
    @NotNull
    @Enumerated(EnumType.ORDINAL)
    private StructureDepth level;
    @NotNull
    private String code;
    @NotNull
    private String name;

    /**
     * Erstellt eine neue Instanz.
     */
    public PoliticalStructure() {
    }

    /**
     * Liefert die interne Id des Tupels.
     *
     * @return id.
     */
    public int getId() {
        return id;
    }

    /**
     * Setzt die interne Id des Tupels.
     *
     * @param anId interne Id.
     */
    public void setId(final int anId) {
        id = anId;
    }

    /**
     * Liefert die Strukturnummer zurück. Diese definiert den kompletten hierarchischen fachlichen
     * Schlüssel, getrennt jeweils durch einen Punkt.
     *
     * @return fachlicher Schlüssel.
     */
    public StructureNumber getStructureNumber() {
        return structureNumber;
    }

    /**
     * Setzt die Strukturnummer. d.H. den fachlichen Key.
     *
     * @param aStructureNumber Strukturnummer
     */
    public void setStructureNumber(final StructureNumber aStructureNumber) {
        structureNumber = aStructureNumber;
    }

    /**
     * Liefert die Version zurück.
     *
     * @return Versionsnummer.
     */
    public int getVersion() {
        return version;
    }

    /**
     * Setzt die Version.
     *
     * @param aVersion Versionsnummer.
     */
    public void setVersion(final int aVersion) {
        version = aVersion;
    }

    /**
     * Liefert den Level also die Tiefe der Struktur.
     *
     * @return Level der Struktur.
     */
    public StructureDepth getLevel() {
        return level;
    }

    /**
     * Setzt den Level, also die Tiefe der Struktur.
     *
     * @param aLevel Tiefe.
     */
    public void setLevel(final StructureDepth aLevel) {
        level = aLevel;
    }

    /**
     * Liefert den Code dieses Elements. Je nach Art / Tiefe kann dies z.B. ein Kantonskürzel oder
     * eine Postleitzahl, etc. sein.
     *
     * @return fachlicher Code von genau diesem Element.
     */
    public String getCode() {
        return code;
    }

    /**
     * Setzt den Code des Elements.
     *
     * @param aCode fachlicher Code
     */
    public void setCode(final String aCode) {
        code = aCode;
    }

    /**
     * Liefert den Namen.
     *
     * @return Name des Elements.
     */
    public String getName() {
        return name;
    }

    /**
     * Setzt den Namen des Elements.
     *
     * @param aName Name
     */
    public void setName(final String aName) {
        name = aName;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + id;
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
        final PoliticalStructure other = (PoliticalStructure) obj;
        if (id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        final Map<String, Object> properties = new HashMap<String, Object>();
        properties.put("id", id);
        properties.put("structureNumber", structureNumber);
        properties.put("version", version);
        properties.put("level", level);
        properties.put("code", code);
        properties.put("name", name);
        return ApplicationInfo.toString(this, properties);
    }

}
