/*
 * Copyright 2013 Helmut Gehrer.
 * Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file except in
 * compliance with the License. Obtain your copy at http://www.apache.org/licenses/LICENSE-2.0 .
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing permissions and limitations.
 */
package ch.helmchen.kompass.geo.entities;

import javax.persistence.Embedded;
import javax.persistence.Entity;
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
    private StructureDepth level;
    @NotNull
    private String code;
    @NotNull
    private String name;

    /**
     *
     */
    public PoliticalStructure() {
    }

    /**
     *
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param anId
     */
    public void setId(final int anId) {
        id = anId;
    }

    /**
     *
     * @return
     */
    public StructureNumber getStructureNumber() {
        return structureNumber;
    }

    /**
     *
     * @param aStructureNumber
     */
    public void setStructureNumber(final StructureNumber aStructureNumber) {
        structureNumber = aStructureNumber;
    }

    /**
     *
     * @return
     */
    public int getVersion() {
        return version;
    }

    /**
     *
     * @param aVersion
     */
    public void setVersion(final int aVersion) {
        version = aVersion;
    }

    public StructureDepth getLevel() {
        return level;
    }

    public void setLevel(StructureDepth level) {
        this.level = level;
    }

    /**
     *
     * @return
     */
    public String getCode() {
        return code;
    }

    /**
     *
     * @param aCode
     */
    public void setCode(final String aCode) {
        code = aCode;
    }

    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param aName
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
    public boolean equals(Object obj) {
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
    
}
