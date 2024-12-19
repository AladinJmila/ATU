package ie.atu.dip;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class BankingAppGetLoanTest {
	private BankingApp bank;
	
	@BeforeAll
    static void getLoanTestsStarted() {
        System.out.println("Get loan tests started.");
    }

	@BeforeEach
	void setUp() {
		bank = new BankingApp();
		bank.addAccount(TestUtils.MOCK_NAME, TestUtils.MOCK_AMOUNT);
		bank.approveLoan(TestUtils.MOCK_NAME, 500.0);
	}

	@Test
	void testGetLoanWithValidExistingName() {
		assertEquals(TestUtils.MOCK_AMOUNT, bank.getLoan(TestUtils.MOCK_NAME), 500.0);
	}

	@Test
	void testGetLoanWithValidNonExistingName() {
		assertNull(bank.getLoan("Jane Smith"));
	}

	@Test
	void testGetLoanWithNullName() {
		Exception exception = assertThrows(IllegalArgumentException.class, () -> bank.getLoan(null));
		assertEquals(TestUtils.INVALID_NAME_ERROR_MESSAGE, exception.getMessage());
	}

	@Test
	void testGetLoanWithWhitespaceName() {
		Exception exception = assertThrows(IllegalArgumentException.class, () -> bank.getLoan("  "));
		assertEquals(TestUtils.INVALID_NAME_ERROR_MESSAGE, exception.getMessage());
	}
	
	@AfterEach
	void reset() {
		bank = null;
	}
	
	@AfterAll
    static void getLoanTestsEnded() {
        System.out.println("Get loan tests ended.");
    }
}
