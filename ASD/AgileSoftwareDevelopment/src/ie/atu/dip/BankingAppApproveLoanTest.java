package ie.atu.dip;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BankingAppApproveLoanTest {
	private BankingApp bank;

	@BeforeEach
	void setUp() {
		bank = new BankingApp();
		bank.addAccount(TestUtils.MOCK_NAME, TestUtils.MOCK_AMOUNT);
	}

	@Test
	void testApproveLoanWithExistingName() {
		assertTrue(bank.approveLoan(TestUtils.MOCK_NAME, 500.0));
		TestUtils.assertAccountDetails(bank, TestUtils.MOCK_NAME, TestUtils.MOCK_AMOUNT, 1,
				(TestUtils.MOCK_AMOUNT - 500.00));
	}

	@Test
	void testApproveLoanWithNonExistingName() {
		assertFalse(bank.approveLoan("Jane Smith", 500.0));
	}

	@Test
	void testApproveLoanWithNullName() {
		Exception exception = assertThrows(IllegalArgumentException.class,
				() -> bank.approveLoan(null, TestUtils.MOCK_AMOUNT));
		assertEquals(TestUtils.INVALID_NAME_ERROR_MESSAGE, exception.getMessage());
	}

	@Test
	void testApproveLoanWithWhitespaceName() {
		Exception exception = assertThrows(IllegalArgumentException.class,
				() -> bank.approveLoan("  ", TestUtils.MOCK_AMOUNT));
		assertEquals(TestUtils.INVALID_NAME_ERROR_MESSAGE, exception.getMessage());
	}

	@Test
	void testApproveLoanWithDepositGreaterThanZeroAndLessThanTotalDeposits() {
		assertTrue(bank.approveLoan(TestUtils.MOCK_NAME, 500.0));
		assertEquals(500.0, bank.getAccounts().get(0).getLoan(), 0.1);
		TestUtils.assertAccountDetails(bank, TestUtils.MOCK_NAME, TestUtils.MOCK_AMOUNT, 1, (TestUtils.MOCK_AMOUNT - 500.00));
	}

	@Test
	void testApproveLoanWithDepositGreaterThanZeroAndGreaterThanTotalDeposits() {
		assertFalse(bank.approveLoan(TestUtils.MOCK_NAME, (TestUtils.MOCK_AMOUNT + 500.00)));
	}

	@Test
	void testApproveLoanWithDepositEqualsZero() {
		assertTrue(bank.approveLoan(TestUtils.MOCK_NAME, 0.0));
		TestUtils.assertAccountDetails(bank, TestUtils.MOCK_NAME, TestUtils.MOCK_AMOUNT, 1, TestUtils.MOCK_AMOUNT);
	}

	@Test
	void testApproveLoanWithDepositLessThanZero() {
		Exception exception = assertThrows(IllegalArgumentException.class,
				() -> bank.approveLoan(TestUtils.MOCK_NAME, -500.0));
		assertEquals(TestUtils.INVALID_DEPOSIT_ERROR_MESSAGE, exception.getMessage());
	}
}
