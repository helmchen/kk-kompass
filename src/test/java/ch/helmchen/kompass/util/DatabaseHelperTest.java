/*
 * Copyright 2013 helmut.
 * Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file except in
 * compliance with the License. Obtain your copy at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing permissions and limitations.
 */

package ch.helmchen.kompass.util;

import ch.helmchen.kompass.meta.ApplicationInfo;
import java.util.Calendar;
import java.util.Date;
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
public class DatabaseHelperTest {
    
    public DatabaseHelperTest() {
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
     * Test of beginsWith method, of class DatabaseHelper.
     */
    @Test
    public void testBeginsWith() {
        String aSearchParam = "hallo";
        String expResult = "hallo%";
        ApplicationInfo.info(
                DatabaseHelperTest.class, ApplicationInfo.TEST, ApplicationInfo.UNIT_TEST, "testBeginsWith", aSearchParam);
        String result = DatabaseHelper.beginsWith(aSearchParam);
        assertEquals(expResult, result);
    }
 @Test   
    public void testBeginsWithNull() {
        String aSearchParam = null;
        String expResult = "%";
        ApplicationInfo.info(
                DatabaseHelperTest.class, ApplicationInfo.TEST, ApplicationInfo.UNIT_TEST, "testBeginsWithNull", aSearchParam);
        String result = DatabaseHelper.beginsWith(aSearchParam);
        assertEquals(expResult, result);
    }

 @Test   
    public void testBeginsWithEmpty() {
        String aSearchParam = "";
        String expResult = "%";
        ApplicationInfo.info(
                DatabaseHelperTest.class, ApplicationInfo.TEST, ApplicationInfo.UNIT_TEST, "testBeginsWithEmpty", aSearchParam);
        String result = DatabaseHelper.beginsWith(aSearchParam);
        assertEquals(expResult, result);
    }

    /**
     * Test of getMaxEndDate method, of class DatabaseHelper.
     */
    @Test
    public void testGetMaxEndDate() {
        ApplicationInfo.info(
                DatabaseHelperTest.class, ApplicationInfo.TEST, ApplicationInfo.UNIT_TEST, "testBeginsWithEmpty");
       // Date expResult = null;
        Date result = DatabaseHelper.getMaxEndDate();
        Calendar cal = Calendar.getInstance();
        cal.setTime(result);
        assertEquals(3000, cal.get(Calendar.YEAR));
        assertEquals(Calendar.DECEMBER, cal.get(Calendar.MONTH));
        assertEquals(31, cal.get(Calendar.DATE));
    }
 
}