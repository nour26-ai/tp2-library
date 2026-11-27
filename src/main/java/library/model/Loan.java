
package library.model;

import java.time.LocalDate;

public class Loan {

    private LocalDate loanDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private Copy copy;

    // Constructor
    Loan(Copy copy, LocalDate loanDate) {
        this.copy = copy;
        this.loanDate = loanDate;
        this.dueDate = loanDate.plusDays(14);
        this.returnDate = null;
    }

    // Getters
    public LocalDate getLoanDate() {
        return loanDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public Copy getCopy() {
        return copy;
    }

    // Close a loan
    public void close(LocalDate today) {
        if (returnDate != null) {
            throw new IllegalStateException("Loan is already closed");
        }

        returnDate = today;
        copy.makeAvailable();
    }

    // Check if loan is late
    public boolean isLate(LocalDate today) {
        return returnDate == null && today.isAfter(dueDate);
    }
}

