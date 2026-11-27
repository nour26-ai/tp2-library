

package library.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Member {

    private String memberNumber;
    private String name;
    private String email;

    private List<Loan> loans;
    private List<Reservation> reservations;

    // Constructor
    protected Member(String memberNumber, String name, String email) {
        this.memberNumber = memberNumber;
        this.name = name;
        this.email = email;

        this.loans = new ArrayList<>();
        this.reservations = new ArrayList<>();
    }

    // Getters
    public String getMemberNumber() {
        return memberNumber;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public List<Loan> getLoans() {
        return Collections.unmodifiableList(loans);
    }

    public List<Reservation> getReservations() {
        return Collections.unmodifiableList(reservations);
    }

    // Maximum number of loans
    public abstract int maxLoans();

    // Check if member can borrow
    public boolean canBorrow() {
        int activeLoans = 0;

        for (Loan loan : loans) {
            if (loan.getReturnDate() == null) {
                activeLoans++;
            }
        }

        return activeLoans < maxLoans();
    }

    // Borrow a copy
    public Loan borrow(Copy copy, LocalDate today) {

        if (!canBorrow()) {
            throw new IllegalStateException("Maximum loans reached");
        }

        if (copy == null || !copy.isAvailable()) {
            throw new IllegalStateException("Copy is not available");
        }

        Loan loan = new Loan(copy, today);

        loans.add(loan);
        copy.addLoan(loan);
        copy.markBorrowed();

        return loan;
    }

    // Reserve a book
    public Reservation reserve(Book book, LocalDate date) {

        if (book == null || date == null) {
            throw new IllegalArgumentException("Book and date are required");
        }

        Reservation reservation = new Reservation(date);

        reservations.add(reservation);
        book.addReservation(reservation);

        return reservation;
    }
}

