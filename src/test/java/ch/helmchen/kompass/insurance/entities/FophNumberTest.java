/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.helmchen.kompass.insurance.entities;

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
import org.junit.runners.Parameterized.Parameters;

/**
 *
 * @author helmut
 */
@RunWith(Parameterized.class)
public class FophNumberTest {

    private String description;
    private String string;
    private Integer number;
    private String expValue;
    private Boolean expValid;

    public FophNumberTest(String description, String string, Integer number, String expValue, Boolean valid) {
        this.description = description;
        this.string = string;
        this.number = number;
        this.expValue = expValue;
        this.expValid = valid;
    }

    @Parameters
    public static List<Object[]> data() {
        return Arrays.asList(new Object[][]{
            {"Visana", "1555", 1555, "1555", true},
            {"sana24", "1568", 1568, "1568", true},
            {"kurznummer1", "1", 1, "0001", true},
            {"kurznummer2", "12", 12, "0012", true},
            {"kurznummer3", "123", 123, "0123", true},
            {"langnummer1", "12345", 12345, "2345", false},
            {"langnummer2", "123456", 123456, "3456", false},
            {"grenzwert1", "1999", 1999, "1999", true},
            {"grenzwert2", "2000", 2000, "2000", false},
            {"alphanumeric", "abcd", null, "abcd", false},
            {"leer", "", 0, "0000", false},
            {"nulltest", null, null, null, false}
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
     * Test of setValue method, of class FophNumber.
     */
    @Test
    public void testSetValue() {
        ApplicationInfo.info(FophNumberTest.class, ApplicationInfo.TEST, "testcase", "testSetValue", string);
        FophNumber instance = new FophNumber();

        instance.setValue(string);

        assertEquals(
                this.expValue, instance.getValue());
    }

    /**
     * Test of isValid method, of class FophNumber.
     */
    @Test
    public void testIsValidString() {
        ApplicationInfo.info(FophNumberTest.class, ApplicationInfo.TEST, "testcase", "testIsValidString", string);
        boolean result = FophNumber.isValid(string);

        assertEquals(expValid, result);
    }

    /**
     * Test of isValid method, of class FophNumber.
     */
    @Test
    public void testIsValidNumber() {
        ApplicationInfo.info(FophNumberTest.class, ApplicationInfo.TEST, "testcase", "testIsValidNumber", number);
        boolean result = FophNumber.isValid(number);

        assertEquals(expValid, result);
    }

    /**
     * Test of valueOf method, of class FophNumber.
     */
    @Test
    public void testValueOfString() {
        ApplicationInfo.info(FophNumberTest.class, ApplicationInfo.TEST, "testcase", "testValueOfString", string);
        FophNumber result = FophNumber.valueOf(string);
        if (string == null || string.isEmpty()) {
            assertEquals(null, result);
        } else {
            assertEquals(this.expValue, result.getValue());
        }
    }

    /**
     * Test of valueOf method, of class FophNumber.
     */
    @Test
    public void testValueOfNumber() {
        ApplicationInfo.info(FophNumberTest.class, ApplicationInfo.TEST, "testcase", "testValueOfNumber", number);
        FophNumber result = FophNumber.valueOf(number);
        if (number == null || number < 1) {
            assertEquals(null, result);
        } else {
            assertEquals(expValue, result.getValue());
        }
    }
}