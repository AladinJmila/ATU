package ie.atu.dip;

import java.util.ArrayList;
import java.util.List;

/**
 * This program simulates a simple banking application. It allows: - Adding new
 * accounts with an initial deposit. - Depositing and withdrawing money from
 * accounts. - Approving and repaying loans for account holders. - Tracking the
 * total deposits available in the bank.
 * 
 * The program uses a list of Account objects to manage account data.
 */
public class BankingApp {
	// List to store all accounts in the banking application
	private List<Account> accounts;
	private double totalDeposits; // Tracks total deposits in the bank

	// Constructor to initialize the banking application
	public BankingApp() {
		accounts = new ArrayList<>();
		totalDeposits = 0;
	}

	/**
	 * Helper method to find an account by account holder's name.
	 * 
	 * @param accountHolder The name of the account holder.
	 * @return The Account object if found, otherwise null.
	 */
	public Account findAccount(String accountHolder) {
		for (Account account : accounts) {
			if (account.getAccountHolder().equals(accountHolder)) {
				return account;
			}
		}
		return null;
	}

	/**
	 * Adds a new account with an initial deposit.
	 * 
	 * @param accountHolder  The name of the new account holder.
	 * @param initialDeposit The initial deposit amount.
	 */
	public void addAccount(String accountHolder, double initialDeposit) {
		if (accountHolder == null || accountHolder.trim().isEmpty())
			throw new IllegalArgumentException("Account holder name cannot be null or empty");

		if (initialDeposit < 0)
			throw new IllegalArgumentException("Amount cannot be negative");

		accounts.add(new Account(accountHolder, initialDeposit));
		totalDeposits += initialDeposit;
	}

	/**
	 * Deposits money into an account.
	 * 
	 * @param accountHolder The name of the account holder.
	 * @param amount        The deposit amount.
	 * @return True if the deposit is successful, otherwise false.
	 */
	public boolean deposit(String accountHolder, double amount) {
		if (accountHolder == null || accountHolder.trim().isEmpty())
			throw new IllegalArgumentException("Account holder name cannot be null or empty");

		if (amount < 0)
			throw new IllegalArgumentException("Amount cannot be negative");

		Account account = findAccount(accountHolder);
		if (account == null)
			return false;

		account.deposit(amount);
		totalDeposits += amount;
		return true;
	}

	/**
	 * Withdraws money from an account.
	 * 
	 * @param accountHolder The name of the account holder.
	 * @param amount        The withdrawal amount.
	 * @return True if the withdrawal is successful, otherwise false.
	 */
	public boolean withdraw(String accountHolder, double amount) {
		if (accountHolder == null || accountHolder.trim().isEmpty())
			throw new IllegalArgumentException("Account holder name cannot be null or empty");

		if (amount < 0)
			throw new IllegalArgumentException("Amount cannot be negative");

		Account account = findAccount(accountHolder);
		if (account == null || amount <= 0)
			return false;
		if (account.withdraw(amount)) {
			totalDeposits -= amount;
			return true;
		}

		return false;
	}

	/**
	 * Approves a loan for an account holder.
	 * 
	 * @param accountHolder The name of the account holder.
	 * @param loanAmount    The loan amount.
	 * @return True if the loan is approved, otherwise false.
	 */
	public boolean approveLoan(String accountHolder, double loanAmount) {
		if (accountHolder == null || accountHolder.trim().isEmpty())
			throw new IllegalArgumentException("Account holder name cannot be null or empty");

		if (loanAmount < 0)
			throw new IllegalArgumentException("Amount cannot be negative");

		Account account = findAccount(accountHolder);

		if (account == null || loanAmount > totalDeposits)
			return false;

		account.approveLoan(loanAmount);
		totalDeposits -= loanAmount;
		return true;
	}

	/**
	 * Repays a part of the loan for an account holder.
	 * 
	 * @param accountHolder The name of the account holder.
	 * @param amount        The repayment amount.
	 * @return True if the repayment is successful, otherwise false.
	 */
	public boolean repayLoan(String accountHolder, double amount) {
		if (accountHolder == null || accountHolder.trim().isEmpty())
			throw new IllegalArgumentException("Account holder name cannot be null or empty");

		if (amount < 0)
			throw new IllegalArgumentException("Amount cannot be negative");
		
		Account account = findAccount(accountHolder);
		if (account == null || amount <= 0) return false;
		
		if (account.repayLoan(amount)) {
			totalDeposits += amount;
			return true;
		}
		return false;
	}

	/**
	 * Gets the balance of a specific account holder.
	 * 
	 * @param accountHolder The name of the account holder.
	 * @return The balance if the account exists, otherwise null.
	 */
	public Double getBalance(String accountHolder) {
		if (accountHolder == null || accountHolder.trim().isEmpty())
			throw new IllegalArgumentException("Account holder name cannot be null or empty");
		
		Account account = findAccount(accountHolder);
		return account != null ? account.getBalance() : null;
	}

	/**
	 * Gets the loan amount of a specific account holder.
	 * 
	 * @param accountHolder The name of the account holder.
	 * @return The loan amount if the account exists, otherwise null.
	 */
	public Double getLoan(String accountHolder) {
		if (accountHolder == null || accountHolder.trim().isEmpty())
			throw new IllegalArgumentException("Account holder name cannot be null or empty");
		
		Account account = findAccount(accountHolder);
		return account != null ? account.getLoan() : null;
	}

	/**
	 * Gets the total deposits available in the bank.
	 * 
	 * @return The total deposits.
	 */
	public double getTotalDeposits() {
		return totalDeposits;
	}

	/**
	 * Gets the list of accounts available in the bank.
	 * 
	 * @return Accounts list.
	 */
	public List<Account> getAccounts() {
		return accounts;
	}
}
