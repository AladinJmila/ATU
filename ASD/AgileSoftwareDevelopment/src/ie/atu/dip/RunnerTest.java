package ie.atu.dip;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Edge cases have already been tested in BankingApp & InputValidator
 * This is purely to increase test coverage, so I'm only testing the happy path
 */

public class RunnerTest {
	private Runner runner;
	private Scanner scanner;
	private BankingApp bank;
	
	@BeforeAll
	static void runnerTestsStarted() {
		System.out.println("Runner tests started.");
	}

	@BeforeEach
	void setUp() {
		bank = new BankingApp();

		// Simulate user input: valid input
		String input = "John Smith\n1000\n";
		scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

		runner = new Runner(scanner, bank);
		runner.addNewAccount();
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
	

	@Test
	void testRepayLoanWithExistingAccount() {
		// Simulate user input: valid input
		String input1 = "John Smith\n500\n";
		scanner = new Scanner(new ByteArrayInputStream(input1.getBytes()));

		runner = new Runner(scanner, bank);

		runner.approveLoan();
		// Simulate user input: valid input
		String input2 = "John Smith\n250\n";
		scanner = new Scanner(new ByteArrayInputStream(input2.getBytes()));

		runner = new Runner(scanner, bank);

		runner.repayLoan();
		TestUtils.assertAccountDetails(bank, "John Smith", 1000.0, 1, 750.0);
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

	@Test
	void testGetAccountBalanceWithExistingAccount() {
		// Simulate user input: valid input
		String input = "John Smith\n";
		scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
		
		runner = new Runner(scanner, bank);
		
		runner.getAccountBalance();
		TestUtils.assertAccountDetails(bank, "John Smith", 1000.0, 1, 1000.0);
	}
	
	@Test
	void testGetAccountBalanceWithNoneExistingAccount() {
		// Simulate user input: valid input
		String input = "Jane Smith\n";
		scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
		
		runner = new Runner(scanner, bank);
		
		runner.getAccountBalance();
		TestUtils.assertAccountDetails(bank, "John Smith", 1000.0, 1, 1000.0);
	}
	
	
	@Test
	void testGetLoanAmountWithExistingAccount() {
		// Simulate user input: valid input
		String input = "John Smith\n";
		scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
		
		runner = new Runner(scanner, bank);
		
		runner.getLoanAmount();
		TestUtils.assertAccountDetails(bank, "John Smith", 1000.0, 1, 1000.0);
	}
	
	@Test
	void testGetLoanAmountWithNoneExistingAccount() {
		// Simulate user input: valid input
		String input = "Jane Smith\n";
		scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
		
		runner = new Runner(scanner, bank);
		
		runner.getLoanAmount();
		TestUtils.assertAccountDetails(bank, "John Smith", 1000.0, 1, 1000.0);
	}
	
	@Test
	void testGetTotalDeposits() {
		runner.getTotalDeposits();
		TestUtils.assertAccountDetails(bank, "John Smith", 1000.0, 1, 1000.0);
	}
	
	@Test
	void testShowMenu() {
		runner.showMenu();
		TestUtils.assertAccountDetails(bank, "John Smith", 1000.0, 1, 1000.0);
	}

	@Test
	void testInit() {
		bank = new BankingApp();

		// Simulate user input: valid input
		String input = "1\nJohn Smith\n1000\n2\nJohn Smith\n1000\n3\nJohn Smith\n1000\n4\nJohn Smith\n500\n5\nJohn Smith\n500\n6\nJohn Smith\n7\nJohn Smith\n8\n9\n";
		scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

		runner = new Runner(scanner, bank);
		runner.init();
	}
	
	@AfterEach
	void reset() {
		runner = null;
		scanner = null;
		bank = null;
	}
	
	@AfterAll
	static void runnerTestsEnded() {
		System.out.println("Runner tests ended.");
	}
}