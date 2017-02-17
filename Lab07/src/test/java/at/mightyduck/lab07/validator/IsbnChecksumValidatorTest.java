/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.mightyduck.lab07.validator;

import org.apache.commons.validator.ISBNValidator;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Simon
 */
public class IsbnChecksumValidatorTest {
    
    public IsbnChecksumValidatorTest() {
    }

    @org.junit.Test
    public void testSomeMethod() {
        IsbnChecksumValidator v = new IsbnChecksumValidator();
        v.validateFormat("9786999999990");
        
    }
    
}
