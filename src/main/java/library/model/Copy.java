
package library.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Copy {

    private String inventoryNumber;
    private CopyStatus status;
    private Book book;
    private List<Loan> loans;

    // Constructor
    public Copy(String inventoryNumber, Book book) {
        this.inventoryNumber = inventoryNumber;
        this.book = book;
        this.status = CopyStatus.AVAILABLE;
        this.loans = new ArrayList<>();

        if (book != null) {
            book.addCopy(this);
        }
    }

    // Getters
    public String getInventoryNumber() {
        return inventoryNumber;
    }

    public CopyStatus getStatus() {
        return status;
    }

    public Book getBook() {
        return book;
    }

    public List<Loan> getLoans() {
        return Collections.unmodifiableList(loans);
    }

    // Check if the copy is available
    public boolean isAvailable() {
        return status == CopyStatus.AVAILABLE;
    }

    // Get the current loan in progress
    public Loan getCurrentLoan() {
        for (Loan loan : loans) {
            if (loan.getReturnDate() == null) {
                return loan;
            }
        }

        return null;
    }

    // Mark the copy as borrowed
    void markBorrowed() {
        status = CopyStatus.BORROWED;
    }

    // Mark the copy as lost only if it is available
    public void markLost() {
        if (!isAvailable()) {
            throw new IllegalStateException(
                    "Only an available copy can be marked as lost"
            );
        }

        status = CopyStatus.LOST;
    }

    // Make the copy available again
    void makeAvailable() {
        if (status != CopyStatus.LOST) {
            status = CopyStatus.AVAILABLE;
        }
    }

    // Add a loan to the copy
    void addLoan(Loan loan) {
        if (loan != null && !loans.contains(loan)) {
            loans.add(loan);
        }
    }
}


