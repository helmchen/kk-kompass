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
import ch.helmchen.kompass.meta.Versionable;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAttribute;

/**
 * Gemeinde in der Schweiz. Diese beinhaltet <ul>
 * <li>die Gemeindenummer (gemäss BFS)</li>
 * <li>die Strukturnummer für den Zugriff auf politisch höhere Komponenten,</li>
 * <li>und die Zuweisung zu einer Prämienregion</li>
 * </ul>
 * Der Name der Gemeinde ist nicht in diesem Objekt enthalten. Er kann via
 * {@link PoliticalStructure#getName()} ermittelt werden.
 *
 * @author helmut
 */
@Entity
@NamedQueries({
    @NamedQuery(name = Community.QUERY_FIND_BY_COMMUNITY_NUMBER,
            query
            = "SELECT c FROM Community c"
            + " WHERE c.communityNumber = :" + Community.PARAM_COMMUNITY_NUMBER
            + "   AND c.version = :" + Community.PARAM_VERSION)
})

public class Community implements Serializable, Versionable {

    /**
     * Name des Named-Queries, um mehrere Entities anhand des Codes zu laden. Es wird eine Suche mit
     * folgenden Parametern ausgelöst:<ul>
     * <li>PARAM_CODE (like)</li>
     * <li>PARAM_VERSION (=)</li>
     * <li>PARAM_DEEPEST_LEVEL (&gt;=)</li>
     * <li>PARAM_HIGHEST_LEVEL (&lt;=)</li></ul>
     */
    public static final String QUERY_FIND_BY_COMMUNITY_NUMBER
            = Community.BEAN_PREFIX + "findByCommunityNumber";

    /**
     * SQL-Query-Parameter: Name des Datenpools.
     */
    public static final String PARAM_COMMUNITY_NUMBER = "aCommunityNumber";

    /**
     * SQL-Query Parameter: Stichdatum.
     */
    public static final String PARAM_VERSION = "aVersion";

    private static final String BEAN_PREFIX = "ch.helmchen.kompass.geo.Community."
            + ApplicationInfo.API_VERSION + ".";

    private static final int OHI_ZONE_FIELD_SIZE = 8;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @XmlAttribute
    private int id;
    @NotNull
    @Embedded
    private CommunityNumber communityNumber;
    @NotNull
    @XmlAttribute
    private int version;
    @NotNull
    private StructureNumber structureNumber;
    // DB-Key Land + Kanton + Prämienregion.
    @NotNull
    @Size(min = OHI_ZONE_FIELD_SIZE, max = OHI_ZONE_FIELD_SIZE)
    private String ohiZone;

    /**
     * Erstellt eine neue Gemeinde.
     */
    public Community() {
    }

    /**
     * Liefert die interne Id der Gemeinde zurück.
     *
     * @return interne DB-Id.
     */
    public int getId() {
        return id;
    }

    /**
     * Setzt die interne DB-Id.
     *
     * @param anId Datenbank-Id
     */
    public void setId(final int anId) {
        id = anId;
    }

    /**
     * Liefert die Gemeindenummer gemäss BFS.
     *
     * @return Gemeindenummer.
     */
    public CommunityNumber getCommunityNumber() {
        return communityNumber;
    }

    /**
     * Setzt die Gemeindenummer.
     *
     * @param aCommunityNumber Gemeindenummer.
     */
    public void setCommunityNumber(final CommunityNumber aCommunityNumber) {
        communityNumber = aCommunityNumber;
    }

    @Override
    public int getVersion() {
        return version;
    }

    /**
     * Setzt die Versionsnummer.
     *
     * @param aVersion Versionsnummer.
     */
    public void setVersion(final int aVersion) {
        version = aVersion;
    }

    /**
     * Liefert die Strukturnummer dieser Gemeinde.
     *
     * @return Strukturnummer.
     */
    public StructureNumber getStructureNumber() {
        return structureNumber;
    }

    /**
     * Setzt die Strukturnummer der Gemeinde.
     *
     * @param aStructureNumber Strukturnummer
     */
    public void setStructureNumber(final StructureNumber aStructureNumber) {
        structureNumber = aStructureNumber;
    }

    /**
     * Liefert die Zone für die OKP-Berechnung. Diese ist im Format <tt>land.kanton.prämienregion</tt>
     * also z.B. <tt>386.01.2</tt>.
     *
     * @return Zone für Grundversicherung.
     */
    public String getOhiZone() {
        return ohiZone;
    }

    /**
     * Setzt die Zone für die OKP-Prämienberechnung.
     *
     * @param anOhiZone Zone der Grundversicherung.
     */
    public void setOhiZone(final String anOhiZone) {
        ohiZone = anOhiZone;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + id;
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
        final Community other = (Community) obj;
        if (id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        final Map<String, Object> properties = new HashMap<String, Object>();
        properties.put("id", id);
        properties.put("communityNumber", communityNumber);
        properties.put("version", version);
        properties.put("structureNumber", structureNumber);
        properties.put("ohiZone", ohiZone);
        return ApplicationInfo.toString(this, properties);
    }

}
