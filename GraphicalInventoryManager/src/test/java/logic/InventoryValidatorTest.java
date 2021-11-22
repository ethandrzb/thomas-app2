/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Ethan Thomas
 */

package logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InventoryValidatorTest
{
    Inventory inventory;
    InventoryValidator validator;

    @BeforeEach
    void init()
    {
        validator = new InventoryValidator();
        validator.setErrorDialogsEnabled(false);

        inventory = new Inventory();

        // Add dummy data
        inventory.addItem("item 1", "A-XXX-XXX-XXX", 1200.123);
        inventory.addItem("item 2", "A-XXX-XXX-XXW", 654.45);
        inventory.addItem("item 3", "A-XXX-XXX-XXV", 9.09);
    }

    @Test
    void errorDialogsEnabled()
    {
        assertFalse(validator.getErrorDialogsEnabled());

        validator.setErrorDialogsEnabled(true);

        assertTrue(validator.getErrorDialogsEnabled());
    }

    @Test
    void isValidItemName_Positive()
    {
        String testName = "test name";

        assertTrue(validator.isValidItemName(testName));
    }

    // 1 character short of minimum length requirement
    @Test
    void isValidItemName_Negative_Case_1()
    {
        String testName = "t";

        assertFalse(validator.isValidItemName(testName));
    }

    // 1 character above of maximum length requirement
    @Test
    void isValidItemName_Negative_Case_2()
    {
        String testName = "dfgahjlkgdfsahljkdgsahljkdgsaljhkgadshjklgadshljkgdashjklgdsahjlkgdsah" +
                "jklgdashljkdgsfalhjkdsafglhjkdfsalhjksdfalhjkfdsaljkhfdslkhjfsdalkhjfsdalkhjfsdlkhj" +
                "dfsalhjksfadlhjkasfdlhjksfadlhjksfadhljkdfsalhjkdafslhjkafsdhjkladfshjklsafdlhkjfads" +
                "lhkjfsadklhjffffffff";

        assertFalse(validator.isValidItemName(testName));
    }

    @Test
    void isValidSerial_Positive()
    {
        assertTrue(validator.isValidSerial(inventory, "A-XXX-XXX-XXU", ""));
    }

    // Incorrectly formatted serial
    @Test
    void isValidSerial_Negative_Case_1()
    {
        assertFalse(validator.isValidSerial(inventory, "A-XXX-XXX-XX", ""));
    }

    // Duplicate serial
    @Test
    void isValidSerial_Negative_Case_2()
    {

        assertFalse(validator.isValidSerial(inventory, "A-XXX-XXX-XXX", ""));
    }

    @Test
    void isValidMonetaryValue_Positive()
    {
        assertTrue(validator.isValidMonetaryValue("1.23"));

        assertTrue(validator.isValidMonetaryValue("1.20"));

        assertTrue(validator.isValidMonetaryValue("1.2"));

        assertTrue(validator.isValidMonetaryValue("1.0"));

        assertTrue(validator.isValidMonetaryValue("1.00"));

        assertTrue(validator.isValidMonetaryValue("0.09"));
    }

    // Incorrectly formatted number
    @Test
    void isValidMonetaryValue_Negative_Case_1()
    {
        assertFalse(validator.isValidMonetaryValue("."));

        assertFalse(validator.isValidMonetaryValue("q.0"));

        assertFalse(validator.isValidMonetaryValue("0.jh"));

        assertFalse(validator.isValidMonetaryValue("asd.dc"));
    }

    // Value must greater than zero
    @Test
    void isValidMonetaryValue_Negative_Case_2()
    {
        assertFalse(validator.isValidMonetaryValue("0.00"));

        assertFalse(validator.isValidMonetaryValue("0.0"));

        assertFalse(validator.isValidMonetaryValue("0."));

        assertFalse(validator.isValidMonetaryValue("0"));
    }

    @Test
    void validateAllInputs_Positive()
    {
        assertTrue(validator.validateAllInputs(inventory, "test name", "A-YYY-ZZZ-WWW", "321.12"));
    }

    @Test
    void validateAllInputs_Negative()
    {
        // Repeat of selected individual tests

        assertFalse(validator.validateAllInputs(inventory, "t", "A-YYY-ZZZ-WWW", "321.12"));

        assertFalse(validator.validateAllInputs(inventory, "test name", "A-YYY-ZZZ-WW", "321.12"));

        assertFalse(validator.validateAllInputs(inventory, "test name", "A-XXX-XXX-XXV", "321.12"));

        assertFalse(validator.validateAllInputs(inventory, "test name", "A-XXX-XXX-XXG", "0"));
    }
}