/*
 * Copyright 2013 helmut.
 * Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file except in
 * compliance with the License. Obtain your copy at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing permissions and limitations.
 */
package ch.helmchen.kompass.geo.entities;

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
public class PoliticalStructureTest {

    public PoliticalStructureTest() {
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
     * Test of getId method, of class PoliticalStructure.
     */
    @Test
    public void testPropertytId() {
        System.out.println("getId");
        PoliticalStructure instance = new PoliticalStructure();
        int expResult = 4711;
        instance.setId(expResult);
        int result = instance.getId();
        assertEquals(expResult, result);
    }

    /**
     * Test of getStructureNumber method, of class PoliticalStructure.
     */
    @Test
    public void testPropertyStructureNumber() {
        System.out.println("getStructureNumber");
        PoliticalStructure instance = new PoliticalStructure();
        StructureNumber expResult = new StructureNumber("756.01.443.12345");
        instance.setStructureNumber(expResult);
        StructureNumber result = instance.getStructureNumber();
        assertEquals(expResult, result);
    }

    /**
     * Test of getVersion method, of class PoliticalStructure.
     */
    @Test
    public void testPropertyVersion() {
        System.out.println("getVersion");
        PoliticalStructure instance = new PoliticalStructure();
        int expResult = 99;
        instance.setVersion(expResult);
        int result = instance.getVersion();
        assertEquals(expResult, result);
    }
   /**
     * Test of getVersion method, of class PoliticalStructure.
     */
    @Test
    public void testPropertyLevel() {
        System.out.println("getVersion");
        PoliticalStructure instance = new PoliticalStructure();
        int expResult = 3;
        instance.setLevel(expResult);
        int result = instance.getLevel();
        assertEquals(expResult, result);
    }

    /**
     * Test of getCode method, of class PoliticalStructure.
     */
    @Test
    public void testPropertyCode() {
        System.out.println("getCode");
        PoliticalStructure instance = new PoliticalStructure();
        String expResult = "3014";
        instance.setCode(expResult);
        String result = instance.getCode();
        assertEquals(expResult, result);

    }

    /**
     * Test of getName method, of class PoliticalStructure.
     */
    @Test
    public void testPropertyName() {
        System.out.println("getName");
        PoliticalStructure instance = new PoliticalStructure();
        String expResult = "Bern-Breitenrain";
        instance.setName(expResult);
        String result = instance.getName();
        assertEquals(expResult, result);
    }

    /**
     * Test of getLatitude method, of class PoliticalStructure.
     */
    @Test
    public void testPropertyLatitude() {
        System.out.println("getLatitude");
        PoliticalStructure instance = new PoliticalStructure();
        Double expResult = 47.15d;
        instance.setLatitude(expResult);
        Double result = instance.getLatitude();
        assertEquals(expResult, result);
    }

    /**
     * Test of getLongitude method, of class PoliticalStructure.
     */
    @Test
    public void testPropertyLongitude() {
        System.out.println("getLongitude");
        PoliticalStructure instance = new PoliticalStructure();
        Double expResult = 7.6901d;
        instance.setLongitude(expResult);
        Double result = instance.getLongitude();
        assertEquals(expResult, result);
    }

    /**
     * Test of getAccuracy method, of class PoliticalStructure.
     */
    @Test
    public void testPropertyAccuracy() {
        System.out.println("getAccuracy");
        PoliticalStructure instance = new PoliticalStructure();
        Short expResult = 1;
        instance.setAccuracy(expResult);
        Short result = instance.getAccuracy();
        assertEquals(expResult, result);
    }

}
