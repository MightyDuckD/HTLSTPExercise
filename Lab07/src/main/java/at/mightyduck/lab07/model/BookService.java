package at.mightyduck.lab07.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author reio
 */
public class BookService {

    private static final List<Book> BOOKS;

    static {
        BOOKS = new ArrayList<>();
        BOOKS.add(new Book("9781590599259",
                39.99,
                "XNA 2.0 Game Programming",
                "Riemer, Grootjans"));
        BOOKS.add(new Book("9783897215221",
                9.90,
                "SQL kurz & gut",
                "Gennick, Jonathan"));
        BOOKS.add(new Book("978-1565927568",
                54.34,
                "Transact SQL Programming",
                "Gennick, Jonathan"));
        BOOKS.add(new Book("9783898428866",
                49.90,
                "Cinema 4D 10",
                "Asanger, Andreas"));
        BOOKS.add(new Book("9780131347960",
                35.75,
                "JBoss Seam",
                "Juanto Yuan, Michael"));
        BOOKS.add(new Book("9783827321999",
                49.95,
                "Entwurfsmuster",
                "Gamma, Erich"));
        BOOKS.add(new Book("9783540560067",
                54.99,
                "Objektorientierte Software Entwicklung",
                "Gamma, Erich"));

    }

    public static List<Book> getTestData() {
        return BOOKS;
    }

}
