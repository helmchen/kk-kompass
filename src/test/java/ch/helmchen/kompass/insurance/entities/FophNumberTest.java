/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.helmchen.kompass.insurance.entities;
//CHECKSTYLE:OFF Test

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

@RunWith(Parameterized.class)
public class FophNumberTest {

    private static String lastDescription;
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
        if (!description.equals(lastDescription)) {
            ApplicationInfo.info(FophNumberTest.class, ApplicationInfo.TEST, "setup", toString());
            lastDescription = description;
        }
    }

    @Parameters
    public static List<Object[]> data() {
        return Arrays.asList(new Object[][]{
            {"Visana", "1555", 1555, "1555", true},
            {"sana24", "1568", 1568, "1568", true},
            {"kurznummer1", "1", 1, "0001", true},
            {"kurznummer2", "12", 12, "0012", true},
            {"kurznummer3", "123", 123, "0123", true},
            {"grenzwert1", "1999", 1999, "1999", true},
            {"grenzwert2", "2000", 2000, null, false},
            {"langnummer1", "12345", 12345, null, false},
            {"langnummer2", "123456", 123456, null, false},
            {"alphanumeric", "abcd", null, null, false},
            {"leer", "", 0, null, false},
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

    @Test
    public void testSetValue() {
        ApplicationInfo.info(FophNumberTest.class, ApplicationInfo.TEST, "unitTest", "testSetValue", string);
        FophNumber instance = new FophNumber();
        try {
            instance.setValue(string);
        }
        catch (NumberFormatException invalid) {
            if (expValid) {
                throw invalid;
            }
        }
        assertEquals(expValue, instance.getValue());
    }

    @Test
    public void testIsValidString() {
        ApplicationInfo.info(FophNumberTest.class, ApplicationInfo.TEST, "unitTest", "testIsValidString", string);
        boolean result = FophNumber.isValid(string);
        assertEquals(expValid, result);
    }

    @Test
    public void testIsValidNumber() {
        ApplicationInfo.info(FophNumberTest.class, ApplicationInfo.TEST, "unitTest", "testIsValidNumber", number);
        boolean result = FophNumber.isValid(number);
        assertEquals(expValid, result);
    }

    @Test
    public void testValueOfString() {
        ApplicationInfo.info(FophNumberTest.class, ApplicationInfo.TEST, "unitTest", "testValueOfString", string);
        FophNumber result = null;
        try {
            result = FophNumber.valueOf(string);
        }
        catch (NumberFormatException invalid) {
            if (expValid) {
                throw invalid;
            }

        }
        if (expValid) {
            assertEquals(expValue, result.getValue());
        } else {
            assertEquals(null, result);
        }
    }

    @Test
    public void testValueOfNumber() {
        ApplicationInfo.info(FophNumberTest.class, ApplicationInfo.TEST, "unitTest", "testValueOfNumber", number);
        FophNumber result = null;
        try {
            result = FophNumber.valueOf(number);
        }
        catch (NumberFormatException invalid) {
            if (expValid) {
                throw invalid;
            }
        }
        if (expValid) {
            assertEquals(expValue, result.getValue());
        } else {
            assertEquals(null, result);
        }
    }

    @Override
    public String toString() {
        return "FophNumberTest{" + "description=" + description + ", string=" + string + ", number=" + number + ", expValue=" + expValue + ", expValid=" + expValid + '}';
    }

}
