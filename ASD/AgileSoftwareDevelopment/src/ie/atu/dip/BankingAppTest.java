package ie.atu.dip;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BankingAppTest {
	private BankingApp bank;
	private String mockName = "John Smith";
	private double mockAmount = 1000.0d;
	private String invalidNameErrorMessage = "Account holder name cannot be null or empty";
	private String invalidDepositErrorMessage = "Deposit cannot be negative";

	@BeforeEach
	void setUp() {
		bank = new BankingApp();
	}

	@Test
	void testFindAccountWithExistingName() {
		bank.addAccount(mockName, mockAmount);
		Account account = bank.findAccount("John Smith");
		assertEquals("John Smith", account.getAccountHolder());
	}
	
	@Test
	void testFindAccountWithNonExistingName() {
		bank.addAccount(mockName, mockAmount);
		Account account = bank.findAccount("Jane Smith");
		assertNull(account);
	}
	
	@Test
	void testAddAccountWithValidName() {
		bank.addAccount(mockName, mockAmount);
		assertAccountDetails("John Smith", mockAmount, 1, mockAmount);
		
	}

	@Test
	void testAddAccountWithNullName() {
		Exception exception = assertThrows(IllegalArgumentException.class, () -> bank.addAccount(null, mockAmount));
		assertEquals(invalidNameErrorMessage, exception.getMessage());
	}

	@Test
	void testAddAccountWithWhitespaceName() {
		Exception exception = assertThrows(IllegalArgumentException.class, () -> bank.addAccount("  ", mockAmount));
		assertEquals(invalidNameErrorMessage, exception.getMessage());
	}

	@Test
	void testAddAccountWithValidDeposit() {
		bank.addAccount(mockName, mockAmount);
		assertAccountDetails("John Smith", mockAmount, 1, mockAmount);
	}

	@Test
	void testAddAccountWithNegativeDeposit() {
		Exception exception = assertThrows(IllegalArgumentException.class, () -> bank.addAccount(mockName, -1000.0));
		assertEquals(invalidDepositErrorMessage, exception.getMessage());
	}

	private void assertAccountDetails(String expectedName, double expectedBalance, int expectedSize,
			double expectedTotalDeposit) {
		assertEquals(expectedSize, bank.getAccounts().size());
		assertEquals(expectedName, bank.getAccounts().get(0).getAccountHolder());
		assertEquals(expectedBalance, bank.getAccounts().get(0).getBalance(), 0.1);
		assertEquals(expectedTotalDeposit, bank.getTotalDeposits(), 0.1);
	}
}
