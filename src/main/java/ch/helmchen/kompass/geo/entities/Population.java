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
 * Regional population.
 *
 * @author helmut
 */
@Entity
public class Population {

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
    private Integer totalCount;
    private Integer maleCount;
    private Integer femaleCount;
    private Integer childrenCount;
    private Integer teenCount;
    private Integer adultCount;

    /**
     *
     */
    public Population() {
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

    /**
     *
     * @return
     */
    public Integer getTotalCount() {
        return totalCount;
    }

    /**
     *
     * @param aTotalCount
     */
    public void setTotalCount(final Integer aTotalCount) {
        totalCount = aTotalCount;
    }

    /**
     *
     * @return
     */
    public Integer getMaleCount() {
        return maleCount;
    }

    /**
     *
     * @param aMaleCount
     */
    public void setMaleCount(final Integer aMaleCount) {
        this.maleCount = aMaleCount;
    }

    /**
     *
     * @return
     */
    public Integer getFemaleCount() {
        return femaleCount;
    }

    /**
     *
     * @param aFemaleCount
     */
    public void setFemaleCount(final Integer aFemaleCount) {
        femaleCount = aFemaleCount;
    }

    /**
     *
     * @return
     */
    public Integer getChildrenCount() {
        return childrenCount;
    }

    /**
     *
     * @param aChildrenCount
     */
    public void setChildrenCount(final Integer aChildrenCount) {
        childrenCount = aChildrenCount;
    }

    /**
     *
     * @return
     */
    public Integer getTeenCount() {
        return teenCount;
    }

    /**
     *
     * @param aTeenCount
     */
    public void setTeenCount(final Integer aTeenCount) {
        teenCount = aTeenCount;
    }

    /**
     *
     * @return
     */
    public Integer getAdultCount() {
        return adultCount;
    }

    /**
     *
     * @param anAdultCount
     */
    public void setAdultCount(final Integer anAdultCount) {
        adultCount = anAdultCount;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.id;
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
        final Population other = (Population) obj;
        if (id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Population{" + "id=" + id + ", structureNumber=" + structureNumber 
                + ", version=" + version + ", totalCount=" + totalCount 
                + ", maleCount=" + maleCount + ", femaleCount=" + femaleCount 
                + ", childrenCount=" + childrenCount + ", teenCount=" + teenCount 
                + ", adultCount=" + adultCount + '}';
    }

}
