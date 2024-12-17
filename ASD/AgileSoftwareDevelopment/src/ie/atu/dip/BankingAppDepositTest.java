package ie.atu.dip;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BankingAppDepositTest {
	private BankingApp bank;

	@BeforeEach
	void setUp() {
		bank = new BankingApp();
		bank.addAccount(TestUtils.MOCK_NAME, TestUtils.MOCK_AMOUNT);
	}

	@Test
	void testDepositWithExistingName() {
		assertTrue(bank.deposit(TestUtils.MOCK_NAME, 500.0));
		TestUtils.assertAccountDetails(bank, TestUtils.MOCK_NAME, (TestUtils.MOCK_AMOUNT + 500.00), 1,
				(TestUtils.MOCK_AMOUNT + 500.00));
	}

	@Test
	void testDepositWithNonExistingName() {
		assertFalse(bank.deposit("Jane Smith", 500.0));
	}

	@Test
	void testDepositWithNullName() {
		Exception exception = assertThrows(IllegalArgumentException.class,
				() -> bank.deposit(null, TestUtils.MOCK_AMOUNT));
		assertEquals(TestUtils.INVALID_NAME_ERROR_MESSAGE, exception.getMessage());
	}

	@Test
	void testDepositWithWhitespaceName() {
		Exception exception = assertThrows(IllegalArgumentException.class,
				() -> bank.deposit("  ", TestUtils.MOCK_AMOUNT));
		assertEquals(TestUtils.INVALID_NAME_ERROR_MESSAGE, exception.getMessage());
	}

	@Test
	void testDepositWithDepositGreaterThanZero() {
		assertTrue(bank.deposit(TestUtils.MOCK_NAME, 500.0));
		TestUtils.assertAccountDetails(bank, "John Smith", (TestUtils.MOCK_AMOUNT + 500.00), 1,
				(TestUtils.MOCK_AMOUNT + 500.00));
	}

	@Test
	void testDepositWithDepositEqualsZero() {
		assertTrue(bank.deposit(TestUtils.MOCK_NAME, 0.0));
		TestUtils.assertAccountDetails(bank, "John Smith", TestUtils.MOCK_AMOUNT, 1, TestUtils.MOCK_AMOUNT);
	}

	@Test
	void testDepositWithDepositLessThanZero() {
		Exception exception = assertThrows(IllegalArgumentException.class,
				() -> bank.deposit(TestUtils.MOCK_NAME, -500.0));
		assertEquals(TestUtils.INVALID_DEPOSIT_ERROR_MESSAGE, exception.getMessage());
	}
}
