/*
 * Copyright 2013 helmut.
 * Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file except in
 * compliance with the License. Obtain your copy at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing permissions and limitations.
 */

package ch.helmchen.kompass.meta;

/**
 * Dient dazu, eine Entity als versionisierbar zu markieren.
 * @author helmut
 */
public interface Versionable {
    /**
     * Liefert die Versionsnummer der Entity.
     * @return Versionsnummer.
     */
   int getVersion();
   
}
