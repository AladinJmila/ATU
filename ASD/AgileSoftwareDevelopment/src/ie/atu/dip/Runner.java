package ie.atu.dip;

import static java.lang.System.out;
import java.util.Scanner;
import java.text.NumberFormat;
import java.util.Locale;

public class Runner {
	private boolean isFirstRun = true;
	private boolean keepRunning = true;
	private Scanner scanner;
	private BankingApp bank;
	
	Runner() {
		scanner = new Scanner(System.in);
		bank = new BankingApp();	
	}

	Runner(Scanner scanner, BankingApp bank) {
		this.scanner = scanner;
		this.bank = bank;	
	}
	
	public void init() {
		while(keepRunning) {
			showMenu();
			
			// Define the valid range for the menu choices
			int[] range = {1, 9};
			// Validate and get suer input for menu choice
			int choice = InputValidator.validateNumericInput(scanner, () -> showMenu(), range);
			
			// Handle then user's menu choice
			switch(choice) {
				case 1 -> addNewAccount();
				case 2 -> depositMoney();
				case 3 -> withdrawMoney();
				case 4 -> approveLoan();
				case 5 -> repayLoan();
				case 6 -> getAccountBalance();
				case 7 -> getLoanAmount();
				case 8 -> getTotalDeposits();
				case 9 -> keepRunning = false;
				default -> out.println("Invalid Selection, choose a number from " + range[0] + " to " + range[1]+ ".");
			}
		}
	}
	
	public void addNewAccount() {
		String customerName = getCustomerNameInput(scanner);
		double amount = getAmountInput(scanner, "Please enter the initial deposit amount");
		
		// Error handling is not required, InputValidator is handling safe guarding
		bank.addAccount(customerName, amount);
		out.println("Account successfully created for " + customerName + " with an initial deposit of " + amount);

	}
	
	public void depositMoney() {
		String customerName = getCustomerNameInput(scanner);
		double amount = getAmountInput(scanner, "Please enter the deposit amount");

		// Error handling is not required, InputValidator is handling safe guarding
		boolean result = bank.deposit(customerName, amount);
		if (result) {
			out.println("Successfull deposit of " + amount);
		} else {
			out.println("Customer not found. Please try again or create an account.");
		}

	}

	public void withdrawMoney() {
		String customerName = getCustomerNameInput(scanner);
		double amount = getAmountInput(scanner, "Please enter the amout to withdraw");

		// Error handling is not required, InputValidator is handling safe guarding
		boolean result = bank.withdraw(customerName, amount);
		if (result) {
			out.println("Successfull withdrwal of " + amount);
		} else {
			out.println("Customer not found or Invalid amount. Please try again or create an account.");
		}

	}
	
	public void approveLoan() {
		String customerName = getCustomerNameInput(scanner);
		double amount = getAmountInput(scanner, "Please enter the laon amount");

		boolean result = bank.approveLoan(customerName, amount);
		if (result) {
			out.println("Successfull laon approval of " + amount);
		} else {
			out.println("Customer not found or Invalid amount. Please try again or create an account.");
		}

	}

	public void repayLoan() {
		String customerName = getCustomerNameInput(scanner);
		double amount = getAmountInput(scanner, "Please enter the amout to repay");

		boolean result = bank.repayLoan(customerName, amount);
		if (result) {
			out.println("Successfull laon repayment of " + amount);
		} else {
			out.println("Customer not found or Invalid amount. Please try again or create an account.");
		}

	}

	public void getAccountBalance() {
		String customerName = getCustomerNameInput(scanner);

		Double balance = bank.getBalance(customerName);
		if (balance != null) {
			out.println("The account balance of " + customerName + " is " + balance);
		} else {
			out.println("Customer not found. Please try again or create an account.");
		}
	}
	
	public void getLoanAmount() {
		String customerName = getCustomerNameInput(scanner);

			Double amount = bank.getLoan(customerName);
			if (amount != null) {
				out.println("The account balance of " + customerName + " is " + amount);
			} else {
				out.println("Customer not found. Please try again or create an account.");
			}
	}

	public void getTotalDeposits() {
		out.println("The total edposit is " + bank.getTotalDeposits());
	}
	
	public String getCustomerNameInput(Scanner scanner) {
		String prompt = "Please enter the customer's name: ";
		out.print(prompt);
		return InputValidator.validateNameInput(scanner, () -> out.print(prompt));
	}
	
	public double getAmountInput(Scanner scanner, String prompt) {
		double[] range = { 0.0d, 100_000_000.0d };
		NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
		String formattedRange = numberFormat.format(range[0]) + " - " + numberFormat.format(range[1]);
		String amountPrompt = prompt + " (" + formattedRange + "): ";
	
		out.print(amountPrompt);
		return InputValidator.validateNumericInput(scanner, () -> out.print(amountPrompt), range );	
	}
	
	public void showMenu() {
		if (isFirstRun) {
		out.println();
		out.println("************************************************************");
		out.print("*     ");
		out.print("ATU - Dept. of Computer Science & Applied Physics");
		out.println("    *");
		out.println("*                                                          *");
		out.print("*        ");
		out.print("Banking Application Unit Testing Assignment");
		out.println("       *");
		out.println("*                                                          *");
		out.println("************************************************************");
		}

	    System.out.println();
        System.out.println("Welcome to our Banking Service:");
        System.out.println("---------------------------------------------------");
        System.out.println("| 1 | Add New Account");
        System.out.println("| 2 | Deposit Money");
        System.out.println("| 3 | Withdraw Money");
        System.out.println("| 4 | Approve Loan");
        System.out.println("| 5 | Repay Loan");
        System.out.println("| 6 | Check Account Balance");
        System.out.println("| 7 | Check Loan Amount");
        System.out.println("| 8 | Check Total Deposits");
        System.out.println("| 9 | Quit");
        System.out.println("---------------------------------------------------");
        System.out.print("Select Option [1-9]> ");
		
		isFirstRun = false;

	}
	
	public static void main(String[] args) {
		new Runner().init();
	}

}
