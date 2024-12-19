package ie.atu.dip;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

public class BankingAppAddAccountTest {
	private BankingApp bank;
	
	@BeforeAll
    static void addAccountTestsStarted() {
        System.out.println("Add account tests started.");
    }

	@BeforeEach
	void setUp() {
		bank = new BankingApp();
	}

	@Test
	void testAddAccountWithValidName() {
		bank.addAccount(TestUtils.MOCK_NAME, TestUtils.MOCK_AMOUNT);
		TestUtils.assertAccountDetails(bank, TestUtils.MOCK_NAME, TestUtils.MOCK_AMOUNT, 1, TestUtils.MOCK_AMOUNT);
	}

	@Test
	void testAddAccountWithNullName() {
		Exception exception = assertThrows(IllegalArgumentException.class,
				() -> bank.addAccount(null, TestUtils.MOCK_AMOUNT));
		assertEquals(TestUtils.INVALID_NAME_ERROR_MESSAGE, exception.getMessage());
	}

	@Test
	void testAddAccountWithWhitespaceName() {
		Exception exception = assertThrows(IllegalArgumentException.class,
				() -> bank.addAccount("  ", TestUtils.MOCK_AMOUNT));
		assertEquals(TestUtils.INVALID_NAME_ERROR_MESSAGE, exception.getMessage());
	}

	@Test
	void testAddAccountWithValidDeposit() {
		bank.addAccount(TestUtils.MOCK_NAME, TestUtils.MOCK_AMOUNT);
		TestUtils.assertAccountDetails(bank, TestUtils.MOCK_NAME, TestUtils.MOCK_AMOUNT, 1, TestUtils.MOCK_AMOUNT);
	}

	@Test
	void testAddAccountWithNegativeDeposit() {
		Exception exception = assertThrows(IllegalArgumentException.class,
				() -> bank.addAccount(TestUtils.MOCK_NAME, -1000.0));
		assertEquals(TestUtils.INVALID_DEPOSIT_ERROR_MESSAGE, exception.getMessage());
	}
	
	@AfterAll
    static void addAccountTestsEnded() {
        System.out.println("Add account tests ended.");
    }
}
