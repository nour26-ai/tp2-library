package library.model;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Author {

    private String name;
    private List<Book> books;

    public Author(String name) {
        this.name = name;
        this.books = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<Book> getBooks() {
        return Collections.unmodifiableList(books);
    }

    void addBook(Book book) {
        if (!books.contains(book)) {
            books.add(book);
        }
    }
}


