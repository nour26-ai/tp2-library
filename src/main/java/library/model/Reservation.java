

package library.model;

import java.time.LocalDate;

public class Reservation {

    private LocalDate date;

    // Constructor
    Reservation(LocalDate date) {
        this.date = date;
    }

    // Getter
    public LocalDate getDate() {
        return date;
    }

}
