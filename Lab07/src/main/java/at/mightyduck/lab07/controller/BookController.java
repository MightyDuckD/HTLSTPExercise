/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.mightyduck.lab07.controller;

import at.mightyduck.isbn.ISBN;
import at.mightyduck.isbn.ISBNLib;
import at.mightyduck.lab07.model.Book;
import at.mightyduck.lab07.model.BookService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 *
 * @author Simon
 */
@Named("bookcon")
@SessionScoped
public class BookController implements Serializable {

    private List<Book> books;
    private String selectedAutor;
    private Book newbook;
    private String isbn;
    
    @PostConstruct
    public void init() {
        books = new ArrayList<>(BookService.getTestData());
        cleanBooks();
        newbook = new Book("", null, "", "");
    }

    private void cleanBooks() {
        ISBNLib lib = ISBNLib.getInstance();
        for (Book b : books) {
            b.setIsbn(lib.clean(b.getIsbn()).toString());
        }
    }

    public Collection<String> getAutoren() {
        Set<String> result = new TreeSet<>();
        for (Book b : books) {
            result.add(b.getAutor());
        }
        return result;
    }

    public void setSelectedAutor(String selectedAutor) {
        this.selectedAutor = selectedAutor;
    }

    public String getSelectedAutor() {
        return selectedAutor;
    }

    public List<Book> getBooks() {
        return filterByAutor(books, selectedAutor);
    }

    public void createBook() {
        String isbnOld = newbook.getIsbn();
        this.newbook.setIsbn(ISBNLib.getInstance().clean(isbnOld).toString());
        if(this.books.contains(newbook)) {
            FacesMessage message = new FacesMessage("Ein Buch mit dieser ISBN existiert bereits");
            FacesContext.getCurrentInstance().addMessage("addform:isbn", message);
            this.newbook.setIsbn(isbnOld);
        } else {   
            this.books.add(newbook);
            this.newbook = new Book("", null, "", "");
        }
    }

    public ISBN getIsbnDetails() {
        return (isbn == null || isbn.isEmpty()) ? null : ISBNLib.getInstance().clean(isbn);
    }
    
    public void viewIsbnDetails(String isbn) {
        System.out.println("set details " + isbn);
        this.isbn = isbn;
    }

    public Book getNewbook() {
        return newbook;
    }

    public void setNewbook(Book newbook) {
        this.newbook = newbook;
    }

    /**
     *
     * @param books The books to be filtered
     * @param autor
     * @return A new ArrayList with all books from the specified autor or books
     * itself if autor is null.
     */
    private List<Book> filterByAutor(List<Book> books, String autor) {
        if (autor == null) {
            return books;
        }
        List<Book> result = new ArrayList<>();
        for (Book b : books) {
            if (b.getAutor().equals(autor)) {
                result.add(b);
            }
        }
        return result;
    }
}
