/*
 * Copyright 2013 helmut.
 * Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file except in
 * compliance with the License. Obtain your copy at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing permissions and limitations.
 */
package ch.helmchen.kompass.geo.entities;
//CHECKSTYLE:OFF Unit-Test

import ch.helmchen.kompass.insurance.entities.FophNumberTest;
import ch.helmchen.kompass.meta.ApplicationInfo;
import java.util.Arrays;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)

public class CommunityNumberTest {

    private static String lastDescription;
    private String description;
    private Integer geoKey;
    private String dbKey;
    public boolean valid;

    public CommunityNumberTest(String description, Integer geoKey, String dbKey, boolean valid) {
        this.description = description;
        this.geoKey = geoKey;
        this.dbKey = dbKey;
        this.valid = valid;

        if (!description.equals(lastDescription)) {
            ApplicationInfo.info(CommunityNumberTest.class, ApplicationInfo.TEST, ApplicationInfo.SETUP, toString());
            lastDescription = description;
        }

    }

    @Parameterized.Parameters
    public static List<Object[]> data() {
        return Arrays.asList(new Object[][]{
            {"shortNumber", new Integer(1), "00001", true},
            {"mediumNumber", new Integer(123), "00123", true},
            {"longNumber", new Integer(54321), "54321", true},
            {"toLongNumber", new Integer(123456), "123456", false},
            {"negativeNumber", new Integer(-55), "-55", false},
            {"emptyNumber", new Integer(0), null, true}, // erlaubt, muss null zurückgeben.
            {"null", null, null, true}
        });
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getValue method, of class CommunityNumber.
     */
    @Test
    public void testPropertyValue() {
        try {
            ApplicationInfo.info(
                    CommunityNumberTest.class, ApplicationInfo.TEST, ApplicationInfo.UNIT_TEST, "testPropertyValue", dbKey);
            CommunityNumber instance = new CommunityNumber();
            String expResult = dbKey;
            instance.setValue(expResult);
            String result = instance.getValue();
            assertEquals(expResult, result);
        }
        catch (NumberFormatException invalidNumber) {
            ApplicationInfo.warn(
                    CommunityNumberTest.class, ApplicationInfo.TEST, ApplicationInfo.UNHANDLED_EXCEPTION, invalidNumber.getLocalizedMessage());
        }
    }

    @Test
    public void testPropertyValueWithConversion() {
        if (valid) {
            ApplicationInfo.info(
                    CommunityNumberTest.class, ApplicationInfo.TEST, ApplicationInfo.UNIT_TEST, "testPropertyValueWithConversion", geoKey);
            CommunityNumber instance = new CommunityNumber();
            String expResult = dbKey;
            if (geoKey == null) {
                instance.setValue(null);
            } else {
                instance.setValue(String.valueOf(geoKey.intValue()));
            }
            String result = instance.getValue();
            assertEquals(expResult, result);
        }
    }

    @Test(expected = NumberFormatException.class)
    public void testPropertyValueWithConversionErrors() {
        if (valid) {
            throw new NumberFormatException("This testcase must fail without testing.");
        } else {
            ApplicationInfo.info(
                    CommunityNumberTest.class, ApplicationInfo.TEST, ApplicationInfo.UNIT_TEST, "testPropertyValueWithConversionErrors", geoKey);
            CommunityNumber instance = new CommunityNumber();
            String expResult = dbKey;
            if (geoKey == null) {
                instance.setValue(null);
            } else {
                instance.setValue(String.valueOf(geoKey.intValue()));
            }
            String result = instance.getValue();

            assertEquals(expResult, result);
        }
    }

    /**
     * Test of asGeoId method, of class CommunityNumber.
     */
    @Test
    public void testAsGeoId() {
        if (valid) {
            ApplicationInfo.info(
                    CommunityNumberTest.class, ApplicationInfo.TEST, ApplicationInfo.UNIT_TEST, "testAsGeoId", dbKey);
            CommunityNumber instance = new CommunityNumber(dbKey);
            Integer expResult = geoKey;
            if (!valid || geoKey == null || geoKey.intValue() == 0) {
                expResult = null;
            }
            Integer result = instance.asGeoId();
            assertEquals(expResult, result);
        }
    }

    /**
     * Test of asGeoId method, of class CommunityNumber.
     */
    @Test(expected = NumberFormatException.class)
    public void testAsGeoIdWithErrors() {
        if (valid) {
            throw new NumberFormatException("This testcase must fail without testing.");
        } else {
            ApplicationInfo.info(
                    CommunityNumberTest.class, ApplicationInfo.TEST, ApplicationInfo.UNIT_TEST, "testAsGeoId", dbKey);
            CommunityNumber instance = new CommunityNumber(dbKey);
            Integer expResult = geoKey;
            if (!valid) {
                expResult = null;
            }
            Integer result = instance.asGeoId();
            assertEquals(expResult, result);
        }
    }

    /**
     * Test of asDbKey method, of class CommunityNumber.
     */
    @Test
    public void testAsDbKey() {
        try {
            ApplicationInfo.info(
                    CommunityNumberTest.class, ApplicationInfo.TEST, ApplicationInfo.UNIT_TEST, "testAsDbKey", dbKey);
            CommunityNumber instance = new CommunityNumber(dbKey);
            String expResult = dbKey;
            String result = instance.asDbKey();
            assertEquals(expResult, result);
        }
        catch (NumberFormatException invalidNumber) {
            ApplicationInfo.warn(
                    CommunityNumberTest.class, ApplicationInfo.TEST, ApplicationInfo.UNHANDLED_EXCEPTION, invalidNumber.getLocalizedMessage());
        }

    }

    @Override
    public String toString() {
        return "CommunityNumberTest{" + "description=" + description + ", geoKey=" + geoKey + ", dbKey=" + dbKey + ", valid=" + valid + '}';
    }

}
