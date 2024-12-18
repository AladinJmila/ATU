package ie.atu.dip;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class AccountTest {
	private Account account;
	
	@BeforeEach
	public void setUp() {
		account = new Account(TestUtils.MOCK_NAME, TestUtils.MOCK_AMOUNT);
	}
    
	@Test
    void testGetAccountHolder() {
		assertEquals(TestUtils.MOCK_NAME, account.getAccountHolder());
    }

	@Test
    void testGetBalance() {
        assertEquals(TestUtils.MOCK_AMOUNT, account.getBalance(), 0.1);
    }

	@Test
    void testGetLoan() {
		assertEquals(0.0, account.getLoan(), 0.1);
    }

	@Test
    void testDeposit() {
	  account.deposit(500.0);
      assertEquals((TestUtils.MOCK_AMOUNT + 500), account.getBalance(), 0.1);
    }

	@Test
    void testWithdraw() {
       account.withdraw(500.0);
       assertEquals(500.0, account.getBalance(), 0.1);
       assertFalse(account.withdraw(1000.0)); // Insufficient balance
    }
	
	@Test
    void testApproveLoan() {
      account.approveLoan(500.0);
      assertEquals(500.0, account.getLoan(), 0.1);
    }

	@Test
    void testRepayLoan() {
		account.approveLoan(1000.0);
		assertTrue(account.repayLoan(500.0));
		assertEquals(500.0, account.getLoan(), 0.1);
		assertFalse(account.repayLoan(1000)); // Repayment exceeds loan
    }

}
