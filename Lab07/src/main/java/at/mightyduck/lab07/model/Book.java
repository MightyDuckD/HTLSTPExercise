package at.mightyduck.lab07.model;

import java.io.Serializable;
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

    public String getCleanIsbn() {
        String clean = isbn.replace("-", "");
        return String.format("%3s-%1s-%4s-%4s-%1s",
                clean.substring(0, 3),
                clean.substring(3, 4),
                clean.substring(4, 8),
                clean.substring(8, 12),
                clean.substring(12, 13)
        );
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
}
