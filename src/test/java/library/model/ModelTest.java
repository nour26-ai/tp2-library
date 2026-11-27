
package library.model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ModelTest {

    private static final LocalDate DAY =
            LocalDate.of(2026, 10, 1);

    private Author author;
    private Book book;
    private Copy copy;
    private Member student;

    @BeforeEach
    void setUp() {

        author = new Author("First author");

        book = new Book(
                "978-0-00-000001-1",
                "Software Engineering",
                2016,
                author
        );

        copy = new Copy("C-001", book);

        student = new StudentMember(
                "M-01",
                "Student 1",
                "s1@example.org",
                "L3"
        );
    }

    @Test
    void bookAndAuthorKnowEachOther() {

        assertTrue(book.getAuthors().contains(author));
        assertTrue(author.getBooks().contains(book));
    }

    @Test
    void newCopyIsAvailableAndKnownByBook() {

        assertTrue(copy.isAvailable());
        assertEquals(CopyStatus.AVAILABLE, copy.getStatus());
        assertTrue(book.getCopies().contains(copy));
    }

    @Test
    void borrowingLinksLoanToMemberAndCopy() {

        Loan loan = student.borrow(copy, DAY);

        assertTrue(student.getLoans().contains(loan));
        assertTrue(copy.getLoans().contains(loan));
        assertFalse(copy.isAvailable());
        assertEquals(CopyStatus.BORROWED, copy.getStatus());
    }

    @Test
    void studentCannotBorrowSixthCopy() {

        for (int i = 1; i <= 5; i++) {

            Copy newCopy = new Copy("S-00" + i, book);
            student.borrow(newCopy, DAY);
        }

        Copy sixthCopy = new Copy("S-006", book);

        assertThrows(
                IllegalStateException.class,
                () -> student.borrow(sixthCopy, DAY)
        );
    }

    @Test
    void teacherCanBorrowTenCopies() {

        Member teacher = new TeacherMember(
                "T-01",
                "Teacher 1",
                "teacher@example.org",
                "Computer Science"
        );

        for (int i = 1; i <= 10; i++) {

            Copy newCopy = new Copy("T-00" + i, book);
            teacher.borrow(newCopy, DAY);
        }

        assertEquals(10, teacher.getLoans().size());
        assertFalse(teacher.canBorrow());
    }

    @Test
    void copyCannotBeBorrowedTwice() {

        student.borrow(copy, DAY);

        Member anotherStudent = new StudentMember(
                "M-02",
                "Student 2",
                "s2@example.org",
                "L3"
        );

        assertThrows(
                IllegalStateException.class,
                () -> anotherStudent.borrow(copy, DAY)
        );
    }

    @Test
    void closingLoanMakesCopyAvailableAndKeepsLoanHistory() {

        Loan loan = student.borrow(copy, DAY);

        loan.close(DAY.plusDays(5));

        assertTrue(copy.isAvailable());
        assertEquals(CopyStatus.AVAILABLE, copy.getStatus());
        assertTrue(student.getLoans().contains(loan));
        assertTrue(copy.getLoans().contains(loan));
        assertEquals(DAY.plusDays(5), loan.getReturnDate());
    }

    @Test
    void loanIsLateAfterDueDate() {

        Loan loan = student.borrow(copy, DAY);

        assertEquals(DAY.plusDays(14), loan.getDueDate());
        assertFalse(loan.isLate(DAY.plusDays(14)));
        assertTrue(loan.isLate(DAY.plusDays(15)));
    }

    @Test
    void collectionsReturnedByGettersCannotBeModified() {

        assertThrows(
                UnsupportedOperationException.class,
                () -> book.getAuthors().clear()
        );

        assertThrows(
                UnsupportedOperationException.class,
                () -> book.getCopies().clear()
        );

        assertThrows(
                UnsupportedOperationException.class,
                () -> author.getBooks().clear()
        );

        assertThrows(
                UnsupportedOperationException.class,
                () -> student.getLoans().clear()
        );

        assertThrows(
                UnsupportedOperationException.class,
                () -> copy.getLoans().clear()
        );
    }

    @Test
    void reservationsKeepTheirOrder() {

        Reservation first = student.reserve(book, DAY);

        Reservation second = student.reserve(
                book,
                DAY.plusDays(1)
        );

        assertTrue(student.getReservations().contains(first));
        assertTrue(student.getReservations().contains(second));
        assertTrue(book.getReservations().contains(first));
        assertTrue(book.getReservations().contains(second));

        assertSame(first, book.firstReservation());
    }

    @Test
    void onlyAvailableCopyCanBeMarkedLost() {

        student.borrow(copy, DAY);

        assertThrows(
                IllegalStateException.class,
                () -> copy.markLost()
        );

        Copy availableCopy = new Copy("C-002", book);

        availableCopy.markLost();

        assertEquals(CopyStatus.LOST, availableCopy.getStatus());
        assertFalse(availableCopy.isAvailable());
    }

    // PART E - Checking with an Object Diagram

    @Test
    void objectDiagramSituationHasAllCorrectLinks() {

        // Create two authors
        Author author1 = new Author("First author");
        Author author2 = new Author("Second author");

        // Create a book with two authors
        Book book = new Book(
                "978-0-00-000001-1",
                "Software Engineering",
                2016,
                author1
        );

        book.addAuthor(author2);

        // Create two copies
        Copy c1 = new Copy("C-001", book);
        Copy c2 = new Copy("C-002", book);

        // Create a student
        Member student = new StudentMember(
                "M-01",
                "Student 1",
                "s1@example.org",
                "L3"
        );

        // Create a teacher
        Member teacher = new TeacherMember(
                "T-01",
                "Teacher 1",
                "teacher@example.org",
                "Computer Science"
        );

        // Student borrows both copies on 1 October 2026
        LocalDate borrowDate = LocalDate.of(2026, 10, 1);

        Loan loan1 = student.borrow(c1, borrowDate);
        Loan loan2 = student.borrow(c2, borrowDate);

        // Teacher reserves the book on 2 October 2026
        LocalDate reservationDate = borrowDate.plusDays(1);

        Reservation reservation =
                teacher.reserve(book, reservationDate);

        // Check authors and book links
        assertTrue(book.getAuthors().contains(author1));
        assertTrue(book.getAuthors().contains(author2));

        assertTrue(author1.getBooks().contains(book));
        assertTrue(author2.getBooks().contains(book));

        // Check book and copies links
        assertTrue(book.getCopies().contains(c1));
        assertTrue(book.getCopies().contains(c2));

        // Check student and loans links
        assertTrue(student.getLoans().contains(loan1));
        assertTrue(student.getLoans().contains(loan2));

        assertTrue(c1.getLoans().contains(loan1));
        assertTrue(c2.getLoans().contains(loan2));

        // Check teacher and reservation links
        assertTrue(teacher.getReservations().contains(reservation));
        assertTrue(book.getReservations().contains(reservation));

     // Check dates
        assertEquals(borrowDate, loan1.getLoanDate());
        assertEquals(borrowDate, loan2.getLoanDate());

        assertEquals(reservationDate, reservation.getDate());
       
    }

}
