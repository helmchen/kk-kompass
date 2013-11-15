/*
 * Copyright 2013 Helmut Gehrer.
 * Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file except in
 * compliance with the License. Obtain your copy at http://www.apache.org/licenses/LICENSE-2.0 .
 * Unless required by applicable law or agreed to in writing, software distributed under the License  
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing permissions and limitations.
 */
package ch.helmchen.kompass.meta.entities;

import ch.helmchen.kompass.meta.ApplicationInfo;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Repräsentiert eine Datenansammlung, typisiert nach Name. Dient u. A auch der Versionisierung
 * ({@link #getVersion()}). Während des Ladens von Daten kann eine einzelne Version gesperrt werden
 * ({@link #isLocked()}).
 *
 * @author helmut
 */
@Entity
@NamedQueries({
    @NamedQuery(name = DataPool.QUERY_FIND_ALL,
            query = "SELECT p FROM DataPool p"),
    @NamedQuery(name = DataPool.QUERY_FIND_BY_KEYDATE,
            query = "SELECT p FROM DataPool p WHERE p.dataPoolType = :" + DataPool.PARAM_DATAPOOL_TYPE
            + " AND :" + DataPool.PARAM_VALID_AT + " BETWEEN p.validFrom and p.validUntil")
})
public class DataPool implements Serializable {

    /**
     * Name des Named-Queries, um alle Entities zu laden.
     */
    public static final String QUERY_FIND_ALL
            = DataPool.BEAN_PREFIX + "findAll";

    /**
     * Name des Named-Queries, um alle Datensammlungsversionen zu einem bestimmten Stichdatum zu
     * laden.
     */
    public static final String QUERY_FIND_BY_KEYDATE
            = DataPool.BEAN_PREFIX + "findByKeydate";
    /**
     * Name des Named-Queries, um die Versionen einer Datensammlung in einem Zeitbereich zu ermitteln.
     */
    public static final String QUERY_FIND_BY_DATAPOOLTYPE_AND_DATERANGE
            = DataPool.BEAN_PREFIX + "findByDatapooltypeAndDateragen";
    /**
     * SQL-Query-Parameter: Name des Datenpools.
     */
    public static final String PARAM_DATAPOOL_TYPE = "aDataPoolType";

    /**
     * SQL-Query Parameter: Stichdatum.
     */
    public static final String PARAM_VALID_AT = "aKeyDate";
    /**
     * SQL-Query Parameter: Stichdatum ab.
     */
    public static final String PARAM_VALID_FROM = "aKeyDateFrom";
    /**
     * SQL-Query Parameter: Stichdatum bis.
     */
    public static final String PARAM_VALID_UNTIL = "aKeyDateUntil";
    private static final String BEAN_PREFIX = "ch.helmchen.kompass.meta.DataPool."
            + ApplicationInfo.API_VERSION + ".";
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @NotNull
    private int datapoolId;
    @NotNull
    @Enumerated(EnumType.STRING)
    private DataPoolType dataPoolType;
    @NotNull
    private int version;
    @NotNull
    @Temporal(TemporalType.DATE)
    private Date validFrom;
    @NotNull
    @Temporal(TemporalType.DATE)
    private Date validUntil;
    @Enumerated(EnumType.STRING)
    private License license;
    @NotNull
    private boolean locked;
    @NotNull
    @Size(min = 1, max = 240)
    private String licenser;
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date loadedAt;

    /**
     * Erstelt eine neue Instanz.
     */
    public DataPool() {
    }

    /**
     * Liefert die interne Id des Entities.
     *
     * @return Primärschlüssel der Datenbank
     */
    public int getDatapoolId() {
        return datapoolId;
    }

    /**
     * Setzt die interene Id des Datenpools.
     *
     * @param aDatapoolId Primärschüssel der Datenbank.
     */
    public void setDatapoolId(final int aDatapoolId) {
        datapoolId = aDatapoolId;
    }

    /**
     * Liefert den typisierten Namen des Datenpools.
     *
     * @return Typisierter NAme des Datenpools.
     */
    public DataPoolType getDataPoolType() {
        return dataPoolType;
    }

    /**
     * Setzt den typisierten Namen des Datenpools.
     *
     * @param aDataPoolType Typisierter Name des Datenpools.
     */
    public void setDataPoolType(final DataPoolType aDataPoolType) {
        dataPoolType = aDataPoolType;
    }

    /**
     * Liefert die Versionsnummer.
     *
     * @return Versionsnummer.
     */
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
     * Liefert das Datum des Gültigkeitsbeginns dieser Version.
     *
     * @return gültig ab der Version
     */
    public Date getValidFrom() {
        return validFrom;
    }

    /**
     * Setzt das Datum des Gültigkeitsgebinns dieser Version.
     *
     * @param aValidFrom gültig ab
     */
    public void setValidFrom(final Date aValidFrom) {
        validFrom = aValidFrom;
    }

    /**
     * Liefert das Datum des Gültigkeitsendes dieser Version.
     *
     * @return gültig bis der Version.
     */
    public Date getValidUntil() {
        return validUntil;
    }

    /**
     * Setzt das Datum des Gültigkeitsendes dieser Version.
     *
     * @param aValidUntil Gültig-Bis-Datum dieser Version
     */
    public void setValidUntil(final Date aValidUntil) {
        this.validUntil = aValidUntil;
    }

    /**
     * Liefert die Lizenz.
     *
     * @return Lizenz, unter welcher die Daten veröffentlicht sind.
     */
    public License getLicense() {
        return license;
    }

    /**
     * Setzt die Lizenz.
     *
     * @param aLicense Lizenz unter welcher die Daten veröffentlicht wurden.
     */
    public void setLicense(final License aLicense) {
        this.license = aLicense;
    }

    /**
     * Liefert zurück, ob diese Version gesperrt ist. Während eines Ladelaufs kann eine Version für
     * die Verwendung gesperrt werden, bis das ganze Dataset vorhanden ist. Dann können die Daten u.U.
     * nicht angezeigt werden.
     *
     * @return <ul><li><tt>true</tt>: Version ist gesperrt und sollte nicht verwendet werden.</li>
     * <li><tt>false</tt>: Version ist für die Nutzung freigegeben.</li></ul>
     */
    public boolean isLocked() {
        return locked;
    }

    /**
     * Definiert, ob diese Version der Datensammlung für die Verwendung gesperrt ist.
     *
     * @param isLocked Version gesperrt?
     */
    public void setLocked(final boolean isLocked) {
        locked = isLocked;
    }

    /**
     * Liefert den Lizenzgeber der Datensammlung.
     *
     * @return Name des Lizenzgebers
     */
    public String getLicenser() {
        return licenser;
    }

    /**
     * Setzt den Namen des Lizenzgebers.
     *
     * @param aLicenser Lizenzgeber.
     */
    public void setLicenser(final String aLicenser) {
        licenser = aLicenser;
    }

    /**
     * Liefert das Datum, wann diese Version der Datenquelle geladen worden ist.
     *
     * @return Ladedatum.
     */
    public Date getLoadedAt() {
        return loadedAt;
    }

    /**
     * Setzt das Datum, wann diese Version der Datenquelle geladen worden ist.
     *
     * @param aLoadedAt Ladedatum.
     */
    public void setLoadedAt(final Date aLoadedAt) {
        loadedAt = aLoadedAt;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + datapoolId;
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
        final DataPool other = (DataPool) obj;
        if (datapoolId != other.datapoolId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        final Map<String, Object> properties = new HashMap<String, Object>();
        properties.put("datapoolId", datapoolId);
        properties.put("dataPoolType", dataPoolType);
        properties.put("version", version);
        properties.put("validFrom", validFrom);
        properties.put("validUntil", validUntil);
        properties.put("license", license);
        properties.put("locked", locked);
        properties.put("licenser", licenser);
        properties.put("loadedAt", loadedAt);
        return ApplicationInfo.toString(this, properties);
    }
}
