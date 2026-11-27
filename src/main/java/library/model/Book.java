
package library.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Book {

    private String isbn;
    private String title;
    private int year;

    private List<Author> authors;
    private List<Copy> copies;
    private List<Reservation> reservations;

    // Constructor
    public Book(String isbn, String title, int year, Author author) {
        this.isbn = isbn;
        this.title = title;
        this.year = year;

        this.authors = new ArrayList<>();
        this.copies = new ArrayList<>();
        this.reservations = new ArrayList<>();

        if (author != null) {
            addAuthor(author);
        }
    }

    // Getters
    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }

    public List<Author> getAuthors() {
        return Collections.unmodifiableList(authors);
    }

    public List<Copy> getCopies() {
        return Collections.unmodifiableList(copies);
    }

    public List<Reservation> getReservations() {
        return Collections.unmodifiableList(reservations);
    }

    // Add an author
    public void addAuthor(Author author) {
        if (author != null && !authors.contains(author)) {
            authors.add(author);
            author.addBook(this);
        }
    }

    // Add a copy
    void addCopy(Copy copy) {
        if (copy != null && !copies.contains(copy)) {
            copies.add(copy);
        }
    }

    // Add a reservation
    void addReservation(Reservation reservation) {
        if (reservation != null && !reservations.contains(reservation)) {
            reservations.add(reservation);
        }
    }

    // Return the first reservation
    public Reservation firstReservation() {
        if (reservations.isEmpty()) {
            return null;
        }

        return reservations.get(0);
    }
}

