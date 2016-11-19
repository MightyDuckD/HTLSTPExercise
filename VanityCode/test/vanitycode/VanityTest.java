/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vanitycode;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author 20120093
 */
public class VanityTest {

    public VanityTest() {
    }

    @Test
    public void testTimes() {
        assertEquals("AAAAA", Vanity.times("A", 5));
        assertEquals("BBBBB", Vanity.times("B", 5));
        assertEquals("", Vanity.times("A", 0));
        assertEquals("AbAbAbAbAb", Vanity.times("Ab", 5));
        assertEquals("", Vanity.times("", 40));
    }

    @Test
    public void testSimpleencode() {
        assertEquals("44 2 555 555 666", Vanity.simpleencode("Hallo"));
        assertEquals("2 0 22", Vanity.simpleencode("A B"));
        assertEquals("", Vanity.simpleencode(""));
    }

    @Test
    public void testAdvancedencode() {
        assertEquals("4 2 5 5 6", Vanity.advancedencode("Hallo"));
        assertEquals("2 0 2", Vanity.advancedencode("A B"));
        assertEquals("", Vanity.advancedencode(""));
    }

    @Test
    public void testSimpledecode() {
        assertEquals("HALLO", Vanity.simpledecode("44 2 555 555 666"));

    }

}
