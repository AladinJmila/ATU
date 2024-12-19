package ie.atu.dip;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BankingAppGetBalanceTest {
	private BankingApp bank;
	
	@BeforeAll
    static void getBalanceTestsStarted() {
        System.out.println("Get balance tests started.");
    }

	@BeforeEach
	void setUp() {
		bank = new BankingApp();
		bank.addAccount(TestUtils.MOCK_NAME, TestUtils.MOCK_AMOUNT);
	}

	@Test
	void testGetBalanceWithValidExistingName() {
		assertEquals(TestUtils.MOCK_AMOUNT, bank.getBalance(TestUtils.MOCK_NAME), 0.1);
	}

	@Test
	void testGetBalanceWithValidNonExistingName() {
		assertNull(bank.getBalance("Jane Smith"));
	}

	@Test
	void testGetBalanceWithNullName() {
		Exception exception = assertThrows(IllegalArgumentException.class, () -> bank.getBalance(null));
		assertEquals(TestUtils.INVALID_NAME_ERROR_MESSAGE, exception.getMessage());
	}

	@Test
	void testGetBalanceWithWhitespaceName() {
		Exception exception = assertThrows(IllegalArgumentException.class, () -> bank.getBalance("  "));
		assertEquals(TestUtils.INVALID_NAME_ERROR_MESSAGE, exception.getMessage());
	}
	
	@AfterEach
	void reset() {
		bank = null;
	}
	
	@AfterAll
    static void getBalanceTestsEnded() {
        System.out.println("Get balance tests ended.");
    }

}
