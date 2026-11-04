package library;



	import static org.junit.jupiter.api.Assertions.*;

	import java.time.LocalDate;

	import org.junit.jupiter.api.BeforeEach;
	import org.junit.jupiter.api.Test;

	public class LibraryTest {

	    private Library library;
	    private Member member;
	    private Copy copy1;
	    private Copy copy2;
	    private Copy copy3;
	    private Copy copy4;
	    private Copy copy5;
	    private Copy copy6;

	    private LocalDate today = LocalDate.of(2026, 11, 4);

	    @BeforeEach
	    public void setUp() {

	        library = new Library();

	        member = new Member("M001", "Ali");
	        library.registerMember(member);

	        Book book = new Book("12345", "Java Programming", 2024);
	        library.registerBook(book);

	        copy1 = new Copy("C001", book);
	        copy2 = new Copy("C002", book);
	        copy3 = new Copy("C003", book);
	        copy4 = new Copy("C004", book);
	        copy5 = new Copy("C005", book);
	        copy6 = new Copy("C006", book);

	        library.registerCopy(copy1);
	        library.registerCopy(copy2);
	        library.registerCopy(copy3);
	        library.registerCopy(copy4);
	        library.registerCopy(copy5);
	        library.registerCopy(copy6);
	    }

	    @Test
	    public void testLendCopy() {

	        Loan loan = library.lend("M001", "C001", today);

	        assertNotNull(loan);
	        assertFalse(copy1.isAvailable());
	        assertTrue(loan.isOpen());
	        assertEquals(today.plusDays(14), loan.getDueDate());
	    }

	    @Test
	    public void testCannotLendUnavailableCopy() {

	        library.lend("M001", "C001", today);

	        assertThrows(IllegalStateException.class, () -> {
	            library.lend("M001", "C001", today);
	        });
	    }

	    @Test
	    public void testMaximumFiveLoans() {

	        library.lend("M001", "C001", today);
	        library.lend("M001", "C002", today);
	        library.lend("M001", "C003", today);
	        library.lend("M001", "C004", today);
	        library.lend("M001", "C005", today);

	        assertThrows(IllegalStateException.class, () -> {
	            library.lend("M001", "C006", today);
	        });
	    }

	    @Test
	    public void testUnknownMember() {

	        assertThrows(IllegalArgumentException.class, () -> {
	            library.lend("M999", "C001", today);
	        });
	    }

	    @Test
	    public void testUnknownCopy() {

	        assertThrows(IllegalArgumentException.class, () -> {
	            library.lend("M001", "C999", today);
	        });
	    }

	    @Test
	    public void testReturnCopy() {

	        Loan loan = library.lend("M001", "C001", today);

	        library.returnCopy("C001", today.plusDays(5));

	        assertTrue(copy1.isAvailable());
	        assertFalse(loan.isOpen());
	        assertEquals(today.plusDays(5), loan.getReturnDate());
	    }

	    @Test
	    public void testReturnCopyWithoutLoan() {

	        assertThrows(IllegalStateException.class, () -> {
	            library.returnCopy("C001", today);
	        });
	    }

	    @Test
	    public void testLoanIsLate() {

	        Loan loan = new Loan(member, copy1, today);

	        assertTrue(loan.isLate(today.plusDays(15)));
	        assertFalse(loan.isLate(today.plusDays(10)));
	    }
	}

