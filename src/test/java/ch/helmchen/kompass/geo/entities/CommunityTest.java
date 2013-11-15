/*
 * Copyright 2013 helmut.
 * Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file except in
 * compliance with the License. Obtain your copy at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing permissions and limitations.
 */
package ch.helmchen.kompass.geo.entities;

// CHECKSTYLE:OFF Unit-Test
import ch.helmchen.kompass.meta.ApplicationInfo;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class CommunityTest {

    public CommunityTest() {
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

    @Test
    public void testPropertyId() {
        ApplicationInfo.info(CommunityTest.class, ApplicationInfo.TEST, "unitTest", "testPropertyId");
        final Community instance = new Community();
        final int expResult = 4711;
        instance.setId(expResult);
        final int result = instance.getId();
        assertEquals(expResult, result);
    }

    @Test
    public void testPropertyCommunityNumber() {
        ApplicationInfo.info(CommunityTest.class, ApplicationInfo.TEST, "unitTest", "testPropertyCommunityNumber");
        Community instance = new Community();
        CommunityNumber expResult = new CommunityNumber("12345");
        instance.setCommunityNumber(expResult);
        CommunityNumber result = instance.getCommunityNumber();
        assertEquals(expResult, result);
    }

    @Test
    public void testPropertyVersion() {
        ApplicationInfo.info(CommunityTest.class, ApplicationInfo.TEST, "unitTest", "testPropertyVersion");
        Community instance = new Community();
        int expResult = 11;
        instance.setVersion(expResult);
        int result = instance.getVersion();
        assertEquals(expResult, result);
    }

    @Test
    public void testPropertyStructureNumber() {
        ApplicationInfo.info(CommunityTest.class, ApplicationInfo.TEST, "unitTest", "testPropertyStructureNumber");
        Community instance = new Community();
        StructureNumber expResult = new StructureNumber("CH.BE.12345");
        instance.setStructureNumber(expResult);
        StructureNumber result = instance.getStructureNumber();
        assertEquals(expResult, result);
    }

    @Test
    public void testPropertyOhiZone() {
        ApplicationInfo.info(CommunityTest.class, ApplicationInfo.TEST, "unitTest", "testPropertyOhiZone");
        System.out.println("getOhiZone");
        Community instance = new Community();
        String expResult = "CH.BE.1";
        instance.setOhiZone(expResult);
        String result = instance.getOhiZone();
        assertEquals(expResult, result);
    }

}
