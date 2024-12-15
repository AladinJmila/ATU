package ie.atu.dip;

import static java.lang.System.out;
import java.util.Scanner;
import java.text.NumberFormat;
import java.util.Locale;

public class Runner {
	private boolean isFirstRun = true;
	private boolean keepRunning = true;
	private Scanner scanner;
	private InputValidator validator;
	private BankingApp bank;
	
	Runner() {
		scanner = new Scanner(System.in);
		validator = new InputValidator(scanner);
		bank = new BankingApp();	
	}
	
	public void init() {
		while(keepRunning) {
			showMenu();
			
			// Define the valid range for the menu choices
			int[] range = {1, 9};
			// Validate and get suer input for menu choice
			int choice = validator.validateNumericInput( () -> showMenu(), range);
			
			// Handle then user's menu choice
			switch(choice) {
				case 1 -> addNewAccount();
				case 2 -> depositMoney();
				case 3 -> out.println(choice);
				case 4 -> out.println(choice);
				case 5 -> out.println(choice);
				case 6 -> out.println(choice);
				case 7 -> out.println(choice);
				case 8 -> out.println(choice);
				case 9 -> keepRunning = false;
				default -> out.println("Invalid Selection, choose a number from " + range[0] + " to " + range[1]+ ".");
			}
		}
	}
	
	public void addNewAccount() {
		String customerName = getCustomerNameInput();
		double amount = getAmountInput("Please enter the initial deposit amount");	
		
		bank.addAccount(customerName, amount);
		out.println(bank.findAccount(customerName).toString());
	}
	
	public void depositMoney() {
		String customerName = getCustomerNameInput();
		double amount = getAmountInput("Please enter the deposit amount");	
		
		boolean result = bank.deposit(customerName, amount);
		if (result) {
			out.println("Deposit added successfully");
		} else {
			out.println("Customer not found. Please try again or create an account.");
		}
		out.println(bank.findAccount(customerName).toString());
	}
	
	private String getCustomerNameInput() {
		String prompt = "Please enter the customer's name: ";
		out.print(prompt);
		return validator.validateNameInput(() -> out.print(prompt));
	}
	
	private double getAmountInput(String prompt) {
		double[] range = { 0.0d, 100_000_000.0d };
		NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
		String formattedRange = numberFormat.format(range[0]) + " - " + numberFormat.format(range[1]);
		String amountPrompt = prompt + " (" + formattedRange + "): ";
	
		out.print(amountPrompt);
		return validator.validateNumericInput(() -> out.print(amountPrompt), range );	
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
	    // Create a new banking application instance
		new Runner().init();
		
		/*
	    BankingApp bank = new BankingApp();

	    // Add accounts
	    bank.addAccount("Alice", 1000);
	    bank.addAccount("Bob", 500);

	    // Test deposits
	    System.out.println("Depositing 200 to Alice: " + bank.deposit("Alice", 200)); // Should return true
	    System.out.println("Alice's balance: " + bank.getBalance("Alice")); // Should be 1200

	    // Test withdrawals
	    System.out.println("Withdrawing 300 from Bob: " + bank.withdraw("Bob", 300)); // Should return true
	    System.out.println("Bob's balance: " + bank.getBalance("Bob")); // Should be 200

	    // Test loan approval
	    System.out.println("Approving a loan of 400 for Alice: " + bank.approveLoan("Alice", 400)); // Should return true
	    System.out.println("Alice's loan: " + bank.getLoan("Alice")); // Should be 400

	    // Test loan repayment
	    System.out.println("Repaying 200 of Alice's loan: " + bank.repayLoan("Alice", 200)); // Should return true
	    System.out.println("Alice's remaining loan: " + bank.getLoan("Alice")); // Should be 200

	    // Check total deposits in the bank
	    System.out.println("Total deposits in the bank: " + bank.getTotalDeposits());
	    */
	}

}
