/*
 * Copyright 2013 Helmut Gehrer.
 * Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file except in
 * compliance with the License. Obtain your copy at http://www.apache.org/licenses/LICENSE-2.0 .
 * Unless required by applicable law or agreed to in writing, software distributed under the License  
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing permissions and limitations.
 */
package ch.helmchen.kompass.geo.entities;

//CHECKSTYLE:OFF Unit Test.
import ch.helmchen.kompass.insurance.entities.FophNumberTest;
import ch.helmchen.kompass.meta.ApplicationInfo;
import java.util.HashSet;
import java.util.Set;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author helmut
 */
public class CantonTest {

    public CantonTest() {
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
     * Test of values method, of class Canton.
     */
    @Test
    public void testValues() {
        ApplicationInfo.info(
                CantonTest.class, ApplicationInfo.TEST, ApplicationInfo.UNIT_TEST, "testValues");
        Canton[] result = Canton.values();
        assertEquals(26, result.length);
    }

    @Test
    public void testUniqueGeoIds() {
        ApplicationInfo.info(
                CantonTest.class, ApplicationInfo.TEST, ApplicationInfo.UNIT_TEST, "testUniqueGeoIds");
        Set<Integer> uniqueIds = new HashSet<Integer>();
        for (Canton canton : Canton.values()) {
            uniqueIds.add(canton.asGeoId());
        }
        assertEquals(Canton.values().length, uniqueIds.size());
    }

    /**
     * Test of valueOf method, of class Canton.
     */
    @Test
    public void testValueOf() {
        ApplicationInfo.info(
                CantonTest.class, ApplicationInfo.TEST, ApplicationInfo.UNIT_TEST, "testValueOf");
        String name = "BE";
        Canton expResult = Canton.BE;
        Canton result = Canton.valueOf(name);
        assertEquals(expResult, result);
    }

    /**
     * Test of fromGeoId method, of class Canton.
     */
    @Test
    public void testFromGeoIdAg() {
        ApplicationInfo.info(
                CantonTest.class, ApplicationInfo.TEST, ApplicationInfo.UNIT_TEST, "testFromGeoIdAg");
        int aGeoId = 19;
        Canton expResult = Canton.AG;
        Canton result = Canton.fromGeoId(aGeoId);
        assertEquals(expResult, result);
    }

    /**
     * Test of fromGeoId method, of class Canton.
     */
    @Test
    public void testFromGeoIdBe() {
        ApplicationInfo.info(
                CantonTest.class, ApplicationInfo.TEST, ApplicationInfo.UNIT_TEST, "testFromGeoIdBe");
        int aGeoId = 2;
        Canton expResult = Canton.BE;
        Canton result = Canton.fromGeoId(aGeoId);
        assertEquals(expResult, result);
    }

    /**
     * Test of fromDbKey method, of class Canton.
     */
    @Test
    public void testFromDbKeyAg() {
        ApplicationInfo.info(
                CantonTest.class, ApplicationInfo.TEST, ApplicationInfo.UNIT_TEST, "testFromDbKeyAg");
        String aRecordKey = "19";
        Canton expResult = Canton.AG;
        Canton result = Canton.fromDbKey(aRecordKey);
        assertEquals(expResult, result);
    }

    /**
     * Test of fromDbKey method, of class Canton.
     */
    @Test
    public void testFromDbKeyBe() {
        ApplicationInfo.info(
                CantonTest.class, ApplicationInfo.TEST, ApplicationInfo.UNIT_TEST, "testFromDbKeyBe");
        String aRecordKey = "02";
        Canton expResult = Canton.BE;
        Canton result = Canton.fromDbKey(aRecordKey);
        assertEquals(expResult, result);
    }

    /**
     * Test of asGeoId method, of class Canton.
     */
    @Test
    public void testAsGeoIdAg() {
        ApplicationInfo.info(
                CantonTest.class, ApplicationInfo.TEST, ApplicationInfo.UNIT_TEST, "testAsGeoIdAg");
        Canton instance = Canton.AG;
        Integer expResult = new Integer(19);
        Integer result = instance.asGeoId();
        assertEquals(expResult, result);
    }

    /**
     * Test of asGeoId method, of class Canton.
     */
    @Test
    public void testAsGeoIdBe() {
        ApplicationInfo.info(
                CantonTest.class, ApplicationInfo.TEST, ApplicationInfo.UNIT_TEST, "testAsGeoIdBe");
        Canton instance = Canton.BE;
        Integer expResult = new Integer(2);
        Integer result = instance.asGeoId();
        assertEquals(expResult, result);
    }

    /**
     * Test of asDbKey method, of class Canton.
     */
    @Test
    public void testAsDbKeyAg() {
        ApplicationInfo.info(
                CantonTest.class, ApplicationInfo.TEST, ApplicationInfo.UNIT_TEST, "testAsDbKeyAg");
        Canton instance = Canton.AG;
        String expResult = "19";
        String result = instance.asDbKey();
        assertEquals(expResult, result);
    }

    /**
     * Test of asDbKey method, of class Canton.
     */
    @Test
    public void testAsDbKeyBe() {
        ApplicationInfo.info(
                CantonTest.class, ApplicationInfo.TEST, ApplicationInfo.UNIT_TEST, "testAsDbKeyBe");
        Canton instance = Canton.BE;
        String expResult = "02";
        String result = instance.asDbKey();
        assertEquals(expResult, result);
    }
}
