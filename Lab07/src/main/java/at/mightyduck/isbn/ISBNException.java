/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.mightyduck.isbn;

/**
 *
 * @author Simon
 */
public class ISBNException extends RuntimeException {

    public ISBNException() {
    }

    public ISBNException(String message) {
        super(message);
    }

    public ISBNException(Throwable cause) {
        super(cause);
    }

    public ISBNException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
