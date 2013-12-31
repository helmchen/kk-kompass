/*
 * Copyright 2013 Helmut Gehrer.
 * Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file except in
 * compliance with the License. Obtain your copy at http://www.apache.org/licenses/LICENSE-2.0 .
 * Unless required by applicable law or agreed to in writing, software distributed under the License  
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing permissions and limitations.
 */
package ch.helmchen.kompass.util;

import ch.helmchen.kompass.insurance.entities.FophNumber;
import ch.helmchen.kompass.meta.ApplicationInfo;

/**
 *
 * @author helmut
 */
public class AlphanumericKey {

    private AlphanumericKey() {
        // this is a Utility.
    }

    /**
     *
     * @param aKey
     * @param aSize
     * @return
     */
    public static String asKey(Integer aKey, int aSize) {
        if (aKey == null) {
            return null;
        }
        if (aKey.intValue() < 0) {
            return null;
        }
        return asKey(String.valueOf(aKey), aSize);
    }

    /**
     *
     * @param aString
     * @param aSize
     * @return
     */
    public static String asKey(String aString, int aSize) throws NumberFormatException {
        if (aString == null || aString.isEmpty()) {
            return null;
        }

        int checkPositive = Integer.parseInt(aString);
        if (checkPositive == 0) {
            // 0 wird gleich behandelt wie leer, oder null.
            return null;
        }
        if (checkPositive < 0) {
            throw new NumberFormatException("Only positive values are allowed as keys. '"
                    + aString + "' does not apply to this rule.");
        }
        StringBuilder result = new StringBuilder(aString.trim());
        if (result.length() > aSize) {
            throw new NumberFormatException("This key allows a maximum size of " + aSize + ". "
                    + "The value '" + aString + "' is to long.");
        }

        while (result.length() < aSize) {
            // String is to short: adding Zeroes to the left.
            ApplicationInfo.debug(AlphanumericKey.class, ApplicationInfo.VALIDATION, ApplicationInfo.ADDING_TO_UNDERSIZED, result, "0");
            result.insert(0, '0');
        }
        return result.toString();
    }

}
