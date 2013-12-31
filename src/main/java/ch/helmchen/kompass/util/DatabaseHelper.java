/*
 * Copyright 2013 helmut.
 * Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file except in
 * compliance with the License. Obtain your copy at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing permissions and limitations.
 */
package ch.helmchen.kompass.util;

import java.util.Date;

/**
 *
 * @author helmut
 */
public class DatabaseHelper {

    private static final String ONE_ORE_MULTIPLE_CHARS = "%";
    
    private static final Date MAX_END_DATE;
    private static final long DECEMBER_31ST_3000 = 32535126000000L;
    
    static {
        MAX_END_DATE = new Date(DECEMBER_31ST_3000);
    }

    // FIXME ESAPI-Komponente für Datenbank-Encoding ergänzen und nutzen.
    /**
     * Passt den Query-String so an, dass der Suchbegriff als Beginn der Suche verwendet
     * werden kann.
     * 
     * @param aSearchParam Suchbegriff
     * @return Suchbegriff ergänzt für like Suchbegriff%
     */
    public static String beginsWith(final String aSearchParam) {
        if (aSearchParam == null) {
            return ONE_ORE_MULTIPLE_CHARS;
        }
        return aSearchParam + ONE_ORE_MULTIPLE_CHARS;
    }
    
/**
 * Liefert das Maximal-Gültig-Bis Datum zurück. dieses Datum wird als Platzhalter für
 * "unbeschränkt gültig" verwendet.
 * 
 * @return Maximal unterstütztes Endedatum (31.12.3000)
 */
    public static Date getMaxEndDate() {
        return MAX_END_DATE;
    }
}
