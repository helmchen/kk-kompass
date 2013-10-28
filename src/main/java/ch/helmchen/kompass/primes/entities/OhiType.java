/*
 * Copyright 2013 Helmut Gehrer.
 * Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file except in
 * compliance with the License. Obtain your copy at http://www.apache.org/licenses/LICENSE-2.0 .
 * Unless required by applicable law or agreed to in writing, software distributed under the License  
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing permissions and limitations.
 */
package ch.helmchen.kompass.primes.entities;

/**
 *
 * @author helmut
 */
public enum OhiType {

    /**
     *
     */
    BASE("Base"),

    /**
     *
     */
    HAM("HAM_RDS"), 

    /**
     *
     */
    HMO("HMO"), 

    /**
     *
     */
    DIV("DIV");
    
    private String fophName;

    private OhiType(String fophName) {
        this.fophName = fophName;
    }
    
    /**
     *
     * @param aFophName
     * @return
     */
    public static OhiType fromFophName(String aFophName) {
               for (OhiType ohiType : values()) {
            if (ohiType.asFophName().equals(aFophName)) {
                return ohiType;
            }
        }
        throw new IllegalArgumentException("'" + aFophName + "' is not a valid fophName for an OhiType.");
 
    }
    
    /**
     *
     * @return
     */
    public String asFophName() {
        return fophName;
    }
}
