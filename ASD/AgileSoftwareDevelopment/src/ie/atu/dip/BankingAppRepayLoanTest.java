package ie.atu.dip;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class BankingAppRepayLoanTest {
	private BankingApp bank;

	@BeforeEach
	void setUp() {
		bank = new BankingApp();
		bank.addAccount(TestUtils.MOCK_NAME, TestUtils.MOCK_AMOUNT);
		bank.approveLoan(TestUtils.MOCK_NAME, 500.0);
	}

	@Test
	void testRepayLoanWithExistingName() {
		assertTrue(bank.repayLoan(TestUtils.MOCK_NAME, 500.0));
		TestUtils.assertAccountDetails(bank, TestUtils.MOCK_NAME, TestUtils.MOCK_AMOUNT, 1, (TestUtils.MOCK_AMOUNT));
	}

	@Test
	void testRepayLoanWithNonExistingName() {
		assertFalse(bank.repayLoan("Jane Smith", 500.0));
	}

	@Test
	void testRepayLoanWithNullName() {
		Exception exception = assertThrows(IllegalArgumentException.class,
				() -> bank.repayLoan(null, TestUtils.MOCK_AMOUNT));
		assertEquals(TestUtils.INVALID_NAME_ERROR_MESSAGE, exception.getMessage());
	}

	@Test
	void testRepayLoanWithWhitespaceName() {
		Exception exception = assertThrows(IllegalArgumentException.class,
				() -> bank.repayLoan("  ", TestUtils.MOCK_AMOUNT));
		assertEquals(TestUtils.INVALID_NAME_ERROR_MESSAGE, exception.getMessage());
	}

	@Test
	void testRepayLoanWithDepositGreaterThanZeroAndLessThanLoan() {
		assertTrue(bank.repayLoan(TestUtils.MOCK_NAME, 250.0));
		assertEquals(250.0, bank.getAccounts().get(0).getLoan(), 0.1);
		TestUtils.assertAccountDetails(bank, TestUtils.MOCK_NAME, TestUtils.MOCK_AMOUNT, 1,
				(TestUtils.MOCK_AMOUNT - 250.0));
	}

	@Test
	void testRepayLoanWithDepositGreaterThanZeroAndGreaterThanTotalDeposits() {
		assertFalse(bank.repayLoan(TestUtils.MOCK_NAME, 1000.0));
		TestUtils.assertAccountDetails(bank, TestUtils.MOCK_NAME, TestUtils.MOCK_AMOUNT, 1,
				(TestUtils.MOCK_AMOUNT - 500.0));
	}

	@Test
	void testRepayLoanWithDepositEqualsZero() {
		assertFalse(bank.repayLoan(TestUtils.MOCK_NAME, 0.0));
		TestUtils.assertAccountDetails(bank, TestUtils.MOCK_NAME, TestUtils.MOCK_AMOUNT, 1,
				(TestUtils.MOCK_AMOUNT - 500.0));
	}

	@Test
	void testRepayLoanWithDepositLessThanZero() {
		Exception exception = assertThrows(IllegalArgumentException.class,
				() -> bank.repayLoan(TestUtils.MOCK_NAME, -500.0));
		assertEquals(TestUtils.INVALID_DEPOSIT_ERROR_MESSAGE, exception.getMessage());
	}

	@AfterEach
	void reset() {
		bank = null;
	}
}
