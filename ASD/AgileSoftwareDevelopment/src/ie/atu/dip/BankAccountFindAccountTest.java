package ie.atu.dip;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BankAccountFindAccountTest {
	private BankingApp bank;
	private String mockName = "John Smith";
	private double mockAmount = 1000.0d;

	@BeforeEach
	void setUp() {
		bank = new BankingApp();
		bank.addAccount(mockName, mockAmount);
	}

	@Test
	void testFindAccountWithExistingName() {
		Account account = bank.findAccount("John Smith");
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
