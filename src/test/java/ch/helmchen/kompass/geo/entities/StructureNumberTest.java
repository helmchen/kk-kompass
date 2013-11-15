/*
 * Copyright 2013 helmut.
 * Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file except in
 * compliance with the License. Obtain your copy at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing permissions and limitations.
 */
package ch.helmchen.kompass.geo.entities;

//CHECKSTYLE:OFF Unit Test

import ch.helmchen.kompass.meta.ApplicationInfo;
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
public class StructureNumberTest {

    public StructureNumberTest() {
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
    public void testValueOf_validString() {
        String aValue = "756.02.0246.00351";
        ApplicationInfo.info(StructureNumberTest.class, ApplicationInfo.TEST, "unitTest", "testValueOf_validString", aValue);
        StructureNumber expResult = new StructureNumber(aValue);
        StructureNumber result = StructureNumber.valueOf(aValue);
        assertEquals(expResult, result);
    }

    @Test
    public void testValueOf_3args() {
        ApplicationInfo.info(StructureNumberTest.class, ApplicationInfo.TEST, "unitTest", "testValueOf_3args");
        Country aCountry = Country.SWITZERLAND;
        Canton aCanton = Canton.BE;
        String aZone = "1";
        StructureNumber expResult = new StructureNumber("756.02.1");
        StructureNumber result = StructureNumber.valueOf(aCountry, aCanton, aZone);
        assertEquals(expResult, result);
    }

    @Test
    public void testValueOf_LocatableArr() {
        ApplicationInfo.info(StructureNumberTest.class, ApplicationInfo.TEST, "unitTest", "testValueOf_LocatableArr");
        Locatable[] components = {Country.SWITZERLAND, Canton.BE, new ProvinceNumber("246"), new CommunityNumber("351")};
        StructureNumber expResult = new StructureNumber("756.02.0246.00351");
        StructureNumber result = StructureNumber.valueOf(components);
        assertEquals(expResult, result);
    }

    @Test
    public void testPropertyValue() {
        String expResult = "756.02.0246.00351";
        ApplicationInfo.info(StructureNumberTest.class, ApplicationInfo.TEST, "unitTest", "testPropertyValue", expResult);
        StructureNumber instance = new StructureNumber();
        instance.setValue(expResult);
        String result = instance.getValue();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetDepthCommunity() {
        StructureNumber instance = new StructureNumber("756.02.0246.00351");
        ApplicationInfo.info(StructureNumberTest.class, ApplicationInfo.TEST, "unitTest", "testValues", instance);
        StructureDepth expResult = StructureDepth.COMMUNITY;
        StructureDepth result = instance.getDepth();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetDepthCanton() {
        StructureNumber instance = new StructureNumber("756.02");
        ApplicationInfo.info(StructureNumberTest.class, ApplicationInfo.TEST, "unitTest", "testGetDepthCanton", instance);
        StructureDepth expResult = StructureDepth.STATE;
        StructureDepth result = instance.getDepth();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetParent() {
        StructureNumber instance = new StructureNumber("756.02.0246.00351");
        ApplicationInfo.info(StructureNumberTest.class, ApplicationInfo.TEST, "unitTest", "testGetParent", instance);
        StructureNumber expResult = new StructureNumber("756.02.0246");
        StructureNumber result = instance.getParent();
        assertEquals(expResult, result);
    }

}
