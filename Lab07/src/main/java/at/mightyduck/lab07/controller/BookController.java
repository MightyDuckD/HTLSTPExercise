/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.mightyduck.lab07.controller;

import at.mightyduck.lab07.ISBNLib;
import at.mightyduck.lab07.model.Book;
import at.mightyduck.lab07.model.BookService;
import at.mightyduck.lab07.validator.IsbnChecksumValidator;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.bind.JAXBException;

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

    @PostConstruct
    public void init() {
        books = new ArrayList<>(BookService.getTestData());
        cleanBooks();
        newbook = new Book("", null, "", "");
    }

    private void cleanBooks() {
        try {
            ISBNLib.ISBNRangeMessage lib = ISBNLib.loadFromStream(BookController.class.getResourceAsStream("/ISBNRangeMessage.xml"));
            for (Book b : books) {
                b.setIsbn(ISBNLib.clean(lib, b.getIsbn()));
            }
        } catch (JAXBException ex) {
            Logger.getLogger(BookController.class.getName()).log(Level.SEVERE, null, ex);
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
        this.books.add(newbook);
        this.newbook = new Book("", null, "", "");
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
