/*
 * Copyright 2013 Helmut Gehrer.
 * Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file except in
 * compliance with the License. Obtain your copy at http://www.apache.org/licenses/LICENSE-2.0 .
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing permissions and limitations.
 */
package ch.helmchen.kompass.geo.entities;

/**
 * Deklariert die Id eines Objektes, welches in der PoliticalStructure abgelegt und auf einer
 * Landkarte angezeigt werden kann. Das Locatable liefert mindestens zwei Werte zurück<ul>
 * <li>die GeoId, welche als Referenz für GeoJson dient</li>
 * <li>die DB-Repräsenetation der GeoId. Diese ist als String aufbereitet und von Vorteil immer gleich
 * lang.</ul>
 *
 * @author helmut
 */
public interface Locatable {

    /**
     * Liefert die geoID des Objektes. Diese Geo-Id wird von GeoJson zur Identifikation eines Objektes
     * auf der Karte verwendet. Der Wert ist immer numerisch und entspricht in der Schweiz der
     * offiziellen (Gmeinde- oder Amt-) Nummer, welche vom BFS (Bundesamt für Statistik) vergeben
     * wird.
     *
     * @return Numerische Id für GeoJson.
     */
    Integer asGeoId();

    /**
     * Liefert die DB-Id für dieses Objekt. Dies ist der alhpanumerische Wert für die Datenhaltung. Da
     * die Objekte in der {@link StructureNumber} hierarchisch abgelegt werden, sollten alle Elemente
     * eines Typs die gleiche Länge haben (z.B. für Like-Queries).
     *
     * @return Alphanumerische Id (beispielsweise die numerische mit führenden Nullen).
     */
    String asDbKey();

}
