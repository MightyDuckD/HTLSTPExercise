package at.mightyduck.lab07.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.Scanner;

/**
 *
 * @author reio
 */
public class Book implements Serializable {

    private String isbn;
    private Double preis;
    private String titel;
    private String autor;

    public Book() {
    }

    public Book(String isbn, Double preis, String titel, String autor) {
        this.isbn = isbn;
        this.preis = preis;
        this.titel = titel;
        this.autor = autor;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String author) {
        this.autor = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Double getPreis() {
        return preis;
    }

    public void setPreis(Double preis) {
        this.preis = preis;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.isbn);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Book other = (Book) obj;
        if (!Objects.equals(this.isbn, other.isbn)) {
            return false;
        }
        return true;
    }
    
    
}
