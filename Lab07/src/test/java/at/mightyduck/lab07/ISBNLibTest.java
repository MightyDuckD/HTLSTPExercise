/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.mightyduck.lab07;

import at.mightyduck.lab07.validator.IsbnChecksumValidator;
import javax.faces.FacesException;
import org.junit.Test;
import static org.junit.Assert.*;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author Simon
 */
public class ISBNLibTest {

    private IsbnChecksumValidator v = new IsbnChecksumValidator();

    public ISBNLibTest() {
    }

//    @Test(expected = ValidatorException.class)
//    public void otherTest() {
//        v.validate(null, null, "9786999999990");//reg group = invalid -> excpetion
//    }
//
//    @Test
//    public void testSomeMethod() {
//        v.validate(null, null, "9780777777770");//reg group len = 1
//    }

}
