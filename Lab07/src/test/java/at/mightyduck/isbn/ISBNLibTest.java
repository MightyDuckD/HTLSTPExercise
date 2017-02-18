/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.mightyduck.isbn;

import at.mightyduck.isbn.ISBNLib;
import at.mightyduck.isbn.ISBNException;
import at.mightyduck.isbn.ISBN;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Simon
 */
public class ISBNLibTest {

    public ISBNLibTest() {
    }

    @Test
    public void testValid0() {
        ISBNLib lib = ISBNLib.getInstance();
        ISBN isbn = lib.clean("9780777777770");
        Assert.assertEquals("978", isbn.getPrefix());
        Assert.assertEquals("0", isbn.getRegistrationGroup());
        Assert.assertEquals("7777", isbn.getRegistrant());
        Assert.assertEquals("7777", isbn.getPublication());
        Assert.assertEquals("0", isbn.getCheckDigit());
        Assert.assertTrue(isbn.isValidCheckDigit());
        Assert.assertNotNull(isbn.getRegistrantAgency());
        Assert.assertNotNull(isbn.getRegistrationGroupAgency());
        Assert.assertEquals("978-0-7777-7777-0", isbn.toString());
    }

    @Test
    public void testValid1() {
        ISBNLib lib = ISBNLib.getInstance();
        ISBN isbn = lib.clean("9789512388882");
        Assert.assertEquals("978", isbn.getPrefix());
        Assert.assertEquals("951", isbn.getRegistrationGroup());
        Assert.assertEquals("23", isbn.getRegistrant());
        Assert.assertEquals("8888", isbn.getPublication());
        Assert.assertEquals("2", isbn.getCheckDigit());
        Assert.assertTrue(isbn.isValidCheckDigit());
        Assert.assertNotNull(isbn.getRegistrantAgency());
        Assert.assertNotNull(isbn.getRegistrationGroupAgency());
        Assert.assertEquals("978-951-23-8888-2", isbn.toString());
    }

    @Test
    public void testErrors() {
        ISBNLib lib = ISBNLib.getInstance();
        try {
            ISBN isbn = lib.clean("9786999999990");//invalid as in official manual
            Assert.fail("Exception should have been thrown");
        } catch (ISBNException ex) {
            Assert.assertEquals("Unbekannte Registration Group", ex.getMessage());
        }
    }

    @Test
    public void testInputFormat() {
        ISBNLib lib = ISBNLib.getInstance();
        Assert.assertEquals(
                "Auch wenn der Input komplett falsch unterteilt wurde, sollte er geparsed werden, solange er 13 Ziffern und sonst nur '-' besitzt.",
                "978-951-23-8888-2",
                lib.clean("978-9512-3-8-888-2").toString()
        );
        Assert.assertEquals("2",lib.clean("978951238888-X").getCheckDigit());
        Assert.assertEquals("9",lib.clean("978159059925-X").getCheckDigit());
        Assert.assertEquals("0",lib.clean("978013134796-x").getCheckDigit());
    }

    @Test
    public void testCheckDigit() {
        ISBNLib lib = ISBNLib.getInstance();
        Assert.assertEquals("2", lib.clean("978951238888-2").calculateCheckDigit());
        Assert.assertEquals("2", lib.clean("978951238888-2").getCheckDigit());

        Assert.assertEquals("9", lib.clean("978159059925-9").calculateCheckDigit());
        Assert.assertEquals("9", lib.clean("978159059925-9").getCheckDigit());

        Assert.assertEquals("0", lib.clean("978013134796-0").calculateCheckDigit());
        Assert.assertEquals("0", lib.clean("978013134796-0").getCheckDigit());

        Assert.assertTrue(lib.clean("978159059925-9").isValidCheckDigit());
        Assert.assertFalse(lib.clean("978159059925-4").isValidCheckDigit());
        Assert.assertFalse(lib.clean("978159059925-0").isValidCheckDigit());
        Assert.assertTrue(lib.clean("978013134796-0").isValidCheckDigit());
    }
}
