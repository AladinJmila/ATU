package ie.atu.dip;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BankingAppWithdrawTest {
	private BankingApp bank;
	
	@BeforeAll
    static void withdrawTestsStarted() {
        System.out.println("Withdraw an amount tests started.");
    }

	@BeforeEach
	void setUp() {
		bank = new BankingApp();
		bank.addAccount(TestUtils.MOCK_NAME, TestUtils.MOCK_AMOUNT);
	}

	@Test
	void testWithdrawWithExistingName() {
		assertTrue(bank.withdraw(TestUtils.MOCK_NAME, 500.0));
		TestUtils.assertAccountDetails(bank, TestUtils.MOCK_NAME, (TestUtils.MOCK_AMOUNT - 500.00), 1,
				(TestUtils.MOCK_AMOUNT - 500.00));
	}

	@Test
	void testWithdrawWithNonExistingName() {
		assertFalse(bank.withdraw("Jane Smith", 500.0));
	}

	@Test
	void testWithdrawWithNullName() {
		Exception exception = assertThrows(IllegalArgumentException.class,
				() -> bank.withdraw(null, TestUtils.MOCK_AMOUNT));
		assertEquals(TestUtils.INVALID_NAME_ERROR_MESSAGE, exception.getMessage());
	}

	@Test
	void testWithdrawWithWhitespaceName() {
		Exception exception = assertThrows(IllegalArgumentException.class,
				() -> bank.withdraw("  ", TestUtils.MOCK_AMOUNT));
		assertEquals(TestUtils.INVALID_NAME_ERROR_MESSAGE, exception.getMessage());
	}

	@Test
	void testWithdrawWithAmountGreaterThanZeroAndLessThanBalance() {
		assertTrue(bank.withdraw(TestUtils.MOCK_NAME, 500.0));
		TestUtils.assertAccountDetails(bank, "John Smith", (TestUtils.MOCK_AMOUNT - 500.00), 1,
				(TestUtils.MOCK_AMOUNT - 500.00));
	}

	@Test
	void testWithdrawWithAmountGreaterThanZeroAndEqualsToBalance() {
		assertTrue(bank.withdraw(TestUtils.MOCK_NAME, TestUtils.MOCK_AMOUNT));
		TestUtils.assertAccountDetails(bank, "John Smith", 0, 1, 0);
	}

	@Test
	void testWithdrawWithAmountGreaterZeroAndBalance() {
		assertFalse(bank.withdraw(TestUtils.MOCK_NAME, (TestUtils.MOCK_AMOUNT + 500.00)));
	}

	@Test
	void testWithdrawWithAmountEqualsZero() {
		assertFalse(bank.withdraw(TestUtils.MOCK_NAME, 0.0));
		TestUtils.assertAccountDetails(bank, "John Smith", TestUtils.MOCK_AMOUNT, 1, TestUtils.MOCK_AMOUNT);
	}

	@Test
	void testWithdrawWithAmountLessThanZero() {
		Exception exception = assertThrows(IllegalArgumentException.class,
				() -> bank.withdraw(TestUtils.MOCK_NAME, -500.0));
		assertEquals(TestUtils.INVALID_DEPOSIT_ERROR_MESSAGE, exception.getMessage());
	}
	
	@AfterEach
	void reset() {
		bank = null;
	}
	
	@AfterAll
	static void withdrawTestsEnded() {
        System.out.println("Withdraw an amount tests ended.");
    }
	

}
