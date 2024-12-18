package ie.atu.dip;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BankingAppFindAccountTest {
	private BankingApp bank;

	@BeforeEach
	void setUp() {
		bank = new BankingApp();
		bank.addAccount(TestUtils.MOCK_NAME, TestUtils.MOCK_AMOUNT);
	}

	@Test
	void testFindAccountWithExistingName() {
		Account account = bank.findAccount(TestUtils.MOCK_NAME);
		assertEquals("John Smith", account.getAccountHolder());
	}

	@Test
	void testFindAccountWithNonExistingName() {
		Account account = bank.findAccount("Jane Smith");
		assertNull(account);
	}

	@Test
	void testFindAccountWithNullName() {
		Account account = bank.findAccount(null);
		assertNull(account);
	}
}
