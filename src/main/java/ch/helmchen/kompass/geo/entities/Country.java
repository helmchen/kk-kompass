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

/**
 * Definiert den Wertebereich der verfügbaren Länder.
 *
 * @author helmut
 */
public enum Country implements Locatable {

    /**
     * Schweiz.
     */
    SWITZERLAND("CH", 756);
    private static final int FiELD_SIZE = 3;
    private final int geoId;
    private final String iso3166;

    private Country(final String anIso3166, final int aGeoId) {
        iso3166 = anIso3166;
        geoId = aGeoId;
    }

    /**
     * Liefert anhand der Geo-Id (aus geoJson) das Land zurück.
     *
     * @param aGeoId  Geoid des Landes.
     * @return
     */
    public static Country fromGeoId(final int aGeoId) {
        for (Country country : values()) {
            if (country.asGeoId() == aGeoId) {
                return country;
            }
        }
        throw new IllegalArgumentException("'" + aGeoId + "' is not a valid geoId for a country.");
    }

    /**
     * Liefert anhand des Iso-Codes das Land zurück.
     * @param anIso3166  Iso-Code
     * @return Land zum Iso-Code
     * @throws IllegalArgumentException, falls nichts esistiert.
     */
    public static Country fromIso3166(final String anIso3166) throws IllegalArgumentException {
        for (Country country : values()) {
            if (country.asIso3166().equals(anIso3166)) {
                return country;
            }
        }
        throw new IllegalArgumentException("'" + anIso3166
                + "' is not a valid recordKey for a country.");
    }

    /**
     * Liefert das Land anhand des DB-KEys.
     * @param aRecordKey  D-Key
     * @return
     */
    public static Country fromDbKey(final String aRecordKey) {
        for (Country country : values()) {
            if (country.asDbKey().equals(aRecordKey)) {
                return country;
            }
        }
        throw new IllegalArgumentException("'" + aRecordKey
                + "' is not a valid recordKey for a country.");
    }

    @Override
    public Integer asGeoId() {
        return geoId;
    }

    @Override
    public String asDbKey() {
        return AlphanumericKey.asKey(geoId, FiELD_SIZE);
    }

    /**
     *
     * @return
     */
    public String asIso3166() {
        return iso3166;
    }
}
