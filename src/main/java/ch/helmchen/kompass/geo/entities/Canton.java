/*
 * Copyright 2013 Helmut Gehrer.
 * Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file except in
 * compliance with the License. Obtain your copy at http://www.apache.org/licenses/LICENSE-2.0 .
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing permissions and limitations.
 */
package ch.helmchen.kompass.geo.entities;

import ch.helmchen.kompass.util.AlphanumericKey;
import javax.xml.bind.annotation.XmlEnum;

/**
 * Schweizer Kantone. Diese Enum stellt alle Schweizer Kantone zur Verfügung.
 *
 * @author helmut
 */
@XmlEnum
public enum Canton implements Locatable {

    /**
     * Argau.
     */
    AG(19),
    /**
     * Appenzell Innerrhoden.
     */
    AI(16),
    /**
     * Appenzell Auserrhoden.
     */
    AR(15),
    /**
     * Bern.
     */
    BE(2),
    /**
     * Basel Land.
     */
    BL(13),
    /**
     * Basel Stadt.
     */
    BS(12),
    /**
     * Fribourg.
     */
    FR(10),
    /**
     * Genève.
     */
    GE(25),
    /**
     * Graubünden.
     */
    GR(18),
    /**
     * Glarus.
     */
    GL(8),
    /**
     * Jura.
     */
    JU(26),
    /**
     * Luzern.
     */
    LU(3),
    /**
     * Neuchâtel.
     */
    NE(24),
    /**
     * Niedwalden.
     */
    NW(7),
    /**
     * Obwalden.
     */
    OW(6),
    /**
     * St. Gallen.
     */
    SG(17),
    /**
     * Schaffhausen.
     */
    SH(14),
    /**
     * Solothurn.
     */
    SO(11),
    /**
     * Schwyz.
     */
    SZ(5),
    /**
     * Thurgau.
     */
    TG(20),
    /**
     * Ticino.
     */
    TI(21),
    /**
     * Uri.
     */
    UR(4),
    /**
     * Vaud.
     */
    VD(22),
    /**
     * Wallis.
     */
    VS(23),
    /**
     * Zug.
     */
    ZG(9),
    /**
     * Zürich.
     */
    ZH(1);
    private int geoId;

    /**
     * Erstellt eine neue Instanz der Enum.
     *
     * @param aGeoId Id, welche für geoJson genutzt wird.
     */
    private Canton(final int aGeoId) {
        geoId = aGeoId;
    }

    /**
     * Liefert die Kantonsrepräsentation, welche der übergebenen GeoId entspricht. Diese Methode
     * liefert niemals <tt>null</tt>.
     *
     * @param aGeoId Id gemäss geoJson
     * @return Kanton zum übergebenen Wert.
     * @throws IllegalArgumentException wenn zur Id kein Kanton existiert.
     */
    public static Canton fromGeoId(final int aGeoId) throws IllegalArgumentException {
        for (Canton canton : values()) {
            if (canton.asGeoId() == aGeoId) {
                return canton;
            }
        }
        throw new IllegalArgumentException("'" + aGeoId + "' is not a valid geoId for a canton.");
    }

    /**
     * Liefert die Kantonsrepräsentation, welche dem übergebenen DB-Key entspricht. Diese Methode
     * liefert niemals <tt>null</tt>.
     *
     * @param aRecordKey Id aus der Datenbank (z.B. Teilschlüssel aus {@link StructureNumber}.
     * @return Kanton zum übergebenen Wert.
     * @throws IllegalArgumentException wenn zur Id kein Kanton existiert.
     */
    public static Canton fromDbKey(final String aRecordKey) throws IllegalArgumentException {
        for (Canton canton : values()) {
            if (canton.asDbKey().equals(aRecordKey)) {
                return canton;
            }
        }
        throw new IllegalArgumentException("'" + aRecordKey
                + "' is not a valid recordKey for a canton.");
    }

    @Override
    public Integer asGeoId() {
        return geoId;
    }

    @Override
    public String asDbKey() {
        return AlphanumericKey.asKey(geoId, 2);
    }

}
