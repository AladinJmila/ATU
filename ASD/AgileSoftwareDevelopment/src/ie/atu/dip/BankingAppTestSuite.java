package ie.atu.dip;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BankingAppTestSuite {
	private BankingApp bank;
	private String mockName = "John Smith";
	private double mockAmount = 1000.0d;
	private String invalidNameErrorMessage = "Account holder name cannot be null or empty";
	private String invalidDepositErrorMessage = "Deposit cannot be negative";

	@BeforeEach
	void setUp() {
		bank = new BankingApp();
	}

	// findAccount tests
	@Test
	void testFindAccountWithExistingName() {
		bank.addAccount(mockName, mockAmount);
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
	
	// addAccount tests
	@Test
	void testAddAccountWithValidName() {
		bank.addAccount(mockName, mockAmount);
		TestUtils.assertAccountDetails(bank, "John Smith", mockAmount, 1, mockAmount);
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
		TestUtils.assertAccountDetails(bank, "John Smith", mockAmount, 1, mockAmount);
	}

	@Test
	void testAddAccountWithNegativeDeposit() {
		Exception exception = assertThrows(IllegalArgumentException.class, () -> bank.addAccount(mockName, -1000.0));
		assertEquals(invalidDepositErrorMessage, exception.getMessage());
	}
	
	// deposit tests
	@Test 
	void testDepositWithExistingName() {
		bank.addAccount(mockName, mockAmount);
		assertTrue(bank.deposit("John Smith", 500.0));
		TestUtils.assertAccountDetails(bank, "John Smith", 1500.0, 1, 1500.0);
	}
	
	@Test 
	void testDepositWithNonExistingName() {
		assertFalse(bank.deposit("Jane Smith", 500.0));
	}
	
	@Test 
	void testDepositWithNullName() {
		Exception exception = assertThrows(IllegalArgumentException.class, () -> bank.deposit(null, mockAmount));
		assertEquals(invalidNameErrorMessage, exception.getMessage());
	}
	
	@Test 
	void testDepositWithWhitespaceName() {
		Exception exception = assertThrows(IllegalArgumentException.class, () -> bank.deposit("  ", mockAmount));
		assertEquals(invalidNameErrorMessage, exception.getMessage());
	}
	
	@Test 
	void testDepositWithDepositGreaterThanZero() {
		bank.addAccount(mockName, mockAmount);
		assertTrue(bank.deposit("John Smith", 500.0));
		TestUtils.assertAccountDetails(bank, "John Smith", 1500.0, 1, 1500.0);
	}
	
	@Test
	void testDepositWithDepositEqualsZero() {
		bank.addAccount(mockName, mockAmount);
		assertTrue(bank.deposit("John Smith", 0.0));
		TestUtils.assertAccountDetails(bank, "John Smith", 1000.0, 1, 1000.0);
	}
	
	@Test
	void testDepositWithDepositLessThanZero() {
		bank.addAccount(mockName, mockAmount);
		Exception exception = assertThrows(IllegalArgumentException.class, () -> bank.deposit(mockName, -500.0));
		assertEquals(invalidDepositErrorMessage, exception.getMessage());
	}

}
