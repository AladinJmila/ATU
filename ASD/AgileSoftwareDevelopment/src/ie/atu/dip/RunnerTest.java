package ie.atu.dip;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Edge cases have already been tested in BankingApp & InputValidator
 * This is purely to increase test coverage
 */

public class RunnerTest {
	private Runner runner;
	private Scanner scanner;
	private BankingApp bank;

	@BeforeEach
	void setUp() {
		bank = new BankingApp();

		// Simulate user input: valid input
		String input = "John Smith\n1000\n";
		scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

		runner = new Runner(scanner, bank);
		runner.addNewAccount();
	}

	@AfterEach
	void setDown() {
		runner = null;
	}
	
	@Test
	void testAddNewAccount() {
		TestUtils.assertAccountDetails(bank, "John Smith", 1000.0, 1, 1000.0);
	}
	
	@Test
	void testDepositMoneyWithExistingAccount() {
		// Simulate user input: valid input
		String input = "John Smith\n1000\n";
		scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
		
		runner = new Runner(scanner, bank);
		
		runner.depositMoney();
		TestUtils.assertAccountDetails(bank, "John Smith", 2000.0, 1, 2000.0);
	}
	
	@Test
	void testDepositMoneyWithNoneExistingAccount() {
		// Simulate user input: valid input
		String input = "Jane Smith\n1000\n";
		scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
		
		runner = new Runner(scanner, bank);
		
		runner.depositMoney();
		TestUtils.assertAccountDetails(bank, "John Smith", 1000.0, 1, 1000.0);
	}
	
	@Test
	void testWithdrawMoneyWithExistingAccount() {
		// Simulate user input: valid input
		String input = "John Smith\n500\n";
		scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
		
		runner = new Runner(scanner, bank);
		
		runner.withdrawMoney();
		TestUtils.assertAccountDetails(bank, "John Smith", 500.0, 1, 500.0);
	}
	
	@Test
	void testWithdrawMoneyWithNoneExistingAccount() {
		// Simulate user input: valid input
		String input = "Jane Smith\n500\n";
		scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
		
		runner = new Runner(scanner, bank);
		
		runner.withdrawMoney();
		TestUtils.assertAccountDetails(bank, "John Smith", 1000.0, 1, 1000.0);
	}
	
	@Test
	void testApproveLoanWithExistingAccount() {
		// Simulate user input: valid input
		String input = "John Smith\n500\n";
		scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
		
		runner = new Runner(scanner, bank);
		
		runner.approveLoan();
		TestUtils.assertAccountDetails(bank, "John Smith", 1000.0, 1, 500.0);
	}
	
	@Test
	void testApproveLoanWithNoneExistingAccount() {
		// Simulate user input: valid input
		String input = "Jane Smith\n500\n";
		scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
		
		runner = new Runner(scanner, bank);
		
		runner.approveLoan();
		TestUtils.assertAccountDetails(bank, "John Smith", 1000.0, 1, 1000.0);
	}
	
	/*
	@Test
	void testRepayLoanWithExistingAccount() {
		// Simulate user input: valid input
		String input = "John Smith\n500\n";
		scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
		
		runner = new Runner(scanner, bank);
		
		runner.repayLoan();
		TestUtils.assertAccountDetails(bank, "John Smith", 1500.0, 1, 1500.0);
	}
	
	@Test
	void testRepayLoanWithNoneExistingAccount() {
		// Simulate user input: valid input
		String input = "Jane Smith\n500\n";
		scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
		
		runner = new Runner(scanner, bank);
		
		runner.repayLoan();
		TestUtils.assertAccountDetails(bank, "John Smith", 1000.0, 1, 1000.0);
	}
	*/

}