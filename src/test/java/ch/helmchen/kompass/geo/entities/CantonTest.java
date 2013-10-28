/*
 * Copyright 2013 Helmut Gehrer.
 * Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file except in
 * compliance with the License. Obtain your copy at http://www.apache.org/licenses/LICENSE-2.0 .
 * Unless required by applicable law or agreed to in writing, software distributed under the License  
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing permissions and limitations.
 */
package ch.helmchen.kompass.geo.entities;

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
        System.out.println("values");
        Canton[] result = Canton.values();
        assertEquals(26, result.length);
    }
    
    @Test
    public void testUniqueGeoIds() {
        System.out.println("testUniqueGeoIds");
        Set<Integer> uniqueIds = new HashSet<Integer>();
        for (Canton canton:Canton.values()) {
            uniqueIds.add(canton.asGeoId());
        }
        assertEquals(Canton.values().length, uniqueIds.size());
    }


    /**
     * Test of valueOf method, of class Canton.
     */
    @Test
    public void testValueOf() {
        System.out.println("valueOf");
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
        System.out.println("fromGeoIdAg");
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
        System.out.println("fromGeoId");
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
        System.out.println("fromDbKey");
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
        System.out.println("fromDbKey");
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
        System.out.println("asGeoId");
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
        System.out.println("asGeoId");
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
        System.out.println("asDbKey");
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
        System.out.println("asDbKey");
        Canton instance = Canton.BE;
        String expResult = "02";
        String result = instance.asDbKey();
        assertEquals(expResult, result);
    }
    
}
