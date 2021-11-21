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
    InventoryValidator validator;

    @BeforeEach
    void init()
    {
        validator = new InventoryValidator();
        validator.setErrorDialogsEnabled(false);
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
    }

    @Test
    void isValidSerial_Negative() {
    }

    @Test
    void isValidMonetaryValue_Positive() {
    }

    @Test
    void isValidMonetaryValue_Negative() {
    }

    @Test
    void validateAllInputs_Positive() {
    }

    @Test
    void validateAllInputs_Negative() {
    }
}